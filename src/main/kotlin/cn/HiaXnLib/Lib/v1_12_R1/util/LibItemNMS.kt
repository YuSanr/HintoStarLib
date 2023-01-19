package cn.HiaXnLib.Lib.v1_12_R1.util

import cn.HiaXnLib.API.POJO.HiaXnNBTBaseBuilder
import cn.HiaXnLib.API.util.ItemNMS
import net.minecraft.server.v1_12_R1.*
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.collections.HashSet

class LibItemNMS: ItemNMS {
    override fun NMSStackToBukkit(any: Any): ItemStack {
        if (any !is net.minecraft.server.v1_12_R1.ItemStack){
            return ItemStack(Material.AIR)
        }
        any as net.minecraft.server.v1_12_R1.ItemStack
        return CraftItemStack.asBukkitCopy(any)
    }

    override fun itemStackToNMS(item: ItemStack): Any {
        return CraftItemStack.asNMSCopy(item)
    }

    override fun getItemTag(itemStack: ItemStack): Any {
        return (itemStackToNMS(itemStack) as net.minecraft.server.v1_12_R1.ItemStack).tag as Any
    }

    override fun setItemNBT(item: ItemStack, key: String, value: String): ItemStack {
        val nms = itemStackToNMS(item) as net.minecraft.server.v1_12_R1.ItemStack
        val tag = nms.tag!!
        tag.setString(key,value)
        nms.tag = tag
        return CraftItemStack.asBukkitCopy(nms)
    }

    override fun setItemNBT(item: ItemStack, key: String, value: Int): ItemStack {
        val nms = itemStackToNMS(item) as net.minecraft.server.v1_12_R1.ItemStack
        val tag = nms.tag!!
        tag.setInt(key,value)
        nms.tag = tag
        return CraftItemStack.asBukkitCopy(nms)
    }

    override fun setItemNBT(item: ItemStack, key: String, value: Double): ItemStack {
        val nms = itemStackToNMS(item) as net.minecraft.server.v1_12_R1.ItemStack
        val tag = nms.tag!!
        tag.setDouble(key,value)
        nms.tag = tag
        return CraftItemStack.asBukkitCopy(nms)
    }

    override fun setItemNBT(item: ItemStack, key: String, value: Long): ItemStack {
        val nms = itemStackToNMS(item) as net.minecraft.server.v1_12_R1.ItemStack
        val tag = nms.tag!!
        tag.setLong(key,value)
        nms.tag = tag
        return CraftItemStack.asBukkitCopy(nms)
    }

    override fun setItemNBT(item: ItemStack, key: String, value: HiaXnNBTBaseBuilder<*>): ItemStack {
        val nms = itemStackToNMS(item) as net.minecraft.server.v1_12_R1.ItemStack
        val tag = nms.tag!!
        tag.set(key,value.toNBTTag() as NBTBase)
        nms.tag = tag
        return CraftItemStack.asBukkitCopy(nms)
    }

    override fun getItemNBT(item: ItemStack, key: String): Any? {
        val nms = itemStackToNMS(item) as net.minecraft.server.v1_12_R1.ItemStack
        nms.tag?:return null
        val getTag = nms.tag!!.get(key)
        return getTag
    }

    override fun hasNBT(item: ItemStack, key: String): Boolean {
        return getItemNBT(item,key) != null
    }

    override fun getNBTKeys(item: ItemStack): Set<String> {
        val nms = itemStackToNMS(item) as net.minecraft.server.v1_12_R1.ItemStack
        nms.tag?:return HashSet<String>()
        return nms.tag!!.c()
    }

    override fun setSkullItem(item: ItemStack, textureValue: String): ItemStack {
        val nms = itemStackToNMS(item) as net.minecraft.server.v1_12_R1.ItemStack
        val tag = nms.tag
        val skullOwner = NBTTagCompound()
        skullOwner.setString("Id",UUID.randomUUID().toString())
        val properties = NBTTagCompound()
        val texture = NBTTagList()
        val value = NBTTagCompound()
        value.setString("Value",textureValue)
        texture.add(value)
        properties.set("textures",texture)
        skullOwner.set("Properties",properties)
        tag!!.set("SkullOwner",skullOwner)
        nms.tag = tag
        return CraftItemStack.asBukkitCopy(nms)
    }

}