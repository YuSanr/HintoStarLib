package cn.HiaXnLib.item

import cn.HiaXnLib.NMS.item.ItemNMS
import cn.HiaXnLib.NMS.item.itemUtil
import cn.HiaXnLib.item.utils.ItemStackUtils
import org.bukkit.inventory.ItemStack
import java.lang.Exception

open class HiaXnItemStack constructor(var item:ItemStack,var itemAction:(Array<Any?>)->Unit){
    var itemUtil = ItemStackUtils()

    lateinit var uuid:String
    companion object Register{
        val registerItems = HashSet<HiaXnItemStack>()
        fun register(vararg items: HiaXnItemStack){
            registerItems.addAll(items)
        }
    }
    constructor(uuid:String,item: ItemStack,displayName:String,itemAction:(Array<Any?>)->Unit):this(uuid,item,itemAction){
        item.itemMeta = item.itemMeta?.apply {
            setDisplayName(displayName)
        }
    }
    constructor(uuid:String,item:ItemStack,itemAction: (Array<Any?>) -> Unit):this(item,itemAction){
        this.item = getItemNMS().setItemNBT(item,"UUID",uuid)
        this.uuid = uuid
    }
    fun invokeAction(vararg obj:Any){
        itemAction(arrayOf(obj))
    }
    fun isRegistered(item: HiaXnItemStack):Boolean{
        return registerItems.contains(item);
    }

    /**
     * must override
     */
    fun clone(): HiaXnItemStack {
        try{
            return HiaXnItemStack(uuid,item,itemAction)
        }catch (ignored:Exception){
            return HiaXnItemStack(item,itemAction)
        }
    }
    fun getItemUtil(): itemUtil {
        return itemUtil()
    }
    fun getItemNMS(): ItemNMS {
        return ItemNMS()
    }
    fun register(){
        registerItems.add(this);
    }

    /**
     * 若UUID相同 则返回true
     */
    override fun equals(other: Any?): Boolean {
        val otherItem = other as HiaXnItemStack
        try{
            return otherItem.uuid == uuid
        }catch (ignored:Exception){
            // 该物品没有设置UUID
            return itemUtil.itemEquals(otherItem.item,item)
        }
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