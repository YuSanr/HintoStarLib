package cn.hiaxnlib.lib.owner

import org.bukkit.Location
import java.util.UUID

interface Owner {
    fun getOwner(): Owner
    fun getUUID():UUID
    fun getOwnerLocation():Location
}