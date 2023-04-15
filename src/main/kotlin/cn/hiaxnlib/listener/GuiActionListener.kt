package cn.hiaxnlib.listener

import cn.hiaxnlib.lib.event.GuiCloseEvent
import cn.hiaxnlib.lib.event.GuiOpenEvent
import cn.hiaxnlib.lib.managers.GUIManager
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
//        Bukkit.broadcastMessage("value: ${
//            GUIManner.isHiaXnGui(player.openInventory)
//        }, UID:${GUIManner.getUID(player.openInventory).toString().replace("§","&")}" +
//                "Register:${GUIManner.isRegister(GUIManner.getUID(player.openInventory))}")
        if (GUIManager.isHiaXnGui(player.openInventory)) {
            val gui = GUIManager.playerOpenGUI[player.uniqueId]?:return
            if (!gui.moved){
                event.isCancelled = true
            }
            if (event.clickedInventory != gui.inventory)return
            val button = gui.getButton(event.slot)?:return
            if (!button.moved){
                event.isCancelled = true
            }
            button.onItemClicked(event,gui)
        }
    }
    @EventHandler
    fun onInventoryOpen(event:InventoryOpenEvent){
        val player = event.player
        if (GUIManager.isHiaXnGui(player.openInventory)){
            player.openInventory.title
            val gui = GUIManager.playerOpenGUI[player.uniqueId]?:return
            val openEvent = GuiOpenEvent(gui)
            gui.onGUIOpen(openEvent)
            Bukkit.getPluginManager().callEvent(openEvent)
        }
    }
    @EventHandler
    fun onInventoryClose(event:InventoryCloseEvent){
        val player = event.player
        if (GUIManager.isHiaXnGui(player.openInventory)){
            val gui = GUIManager.playerOpenGUI[player.uniqueId]?:return
            val closeEvent = GuiCloseEvent(gui)
            gui.onGUIClose(closeEvent)
            GUIManager.playerOpenGUI.remove(player.uniqueId)
            Bukkit.getPluginManager().callEvent(closeEvent)
        }
    }
}