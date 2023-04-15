package cn.hiaxnlib.lib.particle.particle

import cn.hiaxnlib.lib.particle.Particle
import org.bukkit.util.Vector

/**
 * 方向节点的设置要求
 */
class HiaXnDirectionParticle(particle: Particle,direction:Vector,speed:Double):HiaXnParticle(
    ParticleConfig(particle,speed,0,direction.x,direction.y,direction.z,null,true)) {
}