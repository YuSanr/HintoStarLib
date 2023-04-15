package cn.hiaxnlib.lib.entity.armorStand.actions

import org.bukkit.event.player.PlayerInteractAtEntityEvent

interface EntityInteractable {
    /**
     * 玩家右键这个盔甲架组合时
     * 会执行这个函数
     */
    fun onEntityClick(event:PlayerInteractAtEntityEvent)
}