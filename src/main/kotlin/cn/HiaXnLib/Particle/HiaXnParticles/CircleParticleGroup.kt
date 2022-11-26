package cn.HiaXnLib.Particle.HiaXnParticles

import cn.HiaXnLib.Particle.HiaXnParticle
import cn.HiaXnLib.Particle.HiaXnParticleGroup
import cn.HiaXnLib.Particle.RelativeLocation
import cn.HiaXnLib.Particle.util.ParticleMathUtil
import org.bukkit.Location
import java.util.LinkedList

class CircleParticleGroup(var particle: HiaXnParticle,location:Location,var radius:Double):HiaXnParticleGroup(location) {
    override fun getParticleLocationMap(): LinkedHashMap<RelativeLocation, HiaXnParticle> {
        val result = LinkedHashMap<RelativeLocation,HiaXnParticle>()
        val location = Location(null,0.0,0.0,0.0)
        for (location in ParticleMathUtil.getXYCircleLocation(location, radius, radius.toInt() * 90)) {

            val relative = RelativeLocation(location.x,location.y,location.z)
            result[relative] = particle
        }
        return result
    }
}