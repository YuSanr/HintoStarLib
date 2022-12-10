package cn.HiaXnLib.Particle.ParticleStyle

import cn.HiaXnLib.Particle.HiaXnParticleGroup
import cn.HiaXnLib.Particle.HiaXnParticleStyle
import cn.HiaXnLib.Particle.util.ParticleMathUtil
import org.bukkit.Location
import java.util.*

/**
 * 爱心图形
 * @param size 爱心大小
 * @param all 是否一次播出
 * @param order 为true时顺时针播放 为false时逆时针播放
 */
class LoveYZParticleStyle(var size:Double, var playTimes:Int, var particleGroup: HiaXnParticleGroup, var all:Boolean, var order:Boolean): HiaXnParticleStyle(){
    var index = 0
    var nowTimes = 0
    init {
        setIntervalTime(1)
    }
    override fun getParticleList(location: Location): LinkedList<HiaXnParticleGroup> {
        val locList = ParticleMathUtil.getYZLoveLocation(location,size,size.toInt()*60)
        // 该list的size永远等于pointerNumber size.toInt()*60
        val particleList = LinkedList<HiaXnParticleGroup>()
        for (loc in locList){
            particleList.add(particleGroup.clone(loc))
        }
        if (all){
            return particleList
        }
        return if (order){
            LinkedList(listOf(particleList[index]))
        }else{
            LinkedList(listOf(particleList[particleList.size-index]))
        }
    }

    override fun updateParticleLocation() {
        index++
        index %= size.toInt()*60
        if (index == 0 && playTimes != -1){
           nowTimes++
        }
    }

    override fun cancelStrategy(): Boolean {
        return if (playTimes == -1){
            false
        }else{
            playTimes - nowTimes<=0
        }
    }
}