package cn.hiaxnlib.listener

import cn.hiaxnlib.HiaXnLib
import cn.hiaxnlib.lib.entity.armorStand.actions.EntityInteractable
import org.bukkit.entity.ArmorStand
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.world.ChunkLoadEvent

class ArmorStandGroupListener:Listener {
    @EventHandler
    fun onDamage(event:EntityDamageByEntityEvent){
        if (event.entity !is ArmorStand) return
        val armorStand = event.entity as ArmorStand
        val from = HiaXnLib.getArmorStandManner().getNodeFromEntityUUID(armorStand.uniqueId)?:return
        val node = from.getRoot()
        node.damage(node.toRealDamage(event.damage),event.damager,from)
        event.isCancelled = true
    }
    @EventHandler
    fun onArmorInteract(event:PlayerInteractAtEntityEvent){
        val entity = event.rightClicked
        if (entity !is ArmorStand) return
        val node = HiaXnLib.getArmorStandManner().getStandGroup(entity.uniqueId)?:return
        if (node is EntityInteractable){
            node.onEntityClick(event)
        }
        event.isCancelled = true
    }
}