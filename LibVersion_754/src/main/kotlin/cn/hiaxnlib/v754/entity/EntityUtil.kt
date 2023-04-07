package cn.hiaxnlib.v754.entity

import cn.hiaxnlib.lib.entity.EntityUtil
import cn.hiaxnlib.lib.item.HiaXnTag
import cn.hiaxnlib.lib.version.VersionUtil
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity
import org.bukkit.entity.Entity

class EntityUtil:EntityUtil {
    private val craftEntityCls = VersionUtil.getCraftClass("entity.CraftEntity")
    private val nmsEntityCls = VersionUtil.getNMSClass("Entity")
    private val nbtTagCompoundCLS = VersionUtil.getNMSClass("NBTTagCompound")

    override fun toNMSNBTTagObject(tag: HiaXnTag<*>): Any? {
        val nbtTagString = Class.forName("net.minecraft.server.${VersionUtil.getVersionString()}.NBTTagString")
        return nbtTagString.getConstructor(String::class.java).newInstance(tag.value.toString())
    }

    /**
     * 给实体添加一个NBT标签 此时这个实体不出意外已经生成了草
     */
    override fun setNBTTag(entity: Entity, key: String, tag: HiaXnTag<*>): Entity {
//        val craftEntity = entity as CraftEntity
//        val nmsEntity = craftEntity.handle
//        val nmsTag = nmsEntity.save()
//        nmsTag.setString(key,tag.value.toString())
//        nmsEntity.load(nmsTag)
//        return nmsEntity.bukkitEntity
        val nms = craftEntityCls.getMethod("getHandle").invoke(entity)
        val nmsTag = nbtTagCompoundCLS.newInstance()
        nmsEntityCls.getMethod("save",nbtTagCompoundCLS).invoke(nms,nmsTag)
        nbtTagCompoundCLS.getMethod("setString",String::class.java,String::class.java).invoke(nmsTag,key,tag.value.toString())
        // 加载实体NBT数据
        nmsEntityCls.getMethod("load",nbtTagCompoundCLS).invoke(nms,nmsTag)
        return nmsEntityCls.getMethod("getBukkitEntity").invoke(nms) as Entity
    }

    override fun getNBTTag(entity: Entity, key: String): String? {
        val nms = craftEntityCls.getMethod("getHandle").invoke(entity)
        val nmsTag = nbtTagCompoundCLS.newInstance()
        nmsEntityCls.getMethod("save",nbtTagCompoundCLS).invoke(nms,nmsTag)
        return nbtTagCompoundCLS.getMethod("getString",String::class.java).invoke(nmsTag,key)as String
    }

    override fun removeTag(entity: Entity, key: String): Entity {
        val nms = craftEntityCls.getMethod("getHandle").invoke(entity)
        val nmsTag = nbtTagCompoundCLS.newInstance()
        nmsEntityCls.getMethod("save",nbtTagCompoundCLS).invoke(nms,nmsTag)
        // 加载实体NBT数据
        nbtTagCompoundCLS.getMethod("remove",String::class.java).invoke(nmsTag,key)
        nmsEntityCls.getMethod("load",nbtTagCompoundCLS).invoke(nms,nmsTag)
        return nmsEntityCls.getMethod("getBukkitEntity").invoke(nms) as Entity
    }
}