package cn.HiaXnLib.Particle.ParticleOwner.Barrages

import cn.HiaXnLib.Particle.util.ParticleMathUtil
import org.bukkit.Location
import java.util.*

class LineBarrage(location: Location, barrageUUID:UUID):Barrage(location,barrageUUID) {
    override fun getBarrageTrackLocationList(startLocation: Location, endLocation: Location): LinkedList<Location> {
        return LinkedList<Location>(ParticleMathUtil.getLineLocation(startLocation,endLocation,startLocation.distance(endLocation).toInt()))
    }

    override fun getParticleLocation(): Location {
        return location
    }
}