package cn.HiaXnLib.Listener

import cn.HiaXnLib.Event.BarrageHitEvent
import cn.HiaXnLib.Particle.ParticleOwner.ParticlePlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class BarrageListener :Listener {
    @EventHandler
    fun onBarrageHit(event:BarrageHitEvent){
        if(event.barrage.barrageOwner is ParticlePlayer){
            val particlePlayer = event.barrage.barrageOwner as ParticlePlayer
            val player = particlePlayer.player
//            player.sendMessage("""
//                =======================
//                Barrage Hit type:${event.barrage.javaClass.typeName}
//                Hitted Type : ${event.hitted.javaClass.typeName}
//                =======================
//            """.trimIndent())

        }
    }
}