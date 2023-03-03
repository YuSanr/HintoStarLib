package cn.hiaxnlib.lib.item

import org.bukkit.event.player.PlayerInteractEvent

interface ItemClickAction {
    fun onItemClick(event:PlayerInteractEvent)
}