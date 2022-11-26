package cn.HiaXnLib.Particle.ParticleOwner

import org.bukkit.Location
import java.util.UUID

class ParticlePoint(var location:Location,val pointUUID:UUID):Owner {
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