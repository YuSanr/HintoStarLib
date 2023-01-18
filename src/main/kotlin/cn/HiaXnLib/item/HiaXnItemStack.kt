package cn.HiaXnLib.item

import cn.HiaXnLib.NMS.item.itemUtil
import cn.HiaXnLib.item.utils.ItemStackUtils
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import java.util.concurrent.ConcurrentHashMap

/**
 * 任何自定义物品应该继承该类
 * 而不是调用该类的方法或者函数
 * 物品只有注册时才会调用物品函数
 */
open class HiaXnItemStack constructor(val uuid:String,var item:ItemStack,var itemAction:(Array<Any?>)->Unit){
    var canBreak = false
    companion object ItemUtil{
        val stackUtils = ItemStackUtils()
        // 注册列表集合
        // 使用HashMap是因为方便Item转换为HiaXnItemStack
        val registerItems = ConcurrentHashMap<String,HiaXnItemStack>(128)
        // 注册物品
        fun register(vararg items: HiaXnItemStack){
            items.forEach {
                registerItems[it.uuid] = it
            }
        }
    }
    constructor(uuid:String,item: ItemStack,displayName:String,itemAction:(Array<Any?>)->Unit):this(uuid,item,itemAction){
        item.itemMeta = item.itemMeta?.apply {
            setDisplayName(displayName)
        }
    }
    /**
     * 设置物品的UUID
      */
    init {
        this.item = stackUtils.getItemNMS().setItemNBT(item,"UUID",uuid)
    }

    /**
     * 设置该物品是否能被破坏
     * 支持版本 1.8-1.12.2
     */
    fun setBreak(b:Boolean){
        if (b== true){
            item = stackUtils.getItemNMS().setItemNBT(item, "Unbreakable", 1)
        }else {
            item = stackUtils.getItemNMS().setItemNBT(item, "Unbreakable", 0)
        }
        canBreak = b
    }

    /**
     * 给物品添加一个附魔
     */
    fun addEnchants(enchantment:Enchantment,level:Int){
        item.addEnchantment(enchantment,level)
    }

    /**
     * 执行设定的函数
     * obj -- 函数设定的参数
     */
    fun invokeAction(args:Array<Any?>){
        itemAction(args)
    }

    /**
     * must override
     */
    fun clone(): HiaXnItemStack {
        return HiaXnItemStack(uuid,item,itemAction)
    }
    fun getItemUtil(): itemUtil {
        return itemUtil()
    }
    /**
     * 判定该物品是否注册
     */
    fun isRegistered(item: HiaXnItemStack):Boolean {
        return registerItems.containsValue(item);
    }
    /**
     * 判断物品是否已经注册
     */
    fun isRegister():Boolean{
        for (registerItem in registerItems) {
            if (registerItem.key == uuid){
                return true
            }
        }
        return false
    }

    /**
     * 注册函数
     * 如果该物品没有被注册 则添加到注册列表
     */
    fun register(){
        registerItems[uuid] = this
    }
    /**
     * 从注册的物品中获取该UUID的物品
     */
    fun getItemFromUUID(uuid: String):HiaXnItemStack?{
        for (registerItem in registerItems) {
            if (registerItem.key == uuid){
                return registerItem.value
            }
        }
        return null;
    }
    /**
     * 若UUID相同 则返回true
     */
    override fun equals(other: Any?): Boolean {
        val otherItem = other as HiaXnItemStack
        return otherItem.uuid == uuid
    }
    override fun hashCode(): Int {
        var result = item.hashCode()
        result = 31 * result + itemAction.hashCode()
        try{
            result = 31 * result + uuid.hashCode()
        }catch (ignored:Exception){ }
        return result
    }
}