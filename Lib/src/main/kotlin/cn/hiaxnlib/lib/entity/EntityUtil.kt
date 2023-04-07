package cn.hiaxnlib.lib.entity

import cn.hiaxnlib.lib.item.HiaXnTag
import org.bukkit.entity.Entity

/**
 * 实体NBT操作工具的接口
 */
interface EntityUtil {
    /**
     *  将HiaXnTag 转换为NBTTagCompound 对象
     */
    fun toNMSNBTTagObject(tag: HiaXnTag<*>):Any?

    /**
     * 给实体加载一个NBT 不用重新生成实体
     */
    fun setNBTTag(entity:Entity, key:String, tag: HiaXnTag<*>): Entity

    /**
     * 获取实体的NBT
     */
    fun getNBTTag(entity: Entity, key:String):String?

    /**
     * 删除实体的NBT 不用重新生成实体
     */
    fun removeTag(entity: Entity, key:String): Entity

}