package cn.HiaXnLib.Particle.HiaXnParticles

import cn.HiaXnLib.Particle.HiaXnParticle
import cn.HiaXnLib.Particle.HiaXnParticleGroup
import cn.HiaXnLib.Particle.RelativeLocation
import cn.HiaXnLib.Particle.util.ParticleMathUtil
import org.bukkit.Location
import org.bukkit.World
import java.util.LinkedList

/**
 * 粒子组 点
 */
class PointParticleGroup(location: Location,var particle: HiaXnParticle): HiaXnParticleGroup(location) {
    override fun getParticleLocationMap(): LinkedHashMap<RelativeLocation, HiaXnParticle> {
        val m =  LinkedHashMap<RelativeLocation,HiaXnParticle>()
        m[RelativeLocation(0.0,0.0,0.0)] = particle
        return m
    }
}