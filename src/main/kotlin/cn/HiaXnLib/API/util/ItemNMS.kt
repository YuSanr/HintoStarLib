package cn.HiaXnLib.API.util

import cn.HiaXnLib.API.POJO.HiaXnNBTBaseBuilder
import org.bukkit.inventory.ItemStack

interface ItemNMS {
    /**
     * 将NMS[Object] 转换为 ItemStack
     */
    fun NMSStackToBukkit(any:Any):ItemStack

    /**
     * 将物品转换为NMS包下的ItemStack
     * Object形式 自行转型
     */
    fun itemStackToNMS(item:ItemStack):Any
    fun getItemTag(itemStack: ItemStack):Any
    /**
     * 设置物品的NBT
     * item 设置的物品
     * key NBT键
     * value NBT值
     * @return 返回设置后的ItemStack
     */
    fun setItemNBT(item:ItemStack,key:String,value:String):ItemStack
    fun setItemNBT(item:ItemStack,key:String,value:Int):ItemStack
    fun setItemNBT(item:ItemStack,key:String,value:Double):ItemStack
    fun setItemNBT(item:ItemStack,key:String,value:Long):ItemStack

    /**
     * @param value 采用NBTTag构建的方式 支持多种数据类型[基本与String]
     */
    fun setItemNBT(item:ItemStack,key:String,value:HiaXnNBTBaseBuilder<*>):ItemStack
    fun getItemNBT(item:ItemStack,key:String):Any?

    /**
     * 判断物品是否有某个NBT键值
     */
    fun hasNBT(item:ItemStack,key:String):Boolean

    /**
     * 获取物品的所有NBT键
     */
    fun getNBTKeys(item:ItemStack):Set<String>

    /**
     * @param textureValue 输入皮肤的base-64值 在https://minecraft-heads.com/处为Value的内容
     */
    fun setSkullItem(item:ItemStack,textureValue:String):ItemStack

}