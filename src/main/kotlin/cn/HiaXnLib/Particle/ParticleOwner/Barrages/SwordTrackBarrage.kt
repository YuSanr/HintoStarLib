package cn.HiaXnLib.Particle.ParticleOwner.Barrages

import cn.HiaXnLib.Particle.HiaXnParticle
import cn.HiaXnLib.Particle.HiaXnParticles.SwordParticleGroup
import cn.HiaXnLib.Particle.ParticleOwner.Owner
import cn.HiaXnLib.Particle.ParticleOwner.ParticleEntity
import cn.HiaXnLib.Particle.ParticleOwner.ParticlePlayer
import cn.HiaXnLib.Particle.ParticleOwner.ParticlePoint
import cn.HiaXnLib.Particle.RelativeLocation
import cn.HiaXnLib.Particle.util.ParticleMathUtil
import cn.HiaXnLib.main
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import java.util.*
import kotlin.collections.LinkedHashMap

/**
 * 弹幕框架的示例代码
 */
class SwordTrackBarrage(location:Location,uuid:UUID,barrageOwner:Owner,speed:Double,val target:Player):Barrage(location,uuid,barrageOwner) {
    var nowTick = 0
    init {
        // 构造函数 设定各个初始值 粒子信息
        setBarrageSpeed(speed)
        val vector = Vector(target.location.x-nowLocation.x,target.location.y - nowLocation.y,target.location.z - nowLocation.z)
        setBarrageDirection(vector)
        particleGroup = SwordParticleGroup(this.nowLocation, HiaXnParticle(HiaXnParticle.ParticleConfig(Particle.END_ROD,0.00,1,0.0,0.0,0.0)))
        // 设置策略
        setHitJudgingStrategy {
            if (it is Barrage){
                return@setHitJudgingStrategy false
            }
            if (it is ParticlePlayer){
                if (it.player.player == null){
                    return@setHitJudgingStrategy true
                }
                val distance = it.player.location.distance(this.nowLocation)
                return@setHitJudgingStrategy distance<3.0
            }
            if (it is ParticleEntity){
                val distance = it.entity.location.distance(this.nowLocation)
                return@setHitJudgingStrategy distance<3.0
            }
            if (it is ParticlePoint){
                return@setHitJudgingStrategy !it.location.block.isEmpty && !it.location.block.isLiquid
            }
            return@setHitJudgingStrategy true
        }
        // 设置击中事件
        setHitDoingMethod{
            if (it is ParticlePlayer){
                if(barrageOwner is ParticlePlayer){
                    it.player.damage(6.0, barrageOwner.player)
                }else if (barrageOwner is ParticleEntity){
                    it.player.damage(6.0, barrageOwner.entity)
                }
            }
            Bukkit.getScheduler().runTask(main.getInstance()){
                it.getParticleLocation().world.createExplosion(it.getParticleLocation(),4.0f,true)
            }
            this.hitted = true
            this.effective = false
            barrageHashMap.remove(this.barrageUUID)
        }
    }
    // 更新位置Tick
    override fun updateLocationTick() {
        nowTick++
    }

    // 更新位置函数
    override fun updateLocation() {
        // 更新追踪方位
        if(nowTick <= 100){
            val vector = Vector(target.location.x-nowLocation.x,target.location.y - nowLocation.y,target.location.z - nowLocation.z)
            setBarrageDirection(vector)
        }
        // 速度加成
        // 真实速度  因为运算一次间隔为1tick
        if(nowTick > 40){
            nextLocation()
        }
    }

    override fun updateAndShowParticle() {
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
}