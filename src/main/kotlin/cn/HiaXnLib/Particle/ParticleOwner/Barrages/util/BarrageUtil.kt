package cn.HiaXnLib.Particle.ParticleOwner.Barrages.util

import cn.HiaXnLib.Particle.ParticleOwner.Barrages.Barrage
import cn.HiaXnLib.main
import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.entity.Entity
import java.util.concurrent.FutureTask

object BarrageUtil {
    fun spawnBarrage(barrage:Barrage){
        if(barrage.hitted) return
        Barrage.barrageHashMap[barrage.barrageUUID] = barrage
        BarrageTask.activeBarrageSet.add(barrage)
    }

}

