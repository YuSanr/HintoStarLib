package cn.hiaxnlib.lib.owner

import org.bukkit.Location
import org.bukkit.entity.Entity
import java.lang.NullPointerException
import java.util.*

class OwnerEntity(val entity:Entity): Owner {
    override fun getOwner(): Owner {
        return this
    }

    override fun getUUID(): UUID {
        return entity.uniqueId
    }

    /**
     * @throws NullPointerException Entity not Valid
     */
    override fun getOwnerLocation(): Location {

        return entity.location
    }
}