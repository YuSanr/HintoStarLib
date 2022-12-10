package cn.HiaXnLib.Particle.HiaXnParticles

import cn.HiaXnLib.Particle.HiaXnParticle
import cn.HiaXnLib.Particle.HiaXnParticleGroup
import cn.HiaXnLib.Particle.RelativeLocation
import org.bukkit.Location
import org.bukkit.util.Vector
import kotlin.math.pow

class ArrowParticleGroup(origin: Location?,val particle:HiaXnParticle) : HiaXnParticleGroup(origin) {
    var axisPoint:RelativeLocation? = null
    override fun getParticleLocationMap(): LinkedHashMap<RelativeLocation, HiaXnParticle> {
        // 以X为对称轴 生成一个箭头[XZ平面]
        val startLocation = RelativeLocation(0.0,0.0,0.0)
        val endLocation = RelativeLocation(4.0,0.0,0.0)
        val arrow_Left = RelativeLocation(4-3.0.pow(0.5),0.0,1.0)
        val arrow_Right = RelativeLocation(4-3.0.pow(0.5),0.0,-1.0)
        val axisLocationList = getLineLocation(startLocation.clone(),endLocation.clone(),10)
        val arrowLeftList = getLineLocation(endLocation.clone(),arrow_Left,10)
        val arrowRightList = getLineLocation(endLocation.clone(),arrow_Right,10)
        val result = LinkedHashMap<RelativeLocation,HiaXnParticle>()
        for (i in axisLocationList){
            result[i] = particle
        }
        for (i in arrowLeftList){
            result[i] = particle
        }
        for (i in arrowRightList){
            result[i] = particle
        }
        axisPoint =endLocation.clone()
        return result
    }

    override fun clone(): HiaXnParticleGroup {
        return ArrowParticleGroup(origin,particle)
    }

    override fun clone(location: Location): HiaXnParticleGroup {
        return ArrowParticleGroup(location,particle)
    }

    private fun getLineLocation(startLocation:RelativeLocation,endLocation:RelativeLocation,pointNumber:Int = 100):ArrayList<RelativeLocation>{
        val list = ArrayList<RelativeLocation>()
        // 取直线向量
        val lineVector = Vector(endLocation.x - startLocation.x,endLocation.y - startLocation.y,endLocation.z - startLocation.z)
        val addVector = lineVector.clone().multiply(1.0/pointNumber)
        list.add(startLocation.clone())
        for (i in 1 .. pointNumber) {
            startLocation.x += addVector.x
            startLocation.y += addVector.y
            startLocation.z += addVector.z
            list.add(startLocation.clone())
        }
        return list
    }
}