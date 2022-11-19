package cn.HiaXnLib.Lib.v1_8_R3.util

import cn.HiaXnLib.API.util.ItemNMS
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack

class LibItemNMS:ItemNMS {
    override fun setItemNBT(item: ItemStack, key: String, value: String): ItemStack {
        val craftItem = item as CraftItemStack
        val nms = CraftItemStack.asNMSCopy(item)
        return CraftItemStack.asBukkitCopy(
            nms.apply {
            tag.setString(key,value)
        })
    }
}