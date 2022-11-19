package cn.HiaXnLib.API.util

import org.bukkit.inventory.ItemStack

interface ItemNMS {
    /**
     * 设置物品的NBT
     * item 设置的物品
     * key NBT键
     * value NBT值
     * @return 返回设置后的ItemStack
     */
    fun setItemNBT(item:ItemStack,key:String,value:String):ItemStack

}