package cn.HiaXnLib.API.util

import net.minecraft.server.v1_8_R3.EnumParticle
import org.bukkit.Effect
import org.bukkit.Location

interface ParticleUtil {
    fun spawnParticle(location: Location, effect: Effect, b: Boolean)
}