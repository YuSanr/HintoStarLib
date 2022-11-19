package cn.HiaXnLib.API.util

import org.bukkit.entity.Player

interface PlayerUtil {
    fun sendTitle(player:Player,title:String,subTitle:String,fadeIn:Int,stay:Int,fadeOut:Int)
    fun sendActionBar(player: Player,bar:String)
}