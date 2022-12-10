package cn.HiaXnLib.Particle.ParticleStyle

import cn.HiaXnLib.Particle.HiaXnParticle
import cn.HiaXnLib.Particle.HiaXnParticleGroup
import cn.HiaXnLib.Particle.HiaXnParticleStyle
import cn.HiaXnLib.Particle.HiaXnParticles.PointParticleGroup
import cn.HiaXnLib.Particle.RelativeLocation
import cn.HiaXnLib.Particle.util.ParticleMathUtil
import org.bukkit.Location
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.PI

class BallParticleStyle(val particle:HiaXnParticle): HiaXnParticleStyle() {
    var ballRad = 0.0
    override fun getParticleList(location: Location): LinkedList<HiaXnParticleGroup> {
        val list = ArrayList<RelativeLocation>()
        val res_list = LinkedList<HiaXnParticleGroup>()
        for (loc in ParticleMathUtil.getXZCircleLocation(location, 3.0, 32)) {
            list.add(RelativeLocation(-location.x + loc.x,loc.y - location.y,loc.z - location.z))
        }
        ParticleMathUtil.rotationPoint(list,PI/4,ballRad)
        for (relativeLocation in list) {
            val clone = location.clone()
            clone.x += relativeLocation.x
            clone.y += relativeLocation.y
            clone.z += relativeLocation.z
            res_list.add(PointParticleGroup(clone,particle))
        }
        return res_list
    }

    override fun updateParticleLocation() {
        ballRad += PI/360
    }

    override fun cancelStrategy(): Boolean {
        return false
    }
}