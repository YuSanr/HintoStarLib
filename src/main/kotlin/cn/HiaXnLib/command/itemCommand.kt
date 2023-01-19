package cn.HiaXnLib.command

import cn.HiaXnLib.item.utils.ItemStackUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class itemCommand:CommandExecutor {
    val utils = ItemStackUtils()
    override fun onCommand(sender: CommandSender, p1: Command?, p2: String?, p3: Array<out String>?): Boolean {
        if (sender !is Player){
            sender.sendMessage("这个指令只能由玩家执行")
        }
        sender as Player
        // 适配低版本
        val handItem = sender.itemInHand?:let {
            sender.sendMessage("你必须手持一个物品才能查看物品的NBT结构!")
            return true
        }
        sender.sendMessage("该物品的NBT结构为:")
        sender.sendMessage("${utils.getItemNMS().getItemTag(handItem)}")
        return true
    }

}