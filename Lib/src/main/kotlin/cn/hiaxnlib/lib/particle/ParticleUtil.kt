package cn.hiaxnlib.lib.particle

import cn.hiaxnlib.particle.HiaXnParticle
import org.bukkit.Location

interface ParticleUtil {
    fun spawnParticle(location: Location, particle: HiaXnParticle)
}