package cn.hiaxnlib.v498.utils

import cn.hiaxnlib.lib.item.HiaXnTag
import cn.hiaxnlib.lib.utils.INBTTagUtil
import cn.hiaxnlib.lib.version.VersionUtil
import org.bukkit.inventory.ItemStack

class NBTTagUtil:INBTTagUtil {
    override fun toNMSNBTTagObject(tag: HiaXnTag<*>): Any? {
        val nbtTagString = Class.forName("net.minecraft.server.${VersionUtil.getVersionString()}.NBTTagString")
        return nbtTagString.getConstructor(String::class.java).newInstance(tag.value.toString())
    }
    override fun setNBTTag(item: ItemStack, key: String, tag: HiaXnTag<*>): ItemStack {
        val objTag =  toNMSNBTTagObject(tag)
        val itemStackNMSCLS = Class.forName("net.minecraft.server.${VersionUtil.getVersionString()}.ItemStack")
        val nbtTagBaseCLS = Class.forName("net.minecraft.server.${VersionUtil.getVersionString()}.NBTBase")
        val craftItemStackCLS = Class.forName("org.bukkit.craftbukkit.${VersionUtil.getVersionString()}.inventory.CraftItemStack")
        val nbtTagCompound = Class.forName("net.minecraft.server.${VersionUtil.getVersionString()}.NBTTagCompound")
        val nms = craftItemStackCLS.getMethod("asNMSCopy",ItemStack::class.java).invoke(null,item)
        val nmsTag =  itemStackNMSCLS.getMethod("getTag").invoke(nms)?:let{
            return@let nbtTagCompound.newInstance()
        }
        nbtTagCompound.getMethod("set",String::class.java,nbtTagBaseCLS).invoke(nmsTag,key,objTag)
        itemStackNMSCLS.getMethod("setTag",nbtTagCompound).invoke(nms,nmsTag)
//        val asNMSCopy = CraftItemStack.asNMSCopy(item)
//        val tag1 = asNMSCopy.tag
//        tag1.set("xxx",NBTTagString("XXX"))
//        asNMSCopy.tag = tag1
        return  craftItemStackCLS.getMethod("asBukkitCopy",itemStackNMSCLS).invoke(null,nms) as ItemStack
    }

    override fun getNBTTag(item: ItemStack, key: String): String? {
        val nmsItemStackCLS = Class.forName("net.minecraft.server.${VersionUtil.getVersionString()}.ItemStack")
        val craftItemStackCLS = Class.forName("org.bukkit.craftbukkit.${VersionUtil.getVersionString()}.inventory.CraftItemStack")
        val nbtTagCompoundCLS = Class.forName("net.minecraft.server.${VersionUtil.getVersionString()}.NBTTagCompound")
        val nms = craftItemStackCLS.getMethod("asNMSCopy",ItemStack::class.java).invoke(null,item)
        val getNMSTag = nmsItemStackCLS.getMethod("getTag").invoke(nms)?:return null
        return (nbtTagCompoundCLS.getMethod("getString",String::class.java).invoke(getNMSTag,key)?:return null).toString()
    }

    override fun removeTag(item: ItemStack, key: String): ItemStack {
        val nmsItemStackCLS = Class.forName("net.minecraft.server.${VersionUtil.getVersionString()}.ItemStack")
        val craftItemStackCLS = Class.forName("org.bukkit.craftbukkit.${VersionUtil.getVersionString()}.inventory.CraftItemStack")
        val nbtTagCompound = Class.forName("net.minecraft.server.${VersionUtil.getVersionString()}.NBTTagCompound")
        val nms = craftItemStackCLS.getMethod("asNMSCopy",ItemStack::class.java).invoke(null,item)
        val getNMSTag = nmsItemStackCLS.getMethod("getTag").invoke(nms)?:let{
            return@let nbtTagCompound.newInstance()
        }
        nbtTagCompound.getMethod("remove",String::class.java).invoke(getNMSTag,key)
        nmsItemStackCLS.getMethod("setTag",nbtTagCompound).invoke(nms,getNMSTag)
        return craftItemStackCLS.getMethod("asBukkitCopy",nmsItemStackCLS).invoke(null,nms) as ItemStack
    }

    override fun setSkullTexture(item: ItemStack, texture: String): ItemStack? {
        TODO("Not yet implemented")
    }
}