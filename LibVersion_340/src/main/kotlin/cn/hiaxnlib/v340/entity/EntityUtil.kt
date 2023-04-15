package cn.hiaxnlib.v340.entity

import cn.hiaxnlib.lib.entity.util.EntityUtil
import cn.hiaxnlib.lib.item.HiaXnTag
import cn.hiaxnlib.lib.version.VersionUtil
import org.bukkit.Bukkit
import org.bukkit.entity.Entity


class EntityUtil: EntityUtil {
    private val craftEntityCls = VersionUtil.getCraftClass("entity.CraftEntity")
    private val nmsEntityCls = VersionUtil.getNMSClass("Entity")
    private val nbtTagCompoundCLS = VersionUtil.getNMSClass("NBTTagCompound")

    override fun toNMSNBTTagObject(tag: HiaXnTag<*>): Any? {
        val nbtTagString = Class.forName("net.minecraft.server.${VersionUtil.getVersionString()}.NBTTagString")
        return nbtTagString.getConstructor(String::class.java).newInstance(tag.value.toString())
    }

    /**
     * 给实体添加一个NBT标签
     */
    override fun <T> setNBTTag(entity: T, key: String, tag: HiaXnTag<*>): T? {
//        val craftEntity = entity as CraftEntity
//        val nmsEntity = craftEntity.handle
//        val nmsTag = nmsEntity.nbtTag
//        nmsTag.setString(key,tag.value.toString())
//        nmsEntity.c(nmsTag)
        if (entity !is Entity) return null
        val nms = craftEntityCls.getMethod("getHandle").invoke(entity)
        val nmsTag =  nbtTagCompoundCLS.newInstance()
        nmsEntityCls.getMethod("e",nbtTagCompoundCLS).invoke(nms,nmsTag)
        nbtTagCompoundCLS.getMethod("setString",String::class.java,String::class.java).invoke(nmsTag,key,tag.value.toString())
        // setNBTTag 给混淆成f了
        nmsEntityCls.getMethod("f",nbtTagCompoundCLS).invoke(nms,nmsTag)
        return nmsEntityCls.getMethod("getBukkitEntity").invoke(nms) as T
    }

    override fun <T> getNBTTag(entity: T, key: String): String {
        if (entity !is Entity) return ""
        val nms = craftEntityCls.getMethod("getHandle").invoke(entity)
        val nmsTag =  nbtTagCompoundCLS.newInstance()
        nmsEntityCls.getMethod("e",nbtTagCompoundCLS).invoke(nms,nmsTag)
        return nbtTagCompoundCLS.getMethod("getString",String::class.java).invoke(nmsTag,key)as String
    }

    override fun <T> removeTag(entity: T, key: String): T? {
        if (entity !is Entity) return null
        val nms = craftEntityCls.getMethod("getHandle").invoke(entity)
        val nmsTag =  nbtTagCompoundCLS.newInstance()
        nmsEntityCls.getMethod("e",nbtTagCompoundCLS).invoke(nms,nmsTag)
        // setNBTTag 给混淆成f了
        nbtTagCompoundCLS.getMethod("remove",String::class.java).invoke(nmsTag,key)
        nmsEntityCls.getMethod("f",nbtTagCompoundCLS).invoke(nms,nmsTag)
        return nmsEntityCls.getMethod("getBukkitEntity").invoke(nms) as T
    }

}