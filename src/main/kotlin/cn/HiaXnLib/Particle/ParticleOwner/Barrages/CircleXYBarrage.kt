package cn.HiaXnLib.Particle.ParticleOwner.Barrages

import cn.HiaXnLib.Particle.util.ParticleMathUtil
import org.bukkit.Location
import java.util.*

class CircleXYBarrage(location:Location, barrageUUID:UUID,var radius:Double):Barrage(location, barrageUUID) {
    override fun getBarrageTrackLocationList(startLocation: Location, endLocation: Location): LinkedList<Location> {
        return LinkedList(ParticleMathUtil.getXYCircleLocation(startLocation,radius,radius.toInt()*90))
    }
}