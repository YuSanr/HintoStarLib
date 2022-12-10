package cn.HiaXnLib.Particle.ParticleOwner.Barrages

import cn.HiaXnLib.Event.BarrageHitEvent
import cn.HiaXnLib.Particle.HiaXnParticle
import cn.HiaXnLib.Particle.HiaXnParticleGroup
import cn.HiaXnLib.Particle.HiaXnParticles.PointParticleGroup
import cn.HiaXnLib.Particle.ParticleOwner.Owner
import cn.HiaXnLib.Particle.ParticleOwner.ParticleEntity
import cn.HiaXnLib.Particle.ParticleOwner.ParticlePlayer
import cn.HiaXnLib.Particle.ParticleOwner.ParticlePoint
import cn.HiaXnLib.Particle.RelativeLocation
import cn.HiaXnLib.Particle.util.ParticleMathUtil
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import java.util.HashMap
import java.util.LinkedList
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.min

/**
 * @param barrageOwner 弹幕所有者
 */
abstract class Barrage(var location:Location,val barrageUUID:UUID,var barrageOwner:Owner) :Owner{
    companion object{
        val barrageHashMap = ConcurrentHashMap<UUID,Barrage>()
    }
    // 是否存活
    var effective = true
    var hitted = false
    // 当前位置
    var nowLocation:Location = location
    // 绑定粒子组  用于显示弹幕
    var particleGroup:HiaXnParticleGroup = PointParticleGroup(nowLocation, HiaXnParticle(
        HiaXnParticle.ParticleConfig(
            Particle.CLOUD,0.0,1,0.0,0.0,0.0)))
    // 击中策略判断
    private var hitStrategy:(Owner)->Boolean = {false}
    // 击中行为策略列表
    var hitDoingStrategy:(Owner)->Unit = {}
    // 最大速度 /秒
    private val maxSpeed = 80.0
    // 重力向量常量
    val g = Vector(0.0,-1.0,0.0)
    // 当前速度
    var speedVector = Vector(0.0,0.0,0.0)
    var nowSpeed = 0.0
    /**
     * 击中判断策略
     */
    fun setHitJudgingStrategy(strategy:(owner: Owner)->Boolean){
        hitStrategy = strategy
    }

    /**
     * 设置弹幕的移动速度
     * Max Speed = 80.0 / s
     */
    fun setBarrageSpeed(speed:Double){
        nowSpeed = min(speed,maxSpeed)
    }

    /**
     * 设置弹幕移动的方向
     */
    fun setBarrageDirection(vector: Vector){
        // 转换为单位向量
        speedVector = vector.multiply(1/vector.length())
    }
    /**
     * 若该弹幕击中实体 即 hitJudgingStrategy中有一个为True
     * 则执行该设定的方法 并Call Event BarrageHitEvent
     * @param strategy 设置的击中策略 - 如 对实体造成伤害 爆炸等
     */
    fun setHitDoingMethod(strategy:(owner: Owner)->Unit){
        this.hitDoingStrategy = strategy
    }

    /**
     * 更新弹幕位置
     * 由某个参数决定当前的位置 速度 方向等
     */
    abstract  fun updateLocationTick()

