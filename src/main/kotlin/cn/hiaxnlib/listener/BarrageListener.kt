package cn.hiaxnlib.listener

import cn.hiaxnlib.HiaXnLib
import cn.hiaxnlib.event.BarrageHitEvent
import cn.hiaxnlib.lib.owner.OwnerPlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class BarrageListener :Listener {
    @EventHandler
    fun onBarrageHit(event:BarrageHitEvent){
        HiaXnLib.getBarrageManner().aliveBarrages.remove(event.barrage)
    }
}