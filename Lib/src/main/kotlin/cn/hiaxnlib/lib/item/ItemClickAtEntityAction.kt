package cn.hiaxnlib.lib.item

import org.bukkit.event.player.PlayerInteractAtEntityEvent

interface ItemClickAtEntityAction {
    fun onItemClickAtEntity(event:PlayerInteractAtEntityEvent)
}