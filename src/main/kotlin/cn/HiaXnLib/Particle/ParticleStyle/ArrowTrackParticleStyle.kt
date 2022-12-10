package cn.HiaXnLib.Particle.ParticleStyle

import cn.HiaXnLib.Particle.HiaXnParticle
import cn.HiaXnLib.Particle.HiaXnParticleGroup
import cn.HiaXnLib.Particle.HiaXnParticleStyle
import cn.HiaXnLib.Particle.HiaXnParticles.ArrowParticleGroup
import cn.HiaXnLib.Particle.HiaXnParticles.PointParticleGroup
import cn.HiaXnLib.Particle.ParticleOwner.ParticlePlayer
import cn.HiaXnLib.Particle.ParticleOwner.ParticlePoint
import cn.HiaXnLib.Particle.RelativeLocation
import cn.HiaXnLib.Particle.util.ParticleMathUtil
import org.bukkit.Location
import org.bukkit.Particle
import java.util.*
import kotlin.math.PI

// Group仅仅存放粒子信息
// 重新生成Group Point
class ArrowTrackParticleStyle(private val target: ParticlePoint, particle:HiaXnParticle): HiaXnParticleStyle() {
    private val group = ArrowParticleGroup(Location(null,0.0,0.0,0.0),particle)
    override fun getParticleList(location: Location): LinkedList<HiaXnParticleGroup> {
        val list = LinkedList<HiaXnParticleGroup>()
        val keyList = LinkedList<RelativeLocation>()
        val particleList = LinkedList<HiaXnParticle>()
        for (loc in group.getParticleLocationMap()) {
            keyList.add(loc.key)
            particleList.add(loc.value)
        }
        val startLocation = group.axisPoint
        val endLocation = RelativeLocation(target.getParticleLocation().x - location.x,target.getParticleLocation().y - location.y, target.getParticleLocation().z - location.z)
        val targetYaw = ParticleMathUtil.toPointYaw(startLocation!!,endLocation)
        val targetPitch = ParticleMathUtil.toPointPitch(startLocation,endLocation)
        ParticleMathUtil.rotationPoint(keyList, targetYaw,targetPitch) as LinkedList<RelativeLocation>
        for (i in 0 until keyList.size) {
            val locClone = location.clone()
            locClone.x += keyList[i].x
            locClone.y += keyList[i].y
            locClone.z += keyList[i].z
            list.add(PointParticleGroup(locClone, particleList[i]))
        }
        return list
    }

    override fun updateParticleLocation() {

    }

    override fun cancelStrategy(): Boolean {
        return false
    }
}