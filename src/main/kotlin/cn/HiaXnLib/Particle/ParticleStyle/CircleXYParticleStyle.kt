package cn.HiaXnLib.Particle.ParticleStyle

import cn.HiaXnLib.Particle.HiaXnParticleGroup
import cn.HiaXnLib.Particle.HiaXnParticleStyle
import cn.HiaXnLib.Particle.util.ParticleMathUtil
import org.bukkit.Location
import java.util.*

// 执行次数
/**
 * @param order 为true时 顺时针旋转 为false时 逆时针旋转
 */
class CircleXYParticleStyle(var playTimes:Int, val radius:Double, var particleGroup:HiaXnParticleGroup, var order:Boolean): HiaXnParticleStyle() {
    var index = 0
    var nowTimes = 0
    init {
        setIntervalTime(1)
    }
    override fun getParticleList(location: Location): LinkedList<HiaXnParticleGroup> {
        val result = LinkedList<HiaXnParticleGroup>()
        if (order){
            result.add(particleGroup.clone(ParticleMathUtil.getXYCircleLocation(location,radius,radius.toInt()*90)[index]))
        }else{
            val list = ParticleMathUtil.getXYCircleLocation(location, radius, radius.toInt() * 90)
            result.add(particleGroup.clone(list[list.size - index - 1]))
        }
        return result
    }

    override fun updateParticleLocation() {
        index++
        if (index%(radius.toInt()*90) == 0 && playTimes != -1){
            nowTimes++
        }
        index %= radius.toInt()*90
    }

    override fun cancelStrategy(): Boolean {
        if (playTimes!=-1 && playTimes-nowTimes<=0){
            return true
        }
        return false
    }
}