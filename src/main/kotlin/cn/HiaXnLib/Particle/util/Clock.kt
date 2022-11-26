package cn.HiaXnLib.Particle.util

import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import java.util.UUID

/**
 * 用于计时Kunkun
 */
class Clock(var time:Int,var bindPlayer:Player,var args:Array<Any?>,var action:(Array<Any?>)->Unit,):BukkitRunnable() {
    companion object{
        val runnedClock = HashMap<UUID, Clock>()
    }
    // 1tick /s 的速度减少 因此是double
    override fun run() {
        time -=1
        if (time <=0){
            cancel()
        }
    }

    override fun cancel() {
        action(args)
        runnedClock.remove(bindPlayer.uniqueId)
        super.cancel()
    }

    override fun runTaskTimer(plugin: Plugin?, delay: Long, period: Long): BukkitTask {
        runnedClock[bindPlayer.uniqueId] = this
        return super.runTaskTimer(plugin, delay, period)
    }
}