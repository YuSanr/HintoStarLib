package cn.hiaxnlib.lib.entity.util

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
    fun <T> setNBTTag(entity:T, key:String, tag: HiaXnTag<*>): T?

    /**
     * 获取实体的NBT
     */
    fun <T> getNBTTag(entity: T, key:String):String?

    /**
     * 删除实体的NBT 不用重新生成实体
     */
    fun <T> removeTag(entity: T, key:String): T?
}