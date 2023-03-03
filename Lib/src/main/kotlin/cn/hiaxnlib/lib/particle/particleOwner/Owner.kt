package cn.hiaxnlib.lib.particle.particleOwner

import org.bukkit.Location
import java.util.UUID

interface Owner {
    fun getOwner():Owner
    fun getUUID():UUID
    fun getParticleLocation():Location
}