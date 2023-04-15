package cn.hiaxnlib.lib.particle

import cn.hiaxnlib.lib.particle.particle.HiaXnParticle
import org.bukkit.Location

interface ParticleUtil {
    fun spawnParticle(location: Location, particle: HiaXnParticle)
}