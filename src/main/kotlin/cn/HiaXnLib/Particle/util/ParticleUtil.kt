package cn.HiaXnLib.Particle.util

import cn.HiaXnLib.Particle.HiaXnParticle
import cn.HiaXnLib.Particle.HiaXnParticlePair
import cn.HiaXnLib.Particle.HiaXnParticleStyle
import org.bukkit.Location

object ParticleUtil {
    fun spawnParticle(location:Location,particle: HiaXnParticle){
        location.world.spigot().playEffect(location,particle.config.type
            ,particle.data.id,particle.data.data,particle.config.offX,particle.config.offY,
            particle.config.offZ,particle.config.speed,particle.config.count,particle.config.radius)
    }
    fun createParticleStyleTask(pair:HiaXnParticlePair){
        ParticleTask.activeParticleStyleSet.add(pair)
    }
}