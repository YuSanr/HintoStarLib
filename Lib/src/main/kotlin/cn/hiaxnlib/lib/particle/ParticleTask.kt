package cn.hiaxnlib.lib.particle

import cn.hiaxnlib.particle.HiaXnParticleStyle
import org.bukkit.Location
import org.bukkit.scheduler.BukkitRunnable

/**
 * 在主类被构建
 */
class ParticleTask:Comparable<ParticleTask>,BukkitRunnable() {
    private val activeTask = ArrayList<RunningTask>()
    private class RunningTask(val style: HiaXnParticleStyle, val origin:Location) {
        var tick = 0
        var over = false
        fun runTick() {
            tick++
            if (tick % style.getIntervalTime() == 0){
                if (style.checkOver()){
                    over = true
                    return
                }
                style.display(origin)
                style.updateParticleTick()
                tick = 0
            }
        }
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as RunningTask

            if (style != other.style) return false
            if (origin != other.origin) return false
            if (tick != other.tick) return false
            if (over != other.over) return false

            return true
        }

        override fun hashCode(): Int {
            var result = style.hashCode()
            result = 31 * result + origin.hashCode()
            result = 31 * result + tick
            result = 31 * result + over.hashCode()
            return result
        }
    }
    fun addTask(style: HiaXnParticleStyle,origin:Location){
        activeTask.add(RunningTask(style,origin))
    }

    override fun compareTo(other: ParticleTask): Int {
        return activeTask.size - other.activeTask.size
    }

    override fun run() {
        val iterator = activeTask.iterator()
        while (iterator.hasNext()){
            val task = iterator.next()
            if (task.over){
                iterator.remove()
            }
            task.runTick()
        }
    }
}