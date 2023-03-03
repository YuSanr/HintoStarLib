package cn.hiaxnlib.v340.particle

import cn.hiaxnlib.lib.particle.ParticleUtil
import cn.hiaxnlib.lib.particle.particleData.DustOptions
import cn.hiaxnlib.lib.version.VersionUtil
import cn.hiaxnlib.particle.HiaXnParticle
import org.bukkit.Location

class ParticleUtil:ParticleUtil {
    override fun spawnParticle(location: Location, particle: HiaXnParticle) {
        /**
         * 1.8的粒子写的和shit一样
         *
         * 想要自定义颜色的RedStoneDust
         *
         * 之前的什么offX offY offZ全部变样
         * 在1.8
         * OffX = ColorRed /255f OffY = ColorGreen / 255F OffZ = ColorBlue/ 255F
         * count 必须为 0
         * speed >=1F
         * 在这种情况即使使用world.spigot().playEffect()也能修改Particle RedStoneDust的颜色
         * 这里用了包 PacketPlayOutWorldParticle
         */
        val craftCLS = Class.forName("net.minecraft.server.${VersionUtil.getVersionString()}.entity.CraftPlayer")
        if (particle.config.data is DustOptions){
            val dustOptions =  particle.config.data as DustOptions
            val particleEnum = Class.forName("net.minecraft.server.${VersionUtil.getVersionString()}.EnumParticle")
            val worldParticleCLS = Class.forName("net.minecraft.server.${VersionUtil.getVersionString()}.PacketPlayerOutWorldParticles")
            val worldParticle = worldParticleCLS.getConstructor(
                particleEnum,Boolean::class.java,Float::class.java,Float::class.java,Float::class.java,Float::class.java,Float::class.java,Float::class.java,Float::class.java,
                Int::class.java,IntArray::class.java
            ).newInstance(particleEnum.getMethod("valueOf",String::class.java).invoke(null,particle.config.type.toString()),particle.config.force,
                location.x.toFloat(),location.y.toFloat(),location.z.toFloat(),dustOptions.color.red/255f,dustOptions.color.green/255f,dustOptions.color.blue/255f,1f, 0
            )
            for (player in location.world.players) {
                val field = craftCLS.getField("playerConnection")
                val playerConnectionObject = field.get(player)
                val playerConnectionCls = playerConnectionObject::class.java
                playerConnectionCls.getMethod("sendPacket",worldParticleCLS).invoke(playerConnectionObject,worldParticle)
            }
        }else{
            val particleEnum = Class.forName("net.minecraft.server.${VersionUtil.getVersionString()}.EnumParticle")
            val worldParticleCLS = Class.forName("net.minecraft.server.${VersionUtil.getVersionString()}.PacketPlayerOutWorldParticles")
            val worldParticle = worldParticleCLS.getConstructor(
                particleEnum,Boolean::class.java,Float::class.java,Float::class.java,Float::class.java,Float::class.java,Float::class.java,Float::class.java,Float::class.java,
                Int::class.java,IntArray::class.java
            ).newInstance(particleEnum.getMethod("valueOf",String::class.java).invoke(null,particle.config.type.toString()),particle.config.force,
                location.x.toFloat(),location.y.toFloat(),location.z.toFloat(),particle.config.offX,particle.config.offY,particle.config.offZ,particle.config.extra, particle.config.count
            )
            for (player in location.world.players) {
                val field = craftCLS.getField("playerConnection")
                val playerConnectionObject = field.get(player)
                val playerConnectionCls = playerConnectionObject::class.java
                playerConnectionCls.getMethod("sendPacket",worldParticleCLS).invoke(playerConnectionObject,worldParticle)
            }
        }
    }
}