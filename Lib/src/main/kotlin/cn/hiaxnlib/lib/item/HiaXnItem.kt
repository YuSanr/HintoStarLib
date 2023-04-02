package cn.hiaxnlib.lib.item

import cn.hiaxnlib.lib.version.VersionUtil
import org.bukkit.inventory.ItemStack

abstract class HiaXnItem(var item:ItemStack,var uuid:String) {
    init {
        setNBTTag("UUID", HiaXnTag<String>(uuid))
    }
    fun setLore(vararg lore:String){
        item.itemMeta = item.itemMeta.apply {
            setLore(*lore)
        }
    }
    fun getLore(): MutableList<String>? {
        if (item.itemMeta == null) return null
        return item.itemMeta!!.lore
    }
    fun setNBTTag(key:String,tag: HiaXnTag<String>){
        val nbtUtil = VersionUtil.getNBTTagUtil()
        item = nbtUtil.setNBTTag(item,key,tag)
    }
    fun getNBTTag(key:String): HiaXnTag<String>?{
        val nbtUtil = VersionUtil.getNBTTagUtil()
        val value = nbtUtil.getNBTTag(item, key)?:return null
        return HiaXnTag<String>(value)
    }
    fun removeNBTTag(key: String){
        val nbtUtil = VersionUtil.getNBTTagUtil()
        item = nbtUtil.removeTag(item,key)
    }

}