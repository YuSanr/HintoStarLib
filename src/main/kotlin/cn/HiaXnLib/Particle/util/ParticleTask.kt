package cn.HiaXnLib.Particle.util

import cn.HiaXnLib.Particle.HiaXnParticlePair
import cn.HiaXnLib.Particle.HiaXnParticleStyle
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import java.util.UUID

/**
 * 在主类被构建
 */
class ParticleTask(private val plugin:Plugin):BukkitRunnable() {
    companion object{
        val activeParticleStyleSet = HashSet<HiaXnParticlePair>()
        private val runningTaskMap = HashMap<UUID,ArrayList<ShowParticlePair>>()
    }

    override fun run() {
        for (pair in activeParticleStyleSet) {
            if (!runningTaskMap.containsKey(pair.owner.getUUID())){
                runningTaskMap[pair.owner.getUUID()] = ArrayList<ShowParticlePair>()
            }
            val subList = runningTaskMap[pair.owner.getUUID()] ?:continue
            if (!containsPair(pair,pair.owner.getUUID())){
                subList.add(
                    ShowParticlePair(Bukkit.getScheduler().runTaskTimerAsynchronously(
                        plugin,{
                            pair.display()
                        },5,pair.style.getIntervalTime().toLong()),pair))
            }
        }
        val iterator = runningTaskMap.iterator()
        while (iterator.hasNext()){
            val next = iterator.next()
            val subListIterator = next.value.iterator()
            while (subListIterator.hasNext()){
                val subNext = subListIterator.next()
                if(subNext.pair.over){
                    activeParticleStyleSet.remove(subNext.pair)
                    subNext.task.cancel()
                    subListIterator.remove()
                }
            }
        }
    }

    private class ShowParticlePair(val task:BukkitTask,val pair: HiaXnParticlePair){
    }
    fun containsPair(pair:HiaXnParticlePair,uuid: UUID):Boolean{
        var showParticlePairs = runningTaskMap[uuid]?:return false
        for (i in showParticlePairs.indices){
            if (showParticlePairs[i].pair == pair){
                return true
            }
        }
        return false
    }
}