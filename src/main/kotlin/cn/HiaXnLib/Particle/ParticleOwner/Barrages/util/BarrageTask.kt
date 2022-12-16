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
        val activeBarrageIterator = activeBarrageSet.iterator()
        // 针对40%的检测性能损耗的优化
        while (activeBarrageIterator.hasNext()){
            val barrage = activeBarrageIterator.next()
            if (!runningBarrageTaskMap.containsKey(barrage.barrageOwner.getUUID())){
                runningBarrageTaskMap[barrage.barrageOwner.getUUID()] = ArrayList<RunningBarrageTask>()
            }
            val subList = runningBarrageTaskMap[barrage.barrageOwner.getOwner().getUUID()]?:continue
            subList.add(
                RunningBarrageTask(BarrageRunnable(barrage).runTaskTimerAsynchronously(main.getInstance(),5,1),barrage)
            )
            // 用完就删
            activeBarrageIterator.remove()
        }
        val iterator = runningBarrageTaskMap.iterator()
        while (iterator.hasNext()){
            val next = iterator.next()
            val subListIterator = next.value.iterator()
            while (subListIterator.hasNext()){
                val subNext = subListIterator.next()
                if(subNext.barrage.hitted){
                    Barrage.barrageHashMap.remove(subNext.barrage.barrageUUID)
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
}