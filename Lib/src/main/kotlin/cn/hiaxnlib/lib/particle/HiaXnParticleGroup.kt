package cn.hiaxnlib.particle

import cn.hiaxnlib.lib.version.VersionUtil
import org.bukkit.Location

/**
 * @param origin 粒子相对原点 粒子生成位置以该原点生成 考虑到原点发生改变 因此可以修改
 */
abstract class HiaXnParticleGroup(){
   abstract fun getShapeParticleMap():ArrayList<Pair<RelativeLocation,HiaXnParticle>>
   private val util = VersionUtil.getParticleUtil()
   fun display(origin:Location){
       for (entry in getShapeParticleMap()) {
           val loc = origin.clone().also {
               it.x += entry.first.x
               it.y += entry.first.y
               it.z += entry.first.z
           }
           util.spawnParticle(loc,entry.second)
       }
   }
}