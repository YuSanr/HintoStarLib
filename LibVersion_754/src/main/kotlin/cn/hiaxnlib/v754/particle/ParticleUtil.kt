package cn.hiaxnlib.v754.particle

import cn.hiaxnlib.particle.HiaXnParticle
import org.bukkit.Location
import cn.hiaxnlib.lib.particle.ParticleUtil
import cn.hiaxnlib.lib.particle.particleData.DustOptions
import net.minecraft.server.v1_16_R3.PacketPlayOutWorldParticles
import net.minecraft.server.v1_16_R3.ParticleParam
import org.bukkit.Particle

//import org.bukkit.Location
//
class ParticleUtill :ParticleUtil{
        override fun spawnParticle(location: Location, particle: HiaXnParticle) {
//                val dustOptions = particle.config.data as DustOptions
//                val particleInstance = PacketPlayOutWorldParticles(
//                        ParticleParam.a.,
//                        particle.config.force,
//                        location.x.toFloat(),location.y.toFloat(),location.z.toFloat(),dustOptions.color.red/255f,dustOptions.color.green/255f,dustOptions.color.blue/255f,1f, 0
//                )
        }
}