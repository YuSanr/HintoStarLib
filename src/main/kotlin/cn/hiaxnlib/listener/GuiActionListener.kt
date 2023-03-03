package cn.hiaxnlib.listener

import cn.hiaxnlib.lib.event.GuiCloseEvent
import cn.hiaxnlib.lib.event.GuiOpenEvent
import cn.hiaxnlib.lib.manner.GUIManner
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class GuiActionListener:Listener {
    @EventHandler
    fun onInventoryClick(event:InventoryClickEvent){
        val player = event.whoClicked as Player
        if (GUIManner.isHiaXnGui(player.openInventory)) {
            val gui = GUIManner.playerOpenGUI[player.uniqueId]?:return
            if (!gui.moved){
                event.isCancelled = true
            }
            if (event.clickedInventory != gui.inventory)return
            val button = gui.getButton(event.slot)?:return
            if (!button.moved){
                event.isCancelled = true
            }
            button.onItemClicked(event)
        }
    }
    @EventHandler
    fun onInventoryOpen(event:InventoryOpenEvent){
        val player = event.player
        if (GUIManner.isHiaXnGui(player.openInventory)){
            player.openInventory.title
            val gui = GUIManner.playerOpenGUI[player.uniqueId]?:return
            val openEvent = GuiOpenEvent(gui)
            gui.onGUIOpen(openEvent)
            Bukkit.getPluginManager().callEvent(openEvent)
        }
    }
    @EventHandler
    fun onInventoryClose(event:InventoryCloseEvent){
        val player = event.player
        if (GUIManner.isHiaXnGui(player.openInventory)){
            val gui = GUIManner.playerOpenGUI[player.uniqueId]?:return
            val closeEvent = GuiCloseEvent(gui)
            gui.onGUIClose(closeEvent)
            GUIManner.playerOpenGUI.remove(player.uniqueId)
            Bukkit.getPluginManager().callEvent(closeEvent)
        }
    }
}