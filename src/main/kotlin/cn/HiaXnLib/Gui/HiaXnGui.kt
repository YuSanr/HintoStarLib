package cn.HiaXnLib.Gui

import cn.HiaXnLib.Gui.utils.HiaXnGUIUtils
import cn.HiaXnLib.item.HiaXnItemStack
import cn.HiaXnLib.item.utils.ItemStackUtils
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory

open class HiaXnGui(var uuid:String,var inventory:Inventory){
    // HiaXnGui必须设置HiaXnItemStack
    var itemUtil = ItemStackUtils()
    companion object Register{
        var registeredGUI = HashSet<HiaXnGui>()
        fun registerGUIs(vararg guis: HiaXnGui){
            registeredGUI.addAll(guis)
        }
    }
    fun openGUI(player:Player){
        player.openInventory(inventory)
    }
    fun setItem(item: HiaXnItemStack, slot:Int): HiaXnGui {
        inventory.setItem(slot,item.item)
        return this
    }
    fun register(){
        registerGUIs(this)
    }
    fun invokeItemAction(item: HiaXnItemStack, args:Array<Any?>){
        item.invokeAction(args)
    }
    fun isRegisteredGUI(inventory: HiaXnGui):Boolean{
        return registeredGUI.contains(inventory)
    }
    fun setTitle(title:String){
        inventory = if (inventory.type !=InventoryType.CHEST){
            Bukkit.createInventory(inventory.holder,inventory.size,title).also {
                it.contents = inventory.contents
            }
        }else{
            Bukkit.createInventory(inventory.holder,inventory.type,title).also {
                it.contents = inventory.contents
            }
        }
    }
}