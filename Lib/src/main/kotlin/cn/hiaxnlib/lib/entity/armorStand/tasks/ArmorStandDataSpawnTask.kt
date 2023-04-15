package cn.hiaxnlib.lib.entity.armorStand.tasks

import cn.hiaxnlib.lib.entity.armorStand.ArmorStandNode
import cn.hiaxnlib.lib.entity.armorStand.utils.ChunkUtil
import cn.hiaxnlib.lib.version.VersionUtil
import org.bukkit.scheduler.BukkitRunnable

class ArmorStandDataSpawnTask:BukkitRunnable() {
    val manager = VersionUtil.getArmorStandManager()
    override fun run() {

        if (manager.spawnEntityQueue.isEmpty()) {
            cancel()
        }

        val buffer = ArrayList<ArmorStandNode>(manager.spawnEntityQueue)
        for (queueNode in buffer) {
            if(ChunkUtil.isLoad(queueNode.location)){
                manager.respawnEntity(queueNode,queueNode.location)
                manager.spawnEntityQueue.remove(queueNode)
            }
        }
    }
}