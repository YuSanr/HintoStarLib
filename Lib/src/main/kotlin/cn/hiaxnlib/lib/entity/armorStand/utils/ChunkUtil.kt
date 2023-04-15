package cn.hiaxnlib.lib.entity.armorStand.utils

import org.bukkit.Chunk
import org.bukkit.Location

object ChunkUtil {
    fun isLoad(location: Location):Boolean{
        if (location.world == null) return false
        val chunkX = location.x.toInt() shr 4
        val chunkZ = location.z.toInt() shr 4
        return location.world.isChunkLoaded(chunkX,chunkZ)
    }
    fun loadChunk(location: Location):Chunk{
        return location.world.getChunkAt(location)
    }
    fun loadChunk(location: Location,boolean: Boolean):Boolean{
        return location.world.loadChunk(location.x.toInt() shr 4,location.z.toInt() shr 4,boolean)
    }
}