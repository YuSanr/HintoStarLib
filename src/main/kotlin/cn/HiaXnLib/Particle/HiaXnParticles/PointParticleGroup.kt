package cn.HiaXnLib.Particle.HiaXnParticles

import cn.HiaXnLib.Particle.HiaXnParticle
import cn.HiaXnLib.Particle.HiaXnParticleGroup
import cn.HiaXnLib.Particle.RelativeLocation
import org.bukkit.Location

/**
 * 粒子组 点
 */
class PointParticleGroup(private val location: Location?,var particle: HiaXnParticle): HiaXnParticleGroup(location) {
    init {
        axis = RelativeLocation(0.0,0.0,0.0)
    }
    override fun getParticleLocationMap(): LinkedHashMap<RelativeLocation, HiaXnParticle> {
        val m =  LinkedHashMap<RelativeLocation,HiaXnParticle>()
        m[RelativeLocation(0.0,0.0,0.0)] = particle
        return m
    }

    override fun clone(): HiaXnParticleGroup {
        return PointParticleGroup(location,particle)
    }

    override fun clone(location: Location): HiaXnParticleGroup {
        return PointParticleGroup(location,particle)
    }
}