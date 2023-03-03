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
       val tag =  toNMSNBTTagObject(tag)
        val itemStack = Class.forName("net.minecraft.server.${VersionUtil.getVersionString()}.ItemStack")
        val nbtTagStringCLS = Class.forName("net.minecraft.server.${VersionUtil.getVersionString()}.NBTTagString")
        val craftItemStackCLS = Class.forName("net.minecraft.server.${VersionUtil.getVersionString()}.CraftItemStack")
        val nbtTagCompound = Class.forName("net.minecraft.server.${VersionUtil.getVersionString()}.NBTTagCompound")
        val nms = craftItemStackCLS.getMethod("asNMSCopy",ItemStack::class.java).invoke(null,item)
        val getNMSTag = itemStack.getMethod("getTag").invoke(nms)
        nbtTagCompound.getMethod("setString",String::class.java,nbtTagStringCLS).invoke(getNMSTag,key,tag)
        itemStack.getMethod("setTag",nbtTagCompound).invoke(nms,getNMSTag)
        return craftItemStackCLS.getMethod("asBukkitCopy",itemStack).invoke(null,nms) as ItemStack
    }

    override fun getNBTTag(item: ItemStack, key: String): String? {
        val itemStack = Class.forName("net.minecraft.server.${VersionUtil.getVersionString()}.ItemStack")
        val craftItemStackCLS = Class.forName("net.minecraft.server.${VersionUtil.getVersionString()}.CraftItemStack")
        val nbtTagCompound = Class.forName("net.minecraft.server.${VersionUtil.getVersionString()}.NBTTagCompound")
        val nms = craftItemStackCLS.getMethod("asNMSCopy",ItemStack::class.java).invoke(null,item)
        val getNMSTag = itemStack.getMethod("get").invoke(nms)
        return (nbtTagCompound.getMethod("get",String::class.java).invoke(getNMSTag,key)?:return null).toString()
    }

    override fun removeTag(item: ItemStack, key: String): ItemStack {
        val itemStack = Class.forName("net.minecraft.server.${VersionUtil.getVersionString()}.ItemStack")
        val craftItemStackCLS = Class.forName("net.minecraft.server.${VersionUtil.getVersionString()}.CraftItemStack")
        val nbtTagCompound = Class.forName("net.minecraft.server.${VersionUtil.getVersionString()}.NBTTagCompound")
        val nms = craftItemStackCLS.getMethod("asNMSCopy",ItemStack::class.java).invoke(null,item)
        val getNMSTag = itemStack.getMethod("getTag").invoke(nms)
        nbtTagCompound.getMethod("remove",String::class.java).invoke(getNMSTag,key)
        itemStack.getMethod("setTag",nbtTagCompound).invoke(nms,getNMSTag)
        return craftItemStackCLS.getMethod("asBukkitCopy",itemStack).invoke(null,nms) as ItemStack
    }

    override fun setSkullTexture(item: ItemStack, texture: String): ItemStack? {
//        val nms = itemStackToNMS(item) as net.minecraft.server.v1_12_R1.ItemStack
//        val tag = nms.tag
//        val skullOwner = NBTTagCompound()
//        skullOwner.setString("Id",UUID.randomUUID().toString())
//        val properties = NBTTagCompound()
//        val texture = NBTTagList()
//        val value = NBTTagCompound()
//        value.setString("Value",textureValue)
//        texture.add(value)
//        properties.set("textures",texture)
//        skullOwner.set("Properties",properties)
//        tag!!.set("SkullOwner",skullOwner)
//        nms.tag = tag
//        return CraftItemStack.asBukkitCopy(nms)
        return null
    }
}