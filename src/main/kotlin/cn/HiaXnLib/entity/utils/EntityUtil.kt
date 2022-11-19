package cn.HiaXnLib.entity.utils

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType

class EntityUtil {
    fun createEntity(location: Location,type:EntityType):Entity{
        return location.world.spawnEntity(location,type)
    }
    fun removeEntity(entity: Entity){
        if(entity.isValid){
            entity.remove()
        }
    }
    fun setEntityCustomName(name:String,entity: Entity){
        entity.customName = name
    }
}