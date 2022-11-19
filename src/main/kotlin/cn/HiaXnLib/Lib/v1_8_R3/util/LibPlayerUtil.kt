package cn.HiaXnLib.Lib.v1_8_R3.util

import cn.HiaXnLib.API.util.PlayerUtil
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage
import org.bukkit.entity.Player

class LibPlayerUtil:PlayerUtil {
    override fun sendTitle(player: Player, title: String, subTitle: String, fadeIn: Int, stay: Int, fadeOut: Int) {
        val craftPlayer = player as CraftPlayer
        var packetSubtitle: PacketPlayOutTitle
        //发送主标题包
        packetSubtitle = PacketPlayOutTitle(EnumTitleAction.TITLE, CraftChatMessage.fromString(title)[0],fadeIn,stay,fadeOut)
        craftPlayer.getHandle().playerConnection.sendPacket(packetSubtitle)

        // 副标题包
        packetSubtitle = PacketPlayOutTitle(EnumTitleAction.SUBTITLE, CraftChatMessage.fromString(subTitle)[0],fadeIn,stay,fadeOut)
        craftPlayer.getHandle().playerConnection.sendPacket(packetSubtitle)
    }

    override fun sendActionBar(player: Player, bar: String) {
        // 该API来源于1.16.5
        // 应当使用发包的方法
//        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,TextComponent(bar))
    }
}