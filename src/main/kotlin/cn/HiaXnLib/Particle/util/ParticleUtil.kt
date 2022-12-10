package cn.HiaXnLib.Particle.util

import cn.HiaXnLib.Particle.HiaXnParticle
import cn.HiaXnLib.Particle.HiaXnParticlePair
import cn.HiaXnLib.Particle.RelativeLocation
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import java.lang.Math.PI

object ParticleUtil {
    fun spawnParticle(location:Location,particle: HiaXnParticle){
//        location.world.spawnParticle(location,particle.config.type
//            ,particle.data.id,particle.data.data,particle.config.offX,particle.config.offY,
//            particle.config.offZ,particle.config.speed,particle.config.count,particle.config.radius)
        location.world.spawnParticle(
            Particle.valueOf(particle.config.type.toString()),
            location,
            particle.config.count,
            particle.config.offX,
            particle.config.offY,
            particle.config.offZ,
            particle.config.extra,
            particle.config.data
        )
    }
    fun createParticleStyleTask(pair:HiaXnParticlePair){
        ParticleTask.activeParticleStyleSet.add(pair)
    }

    /**
     * 获取玩家眼部指向向量
     */
    fun getPlayerEyeVector(player:Player):Vector{
        return player.eyeLocation.direction
    }
}