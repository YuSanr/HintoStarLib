package cn.hiaxnlib.lib.manner

import cn.hiaxnlib.lib.particle.ParticleTask
import cn.hiaxnlib.particle.HiaXnParticleStyle
import org.bukkit.Location
import org.bukkit.plugin.Plugin

class ParticleManner(private var threadNumber:Int,private val plugin:Plugin) {
    private val tasks = ArrayList<ParticleTask>()
    init {
        if (threadNumber <= 0){
            threadNumber = 1
        }
        for (i in 0 until threadNumber){
            tasks.add(ParticleTask().also { it.runTaskTimerAsynchronously(plugin,0,1) })
        }
    }
    fun addTask(style:HiaXnParticleStyle,origin:Location){
        tasks.sort()
        tasks[0].addTask(style,origin)
    }
}