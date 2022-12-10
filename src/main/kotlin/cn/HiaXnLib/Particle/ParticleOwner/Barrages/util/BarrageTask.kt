package cn.HiaXnLib.Particle.ParticleOwner.Barrages.util

import cn.HiaXnLib.Particle.ParticleOwner.Barrages.Barrage
import cn.HiaXnLib.Particle.ParticleOwner.Barrages.util.BarrageTask.Companion.runningBarrageTaskMap
import cn.HiaXnLib.main
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import java.util.UUID

class BarrageTask: BukkitRunnable() {
    companion object{
        val activeBarrageSet = HashSet<Barrage>()
        val runningBarrageTaskMap = HashMap<UUID,ArrayList<RunningBarrageTask>>()
    }
    override fun run() {
        for (barrage in activeBarrageSet) {
            if (!runningBarrageTaskMap.containsKey(barrage.barrageOwner.getUUID())){
                runningBarrageTaskMap[barrage.barrageOwner.getUUID()] = ArrayList<RunningBarrageTask>()
            }
            val subList = runningBarrageTaskMap[barrage.barrageOwner.getOwner().getUUID()]?:continue
            if (!containsPair(barrage,barrage.barrageOwner.getUUID())){
                subList.add(
                    RunningBarrageTask(BarrageRunnable(barrage).runTaskTimerAsynchronously(main.getInstance(),5,1),barrage)
                )
            }
        }
        val iterator = runningBarrageTaskMap.iterator()
        while (iterator.hasNext()){
            val next = iterator.next()
            val subListIterator = next.value.iterator()
            while (subListIterator.hasNext()){
                val subNext = subListIterator.next()
                if(subNext.barrage.hitted){
                    activeBarrageSet.remove(subNext.barrage)
                    subNext.task.cancel()
                    subListIterator.remove()
                }
            }
        }
    }
    class RunningBarrageTask(val task: BukkitTask, val barrage: Barrage){}
    private class BarrageRunnable(val barrage: Barrage):BukkitRunnable(){
        override fun run() {
            // 更新Tick
            barrage.updateLocationTick()
            // 更新位置
            barrage.updateLocation()
            // 检查是否击中
            barrage.checkStrategy()
            // 显示粒子效果
            barrage.updateAndShowParticle()
            if (!barrage.effective){
                cancel()
            }
            if (barrage.hitted){
                cancel()
            }
        }
    }
    fun containsPair(barrage:Barrage, uuid: UUID):Boolean{
        var runningBarrageTasks = runningBarrageTaskMap[uuid]?:return false
        for (i in runningBarrageTasks.indices){
            if (runningBarrageTasks[i].barrage == barrage){
                return true
            }
        }
        return false
    }
}