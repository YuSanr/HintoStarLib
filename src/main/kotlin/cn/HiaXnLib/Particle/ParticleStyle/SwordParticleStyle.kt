package cn.HiaXnLib.Particle.ParticleStyle

import cn.HiaXnLib.Particle.HiaXnParticle
import cn.HiaXnLib.Particle.HiaXnParticleGroup
import cn.HiaXnLib.Particle.HiaXnParticleStyle
import cn.HiaXnLib.Particle.HiaXnParticles.PointParticleGroup
import cn.HiaXnLib.Particle.HiaXnParticles.SwordParticleGroup
import cn.HiaXnLib.Particle.ParticleOwner.Owner
import cn.HiaXnLib.Particle.RelativeLocation
import cn.HiaXnLib.Particle.util.ParticleMathUtil
import org.bukkit.Location
import java.util.*

class SwordParticleStyle(var target:Owner,particle:HiaXnParticle): HiaXnParticleStyle() {
    val group = SwordParticleGroup(null,particle)
    var time=0
    override fun getParticleList(location: Location): LinkedList<HiaXnParticleGroup> {
        val list = LinkedList<HiaXnParticleGroup>()
        val keyList = LinkedList<RelativeLocation>()
        val particleList = LinkedList<HiaXnParticle>()
        for (loc in group.getParticleLocationMap()) {
            keyList.add(loc.key)
            particleList.add(loc.value)
        }
        val startLocation = group.axis
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
        time++
    }

    override fun cancelStrategy(): Boolean {
        return time>100
    }
}