package cn.hiaxnlib.lib.owner

import org.bukkit.Location
import java.util.UUID

class OwnerPoint(var location:Location, val pointUUID:UUID): Owner {
    override fun getOwner(): Owner {
        return this
    }

    override fun getUUID(): UUID {
        return pointUUID
    }

    override fun getOwnerLocation(): Location {
        return location
    }
}