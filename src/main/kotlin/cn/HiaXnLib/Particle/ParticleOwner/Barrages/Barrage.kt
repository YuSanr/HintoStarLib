package cn.HiaXnLib.Particle.ParticleOwner.Barrages

import cn.HiaXnLib.Particle.ParticleOwner.Owner
import org.bukkit.Location
import org.bukkit.util.Vector
import java.util.LinkedList
import java.util.UUID

abstract class Barrage(var location:Location,val barrageUUID:UUID) :Owner{
    var effective = true
    abstract fun getBarrageTrackLocationList(startLocation: Location,endLocation:Location):LinkedList<Location>
    override fun getOwner(): Owner {
        return this
    }

    override fun getUUID(): UUID {
        return barrageUUID
    }

    override fun getParticleLocation(): Location {
        return location
    }

}