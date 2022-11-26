package cn.HiaXnLib.Particle.ParticleOwner

import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.UUID

class ParticlePlayer(var player:Player) :Owner{
    override fun getOwner(): Owner {
        return this
    }

    override fun getUUID(): UUID {
        return player.uniqueId
    }

    /**
     * @throws NullPointerException 玩家不在线
     */
    override fun getParticleLocation(): Location {
        return player.location
    }
}