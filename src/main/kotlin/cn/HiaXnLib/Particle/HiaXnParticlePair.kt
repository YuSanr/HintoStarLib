package cn.HiaXnLib.Particle

import cn.HiaXnLib.Particle.ParticleOwner.Owner
import cn.HiaXnLib.Particle.util.ParticleUtil
import org.bukkit.Location

/**
 * 该类用于播放设置的粒子
 * 正式将粒子绑定在Owner处
 * 执行时需要借助ParticleTask
 * 因此需要在ParticleUtil的CreateParticleDisplayTask函数中运行
 * @see ParticleUtil.createParticleStyleTask
 */
class HiaXnParticlePair(var owner:Owner, var style: HiaXnParticleStyle,var playMethod:((location:Location,particle:HiaXnParticle)->Unit)? = null) {
    // 在owner所在的位置释放粒子
    // 此时需要多次执行
    var over = false
    fun display(){
        try{
            val particleLocation = owner.getParticleLocation()
            for (group in style.getParticleList(particleLocation)) {
                if(playMethod!= null){
                    group.display(playMethod!!)
                }
                group.display()
            }
            style.updateParticleLocation()
        }catch (ignored:NullPointerException){
            over = true
            return
        }
        over = style.cancelStrategy()
    }

}