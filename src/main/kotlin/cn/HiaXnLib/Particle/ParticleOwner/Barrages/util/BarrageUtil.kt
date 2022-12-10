package cn.HiaXnLib.Particle.ParticleOwner.Barrages.util

import cn.HiaXnLib.Particle.ParticleOwner.Barrages.Barrage
import org.bukkit.Location

object BarrageUtil {
    fun spawnBarrage(barrage:Barrage){
        if(barrage.hitted) return
        Barrage.barrageHashMap[barrage.barrageUUID] = barrage
        BarrageTask.activeBarrageSet.add(barrage)
    }
}