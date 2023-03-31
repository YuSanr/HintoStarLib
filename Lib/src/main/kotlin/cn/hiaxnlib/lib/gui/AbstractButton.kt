package cn.hiaxnlib.lib.gui

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

abstract class AbstractButton(var item:ItemStack,var moved:Boolean = false) {
    abstract fun onItemClicked(event:InventoryClickEvent,clickGUI:AbstractGUI)
}