package cn.hiaxnlib.listener

import cn.hiaxnlib.HiaXnLib
import cn.hiaxnlib.lib.item.ItemClickAction
import cn.hiaxnlib.lib.item.ItemClickAtEntityAction
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

class ItemActionListener:Listener {
    @EventHandler (priority = EventPriority.HIGHEST)
    fun onItemClick(event:PlayerInteractEvent){
        val item = event.item?:return
        if (HiaXnLib.isHiaXnItem(item)){
            val hiaxnItem = HiaXnLib.toHiaXnItem(item) ?:return
            if (hiaxnItem is ItemClickAction){
                (hiaxnItem as ItemClickAction).onItemClick(event)
            }
        }
    }
    @EventHandler (priority = EventPriority.HIGHEST)
    fun onItemClickAtEntity(event:PlayerInteractAtEntityEvent){
        val item = event.player.itemInHand?:return
        if (HiaXnLib.isHiaXnItem(item)){
            val hiaxnItem = HiaXnLib.toHiaXnItem(item) ?:return
            if (hiaxnItem is ItemClickAtEntityAction){
                (hiaxnItem as ItemClickAtEntityAction).onItemClickAtEntity(event)
            }
        }
    }
}