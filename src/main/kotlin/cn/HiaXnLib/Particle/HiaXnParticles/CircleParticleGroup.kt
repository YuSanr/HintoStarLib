package cn.HiaXnLib.Particle.HiaXnParticles

import cn.HiaXnLib.Particle.HiaXnParticle
import cn.HiaXnLib.Particle.HiaXnParticleGroup
import cn.HiaXnLib.Particle.RelativeLocation
import cn.HiaXnLib.Particle.util.ParticleMathUtil
import org.bukkit.Location

class CircleParticleGroup(var particle: HiaXnParticle,private val location:Location?,var radius:Double):HiaXnParticleGroup(location) {
    override fun getParticleLocationMap(): LinkedHashMap<RelativeLocation, HiaXnParticle> {
        val result = LinkedHashMap<RelativeLocation,HiaXnParticle>()
        val xyz = Location(null,0.0,0.0,0.0)
        for (location in ParticleMathUtil.getXZCircleLocation(xyz, radius, radius.toInt() * 45)) {
            val relative = RelativeLocation(location.x,location.y,location.z)
            result[relative] = particle
        }
        return result
    }

    override fun clone(): HiaXnParticleGroup {
        return CircleParticleGroup(particle, location,radius)
    }

    override fun clone(location: Location): HiaXnParticleGroup {
        return CircleParticleGroup(particle,location,radius)
    }
}