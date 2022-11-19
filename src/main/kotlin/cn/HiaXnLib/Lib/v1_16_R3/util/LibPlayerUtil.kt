package cn.HiaXnLib.Lib.v1_16_R3.util

import cn.HiaXnLib.API.util.PlayerUtil
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import net.minecraft.server.v1_16_R3.PacketPlayOutTitle
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_16_R3.util.CraftChatMessage
import org.bukkit.entity.Player

class LibPlayerUtil:PlayerUtil {
    override fun sendTitle(player: Player, title: String, subTitle: String, fadeIn: Int, stay: Int, fadeOut: Int) {
        val craftPlayer = player as CraftPlayer
        var packetSubtitle: PacketPlayOutTitle
        //发送主标题包
        packetSubtitle = PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, CraftChatMessage.fromString(title)[0],fadeIn,stay,fadeOut)
        craftPlayer.getHandle().playerConnection.sendPacket(packetSubtitle)

        // 副标题包
        packetSubtitle = PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, CraftChatMessage.fromString(subTitle)[0],fadeIn,stay,fadeOut)
        craftPlayer.getHandle().playerConnection.sendPacket(packetSubtitle)
    }

    override fun sendActionBar(player: Player, bar: String) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,TextComponent(bar))
    }
}