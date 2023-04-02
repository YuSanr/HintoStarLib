package cn.hiaxnlib.lib.gui

import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class AirButton:AbstractButton(ItemStack(Material.AIR)) {
    override fun onItemClicked(event: InventoryClickEvent, clickGUI:AbstractGUI) {
    }
}