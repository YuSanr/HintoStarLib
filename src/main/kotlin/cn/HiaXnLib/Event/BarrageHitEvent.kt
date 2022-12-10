package cn.HiaXnLib.Event

import cn.HiaXnLib.Particle.ParticleOwner.Barrages.Barrage
import cn.HiaXnLib.Particle.ParticleOwner.Owner
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * @param barrage 击中的弹幕
 * @param hitted 被击中的Owner 可以是位置 玩家 实体
 */
class BarrageHitEvent(val barrage:Barrage,val hitted: Owner):Event() {
    companion object{
        val handler = HandlerList()
        @JvmStatic
        fun getHandlerList():HandlerList{
            return handler
        }
    }
    override fun getHandlers(): HandlerList {
        return handler
    }
}