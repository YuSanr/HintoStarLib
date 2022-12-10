package cn.HiaXnLib.Particle.HiaXnParticles

import cn.HiaXnLib.Particle.HiaXnParticle
import cn.HiaXnLib.Particle.HiaXnParticleGroup
import cn.HiaXnLib.Particle.RelativeLocation
import cn.HiaXnLib.Particle.util.ParticleMathUtil
import org.bukkit.Location

class SwordParticleGroup(origin:Location?,var particle: HiaXnParticle):HiaXnParticleGroup(origin) {
    // 对称轴
    init {
        axis = RelativeLocation(4.0,0.0,0.0)
    }
    private var bufferedLocationMap = LinkedHashMap<RelativeLocation,HiaXnParticle>()
    override fun getParticleLocationMap(): LinkedHashMap<RelativeLocation, HiaXnParticle> {
        if(bufferedLocationMap.isEmpty()){
            val startLocation = RelativeLocation(0.0,0.0,0.0)
            val endAxisLocation = axis.clone()
            val swordStartLocation = RelativeLocation(1.0,0.0,1.0)
            val swordEndLocation = RelativeLocation(1.0,0.0,-1.0)
            val result = LinkedHashMap<RelativeLocation,HiaXnParticle>()
            val axisList = ParticleMathUtil.getLineLocation(startLocation,endAxisLocation,20)
            val swordList = ParticleMathUtil.getLineLocation(swordStartLocation,swordEndLocation,20)
            for (loc in axisList){
                result[loc.clone()] = particle
            }
            for (loc in swordList){
                result[loc.clone()] = particle
            }
            bufferedLocationMap = result
        }
        val result = LinkedHashMap<RelativeLocation,HiaXnParticle>()
        for (mutableEntry in bufferedLocationMap) {
            result[mutableEntry.key.clone()] = mutableEntry.value
        }
        return result
    }
    override fun clone(): HiaXnParticleGroup {
        return SwordParticleGroup(origin,particle)
    }

    override fun clone(location: Location): HiaXnParticleGroup {
        return SwordParticleGroup(location,particle)
    }
}