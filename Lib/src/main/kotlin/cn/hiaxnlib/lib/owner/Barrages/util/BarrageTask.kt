package cn.hiaxnlib.lib.owner.Barrages.util

import cn.hiaxnlib.lib.owner.Barrages.Barrage
import org.bukkit.Location
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import java.util.UUID

class BarrageTask:BukkitRunnable (),Comparable<BarrageTask>{
    private var activeBarrage = ArrayList<Barrage>()

    override fun run() {
//        val iterator = activeBarrage.iterator()
        val clone = activeBarrage.clone() as ArrayList<Barrage>
        for (barrage in clone) {
            barrage.displayParticle()
            barrage.tickAction()
            val hit = barrage.checkBarrageHit()
            if (hit != null){
                barrage.hitMethod(hit)
                activeBarrage.remove(barrage)
            }
            barrage.updateStyleTick()
            barrage.nextLocation()
        }
//        while (iterator.hasNext()){
//            val barrage = iterator.next()
//            barrage.displayParticle()
//            val hit = barrage.checkBarrageHit()
//            if (hit != null){
//                barrage.hitMethod(hit)
//                iterator.remove()
//            }
//            barrage.updateStyleTick()
//            barrage.nextLocation()
//        }
    }
   fun addTask(barrage:Barrage){
       activeBarrage.add(barrage)
   }

    override fun compareTo(other: BarrageTask): Int {
        return activeBarrage.size - other.activeBarrage.size
    }
}