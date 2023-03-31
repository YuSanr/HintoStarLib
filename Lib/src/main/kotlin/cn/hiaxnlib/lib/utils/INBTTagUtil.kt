package cn.hiaxnlib.lib.utils

import cn.hiaxnlib.lib.item.HiaXnTag
import org.bukkit.inventory.ItemStack

interface INBTTagUtil {
    fun toNMSNBTTagObject(tag: HiaXnTag<*>):Any?
    fun setNBTTag(item:ItemStack,key:String,tag: HiaXnTag<*>):ItemStack
    fun getNBTTag(item:ItemStack,key:String):String?
    fun removeTag(item:ItemStack,key:String):ItemStack
    fun setSkullTexture(item:ItemStack,texture:String):ItemStack?
}