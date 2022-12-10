package cn.HiaXnLib.Particle.ParticleOwner

import org.bukkit.Location
import org.bukkit.entity.Entity
import java.lang.NullPointerException
import java.util.*

class ParticleEntity(val entity:Entity):Owner {
    override fun getOwner(): Owner {
        return this
    }

    override fun getUUID(): UUID {
        return entity.uniqueId
    }

    /**
     * @throws NullPointerException Entity not Valid
     */
    override fun getParticleLocation(): Location {

        return entity.location
    }
}