package cn.hiaxnlib.lib.manner

import cn.hiaxnlib.lib.owner.Barrages.Barrage
import cn.hiaxnlib.lib.owner.Barrages.util.BarrageTask
import org.bukkit.plugin.Plugin

class BarrageManner(private var threadNumber:Int,private val plugin:Plugin) {
    val aliveBarrages = ArrayList<Barrage>()
    private val tasks = ArrayList<BarrageTask>()
    init {
        if (threadNumber <= 0){
            threadNumber = 1
        }
        for (i in 0 until threadNumber){
            tasks.add(BarrageTask().also { it.runTaskTimerAsynchronously(plugin,0,1) })
        }
    }
    fun addTask(barrage: Barrage){
        aliveBarrages.add(barrage)
        tasks.sort()
        tasks[0].addTask(barrage)
    }
}