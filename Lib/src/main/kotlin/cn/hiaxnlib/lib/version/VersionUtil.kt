package cn.hiaxnlib.lib.version

import cn.hiaxnlib.lib.entity.EntityUtil
import cn.hiaxnlib.lib.particle.ParticleUtil
import cn.hiaxnlib.lib.utils.INBTTagUtil
import org.bukkit.Bukkit

object VersionUtil {
    fun getParticleUtil(): ParticleUtil {
        val versionParticleUtilCLS = Class.forName("cn.hiaxnlib.v${toVersionNumber()}.particle.ParticleUtil")
        return versionParticleUtilCLS.newInstance() as ParticleUtil
    }
    fun getNBTTagUtil():INBTTagUtil{
        val versionParticleUtilCLS = Class.forName("cn.hiaxnlib.v${toVersionNumber()}.utils.NBTTagUtil")
        return versionParticleUtilCLS.newInstance() as INBTTagUtil
    }

    /**
     * 获取能够操作实体NBT的工具类
     */
    fun getEntityUtil():EntityUtil{
        val versionEntityUtilCLS = Class.forName("cn.hiaxnlib.v${toVersionNumber()}.entity.EntityUtil")
        return versionEntityUtilCLS.newInstance() as EntityUtil
    }
    fun getVersionString():String{
        val split =
            Bukkit.getServer()::class.java.getPackage().toString().split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
        val split1 = split[3].split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return split1[0]
    }
    fun toVersionNumber():Int{
        val version = Bukkit.getBukkitVersion()
//        println("version:$version")
        val toInt = version.split(".")[1].toInt()
        if (toInt<8){
            return 40 // 1.6
        }else if (toInt in 8..12){
            return 340 //1.12.2
        }else if (toInt in 13..14){
            return 498 // 1.14
        }else if (toInt in 15..16){
            return 754
        }else if (toInt in 17..19){
            return 761
        }
        return 0
    }

    /**
     *  指定类路径 net.minecraft.server.当前版本.clsPath  （精确到个类）
     */
    fun getNMSClass(clsPath:String):Class<*>{
        return Class.forName("net.minecraft.server.${getVersionString()}.$clsPath")
    }

    /**
     * 指定类路径 org.bukkit.craftbukkit.版本.类路径（精确到个类）
     */
    fun getCraftClass(clsPath:String):Class<*>{
        return Class.forName("org.bukkit.craftbukkit.${getVersionString()}.${clsPath}")
    }
}