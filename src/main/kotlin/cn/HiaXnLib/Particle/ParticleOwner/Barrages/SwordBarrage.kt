package cn.HiaXnLib.Particle.ParticleOwner.Barrages

import cn.HiaXnLib.Particle.HiaXnParticle
import cn.HiaXnLib.Particle.HiaXnParticles.SwordParticleGroup
import cn.HiaXnLib.Particle.ParticleOwner.Owner
import cn.HiaXnLib.Particle.ParticleOwner.ParticleEntity
import cn.HiaXnLib.Particle.ParticleOwner.ParticlePlayer
import cn.HiaXnLib.Particle.ParticleOwner.ParticlePoint
import cn.HiaXnLib.main
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.block.data.type.Bed.Part
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import java.util.UUID

/**
 * 弹幕框架的示例代码
 */
class SwordBarrage(location:Location,uuid:UUID,barrageOwner:Owner,speed:Double,direction:Vector):Barrage(location,uuid,barrageOwner) {
    var nowTick = 0
    init {
        // 构造函数 设定各个初始值 粒子信息
        setBarrageSpeed(speed)
        setBarrageDirection(direction)
        particleGroup = SwordParticleGroup(nowLocation, HiaXnParticle(HiaXnParticle.ParticleConfig(Particle.END_ROD,0.00,1,0.0,0.0,0.0)))

        // 设置策略
        setHitJudgingStrategy {
            if (it is Barrage){
                return@setHitJudgingStrategy false
            }
            if (it is ParticlePlayer){
                val distance = it.player.location.distance(nowLocation)
                return@setHitJudgingStrategy distance<3.0
            }
            if (it is ParticleEntity){
                val distance = it.entity.location.distance(nowLocation)
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
                    it.player.damage(6.0,(barrageOwner as ParticlePlayer).player)
                }else if (barrageOwner is ParticleEntity){
                    it.player.damage(6.0,(barrageOwner as ParticleEntity).entity)
                }
            }
            Bukkit.getScheduler().runTask(main.getInstance()){
                it.getParticleLocation().world.createExplosion(it.getParticleLocation(),4.0f,true)
            }
            this.hitted = true
            this.effective = false
            Barrage.barrageHashMap.remove(this.barrageUUID)
        }
    }
    // 更新位置Tick
    override fun updateLocationTick() {
        nowTick++
    }

    // 更新位置函数
    override fun updateLocation() {
        // 速度加成
        // 真实速度  因为运算一次间隔为1tick
        if(nowTick > 100){
            nextLocation()
        }
    }
}