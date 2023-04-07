package cn.hiaxnlib.v340.entity

import cn.hiaxnlib.lib.entity.EntityUtil
import cn.hiaxnlib.lib.item.HiaXnTag
import cn.hiaxnlib.lib.version.VersionUtil
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
//        val nmsTag = nmsEntity.nbtTag
//        nmsTag.setString(key,tag.value.toString())
//        nmsEntity.f(nmsTag)
//        return nmsEntity.bukkitEntity
        val nms = craftEntityCls.getMethod("getHandle").invoke(entity)
        val nmsTag = nmsEntityCls.getMethod("getNBTTag").invoke(nms)
        nbtTagCompoundCLS.getMethod("setString",String::class.java,String::class.java).invoke(nmsTag,key,tag.value.toString())
        // setNBTTag 给混淆成f了
        nmsEntityCls.getMethod("f",nbtTagCompoundCLS).invoke(nms,nmsTag)
        return nmsEntityCls.getMethod("getBukkitEntity").invoke(nms) as Entity
    }

    override fun getNBTTag(entity: Entity, key: String): String? {
        val nms = craftEntityCls.getMethod("getHandle").invoke(entity)
        val nmsTag = nmsEntityCls.getMethod("getNBTTag").invoke(nms)
        return nbtTagCompoundCLS.getMethod("getString",String::class.java).invoke(nmsTag,key)as String
    }

    override fun removeTag(entity: Entity, key: String): Entity {
        val nms = craftEntityCls.getMethod("getHandle").invoke(entity)
        val nmsTag = nmsEntityCls.getMethod("getNBTTag").invoke(nms)?:let{
            return@let nbtTagCompoundCLS.newInstance()
        }
        // setNBTTag 给混淆成f了
        nbtTagCompoundCLS.getMethod("remove",String::class.java).invoke(nmsTag,key)
        nmsEntityCls.getMethod("f",nbtTagCompoundCLS).invoke(nms,nmsTag)
        return nmsEntityCls.getMethod("getBukkitEntity").invoke(nms) as Entity
    }
}