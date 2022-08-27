package cn.HiaXnLib.entity.utils

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType

class EntityUtil {
    fun createEntity(world: World,location: Location,type:EntityType):Entity{
        return world.spawnEntity(location,type)
    }
    fun createEntity(world: World,location: Location,entity: Entity):Entity{
        if (entity.isValid){

        }
        return entity
    }
}