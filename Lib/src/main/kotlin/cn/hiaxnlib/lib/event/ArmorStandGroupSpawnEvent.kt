package cn.hiaxnlib.lib.event

import cn.hiaxnlib.lib.entity.armorStand.ArmorStandNode
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class ArmorStandGroupSpawnEvent(val node:ArmorStandNode):Event() {
    companion object{
        val handler = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList {
            return handler
        }
    }
    override fun getHandlers(): HandlerList {
        return handler
    }
}