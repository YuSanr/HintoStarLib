package cn.hiaxnlib.lib.owner

import cn.hiaxnlib.lib.particle.particleOwner.Owner
import org.bukkit.Location
import java.util.UUID

class OwnerPoint(var location:Location, val pointUUID:UUID):Owner {
    override fun getOwner(): Owner {
        return this
    }

    override fun getUUID(): UUID {
        return pointUUID
    }

    override fun getParticleLocation(): Location {
        return location
    }
}