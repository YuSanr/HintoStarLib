package cn.HiaXnLib.Particle

import cn.HiaXnLib.Particle.util.ParticleUtil
import org.bukkit.Location

/**
 * @param origin 粒子相对原点 粒子生成位置以该原点生成 考虑到原点发生改变 因此可以修改
 */
abstract class HiaXnParticleGroup(var origin: Location?){
    var axis:RelativeLocation = RelativeLocation(1.0,0.0,0.0)
    /**
      初始化粒子相对位置Map
     * */
    abstract fun getParticleLocationMap():LinkedHashMap<RelativeLocation,HiaXnParticle>

    /**
     * 在这个位置播放粒子
     */
    fun display(){
        origin?:return
        for (entry in getParticleLocationMap()) {
            val relativeLocation = entry.key
            val nextLocation = origin!!.clone()
            nextLocation.x += relativeLocation.x
            nextLocation.y += relativeLocation.y
            nextLocation.z += relativeLocation.z
            ParticleUtil.spawnParticle(nextLocation,entry.value)
        }
    }
    fun display(map:LinkedHashMap<RelativeLocation,HiaXnParticle>){
        origin?:return
        for (entry in map) {
            val relativeLocation = entry.key
            val nextLocation = origin!!.clone()
            nextLocation.x += relativeLocation.x
            nextLocation.y += relativeLocation.y
            nextLocation.z += relativeLocation.z
            ParticleUtil.spawnParticle(nextLocation,entry.value)
        }
    }
    abstract fun clone():HiaXnParticleGroup
    abstract fun clone(location:Location):HiaXnParticleGroup
}