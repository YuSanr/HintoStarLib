package cn.HiaXnLib.entity

import org.bukkit.Effect
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

class HiaXnEntity (val uuid:String,var entity: Entity){
    companion object StaticEntity{
        fun playerEffect(location: Location,type:Effect,amount:Int,data:Int,radius:Int,speed:Float){
            location.world?.spigot()?.playEffect(location,type,data,0, 0F,0F,0F,speed,amount,radius)
        }
    }
    fun playerEffect(type:Effect,amount:Int,data:Int,radius:Int,speed:Float){
        entity.run {
            world.spigot().run {
                playEffect(location,type,data,0, 0F,0F,0F,speed,amount,radius)
            }
        }
    }
}