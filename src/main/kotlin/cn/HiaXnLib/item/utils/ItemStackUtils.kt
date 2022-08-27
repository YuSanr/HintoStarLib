package cn.HiaXnLib.item.utils

import cn.HiaXnLib.NMS.item.itemUtil
import cn.HiaXnLib.item.HiaXnItemStack
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class ItemStackUtils: itemUtil(){
    override fun removeItem(player: Player?, item: ItemStack?, Amount: Int) {
        super.removeItem(player, item, Amount)
    }

    /**
     * 可以将ItemStack转换为HiaXnItemStack
     * @param item 原版物品堆
     * @return HiaXnItemStack 若原版物品堆的NBT不包含UUID 则返回一个空UUID的HiaXnItemStack 并在Equals中采用ItemUtil的ItemEqual
     */
    fun itemToHiaXnStack(item:ItemStack): HiaXnItemStack {
        val uuid = getNbtBase(item,"UUID") as String?
        return if (uuid !is String){
            HiaXnItemStack(item){}
        }else {
            HiaXnItemStack(uuid,item) {}
        }
    }
}