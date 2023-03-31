package cn.hiaxnlib.util

import cn.hiaxnlib.HiaXnLib
import cn.hiaxnlib.lib.item.HiaXnItem
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

object  InventoryUtil {
    fun hasHiaXnItem(item: HiaXnItem, inventory: Inventory):Boolean{

        val uuid = item.uuid
        for (content in inventory) {
            if (content == null || content.type == Material.AIR) continue
            if (HiaXnLib.isHiaXnItem(content)) {
                if(HiaXnLib.toHiaXnItem(content).uuid == uuid) return true
            }
        }
        return false
    }
    fun hasItemStack(item:ItemStack,inventory: Inventory):Boolean{
        for (content in inventory) {
            if (content == null || content.type == Material.AIR) continue
            if (content.isSimilar(item)){
                return true
            }
        }
        return false
    }
    fun getInventoryItemAmount(item:ItemStack,inventory: Inventory):Int{
        var result = 0
        for (i in 0 until inventory.size) {
            val itemStack = inventory.getItem(i)?:continue
            if (itemStack.isSimilar(item)) {
                result += itemStack.amount
            }
        }
        return result
    }
    fun removeItem(item:ItemStack,inventory: Inventory,count:Int){
        var c = count
        if (getInventoryItemAmount(item,inventory)> c){
            for (i in 0 until inventory.size) {
                val itemStack = inventory.getItem(i)?:continue
                if (c <=0) break
                if (itemStack.isSimilar(item)) {
                    if (itemStack.amount - c >0){
                        itemStack.amount -= c
                        inventory.setItem(i,itemStack)
                    }else if (itemStack.amount - c == 0){
                        inventory.setItem(i,null)
                    }else{
                        inventory.setItem(i,null)
                        c -= itemStack.amount
                    }
                }
            }
        }else if (getInventoryItemAmount(item,inventory) == c){
            inventory.remove(item)
        }
    }
}