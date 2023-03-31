package cn.hiaxnlib.v754.particle

import cn.hiaxnlib.particle.HiaXnParticle
import org.bukkit.Location
import cn.hiaxnlib.lib.particle.ParticleUtil
import cn.hiaxnlib.lib.particle.particleData.DustOptions
import cn.hiaxnlib.lib.version.VersionUtil
import org.bukkit.Particle

//import org.bukkit.Location
//
class ParticleUtill :ParticleUtil{
        override fun spawnParticle(location: Location, particle: HiaXnParticle) {
            // 导包方法
            //import cn.hiaxnlib.lib.particle.particleData.DustOptions
            //import net.minecraft.server.v1_16_R3.PacketPlayOutWorldParticles
            //import net.minecraft.server.v1_16_R3.ParticleParam
            //import org.bukkit.Color
            //import org.bukkit.Particle
            //import org.bukkit.craftbukkit.v1_16_R3.CraftParticle
            //import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer
//               val pNMS =  CraftParticle.toNMS(Particle.REDSTONE,DustOptions(Color.fromRGB(0x000000),0.1))
//                val particlePacket = PacketPlayOutWorldParticles(
//                    pNMS,particle.config.force,location.x,location.y,location.z,
//                    particle.config.offX.toFloat(),particle.config.offY.toFloat(),particle.config.offZ.toFloat(),
//                    particle.config.extra.toFloat(),particle.config.count
//                )
//            for (player in location.world!!.players) {
//                player as CraftPlayer
//                player.handle.playerConnection.sendPacket(particlePacket)
//            }

            // 非导包方法

            val craftParticleCLS = VersionUtil.getCraftClass("CraftParticle")
            val packetParticleCLS = VersionUtil.getNMSClass("PacketPlayOutWorldParticles")
            val packet = if (particle.config.data is DustOptions){
                val libDust = particle.config.data as DustOptions
                val bukkitDust = Particle.DustOptions(libDust.color,libDust.size.toFloat())
                val craftParticle = craftParticleCLS.getMethod("toNMS",Particle::class.java,Particle.DustOptions::class.java).invoke(null,Particle.valueOf(particle.config.type.toString()),bukkitDust)
                packetParticleCLS.getConstructor(
                    craftParticle::class.java,Boolean::class.java,Double::class.java,Double::class.java,Double::class.java,Float::class.java,Float::class.java,Float::class.java,
                    Float::class.java,Int::class.java).newInstance(craftParticle,particle.config.force,location.x,location.y,location.z,
                        particle.config.offX.toFloat(),particle.config.offY.toFloat(),particle.config.offZ.toFloat(),
                        particle.config.extra.toFloat(),particle.config.count
                    )
            }else{
                val craftParticle = craftParticleCLS.getMethod("toNMS",Particle::class.java).invoke(null,Particle.valueOf(particle.config.type.toString()))
                packetParticleCLS.getConstructor(
                    craftParticle::class.java,Boolean::class.java,Double::class.java,Double::class.java,Double::class.java,Float::class.java,Float::class.java,Float::class.java,
                    Float::class.java,Int::class.java).newInstance(craftParticle,particle.config.force,location.x,location.y,location.z,
                    particle.config.offX.toFloat(),particle.config.offY.toFloat(),particle.config.offZ.toFloat(),
                    particle.config.extra.toFloat(),particle.config.count
                )
            }
            val craftPlayerCLS = VersionUtil.getCraftClass("entity.CraftPlayer")
           for (player in location.world!!.players) {
               val handle = craftPlayerCLS.getMethod("getHandle").invoke(player)
               val entityPlayerCLS = handle::class.java
               val playerConnection = entityPlayerCLS.getField("playerConnection").get(handle)
                playerConnection::class.java.getMethod("sendPacket",packetParticleCLS).invoke(playerConnection,packet)
           }
        }
}