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
     * @return 将原版物品堆转换为HiaXnItemStack 若该物品没有UUID 则视为非HiaXnItemStack 则返回Null
     * @exception NullPointerException 该物品为非HiaXnItemStack或者物品未成功注册
     */
    fun itemToHiaXnStack(item:ItemStack): HiaXnItemStack? {
        // 傻逼引号
        val uuidObj = getNbtBase(item,"UUID")?:return null
        val uuidStr =uuidObj.toString().replace("\"","")
        return if (HiaXnItemStack.registerItems.containsKey(uuidStr)){
            HiaXnItemStack.registerItems[uuidStr]
        }else{
            null
        }
    }
}