    /**
     * 更新位置本身 修改nowLocation的值
     * 更改速度由 nowSpeed 和 speedVector 决定
     * 包括最基本的移动
     */
    abstract fun updateLocation()
    // 根据SpeedVector更改particle的指向
    /**
     * 更新当前粒子所在的位置
     * 并且显示粒子
     */
    fun updateAndShowParticle(){
        val speedDirector = RelativeLocation(speedVector.x,speedVector.y,speedVector.z)
        val pitch = ParticleMathUtil.toPointPitch(particleGroup.axis,speedDirector)
        val yaw = ParticleMathUtil.toPointYaw(particleGroup.axis,speedDirector)
        val mapList = particleGroup.getParticleLocationMap()
        val relativeList = LinkedList<RelativeLocation>()
        for (entry in mapList){
            relativeList.add(entry.key)
        }
        ParticleMathUtil.rotationPoint(relativeList,yaw,pitch)
        val newMap = LinkedHashMap<RelativeLocation,HiaXnParticle>()
        val keyList = LinkedList<HiaXnParticle>()
        for (key in mapList.values) {
            keyList.add(key)
        }
        for (i  in 0 until keyList.size){
            newMap[relativeList[i]] = keyList[i]
        }
        // 播放
        particleGroup.display(newMap)
    }
    /**
     * 检测弹幕是否符合击中条件
     * 符合 则执行击中函数
     */
    fun checkStrategy(){
        for (barrages in barrageHashMap) {
            if (barrages.value == this){
                continue
            }
            if (hitStrategy(barrages.value)){
                hitDoingStrategy(barrages.value)
                Bukkit.getPluginManager().callEvent(BarrageHitEvent(this,barrages.value))
                return
            }
        }
        for (player in nowLocation.world.players) {
            // 不 能 打 自 己
            if (barrageOwner.getUUID() == player.uniqueId) continue
            val particlePlayer = ParticlePlayer(player)
            if (hitStrategy(particlePlayer)){
                hitDoingStrategy(particlePlayer)
                Bukkit.getPluginManager().callEvent(BarrageHitEvent(this,particlePlayer))
                return
            }
        }
        // 万一打到了其他实体
        for (entity in nowLocation.world.entities){
            // 玩家也是实体
            if (entity is Player) continue
            val particleEntity = ParticleEntity(entity)
            if (hitStrategy(particleEntity)){
                hitDoingStrategy(particleEntity)
                Bukkit.getPluginManager().callEvent(BarrageHitEvent(this,particleEntity))
                return
            }
        }
        // 万一打到方块了呢
        val particleLocation = ParticlePoint(this.nowLocation,UUID.randomUUID())
        if (hitStrategy(particleLocation)){
            hitDoingStrategy(particleLocation)
            Bukkit.getPluginManager().callEvent(BarrageHitEvent(this,particleLocation))
            return
        }
    }
    override fun getOwner(): Owner {
        return this
    }

    override fun getUUID(): UUID {
        return barrageUUID
    }

    override fun getParticleLocation(): Location {
        return nowLocation
    }

    /**
     * 进行到下一个位置
     * 相当于向运动方向运动了1tick
     */
    protected fun nextLocation(){
        val multiply = speedVector.clone().multiply(nowSpeed / 20.0)
        nowLocation.x += multiply.x
        nowLocation.y+= multiply.y
        nowLocation.z += multiply.z
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Barrage

        if (location != other.location) return false
        if (barrageUUID != other.barrageUUID) return false
        if (barrageOwner != other.barrageOwner) return false
        if (effective != other.effective) return false
        if (hitted != other.hitted) return false
        if (nowLocation != other.nowLocation) return false
        if (hitStrategy != other.hitStrategy) return false
        if (hitDoingStrategy != other.hitDoingStrategy) return false
        if (maxSpeed != other.maxSpeed) return false
        if (g != other.g) return false
        if (speedVector != other.speedVector) return false
        if (nowSpeed != other.nowSpeed) return false

        return true
    }

    override fun hashCode(): Int {
        var result = location.hashCode()
        result = 31 * result + barrageUUID.hashCode()
        result = 31 * result + barrageOwner.hashCode()
        result = 31 * result + effective.hashCode()
        result = 31 * result + hitted.hashCode()
        result = 31 * result + nowLocation.hashCode()
        result = 31 * result + hitStrategy.hashCode()
        result = 31 * result + hitDoingStrategy.hashCode()
        result = 31 * result + maxSpeed.hashCode()
        result = 31 * result + g.hashCode()
        result = 31 * result + speedVector.hashCode()
        result = 31 * result + nowSpeed.hashCode()
        return result
    }
}