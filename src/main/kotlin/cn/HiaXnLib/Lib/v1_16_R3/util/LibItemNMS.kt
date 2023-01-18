package cn.HiaXnLib.Lib.v1_16_R3

import cn.HiaXnLib.API.POJO.HiaXnNBTBaseBuilder
import cn.HiaXnLib.API.util.ItemNMS
import net.minecraft.server.v1_16_R3.*
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.collections.HashSet

class LibItemNMS: ItemNMS {
    override fun NMSStackToBukkit(any: Any): ItemStack {
        if (any !is net.minecraft.server.v1_16_R3.ItemStack){
            return ItemStack(Material.AIR)
        }
        return CraftItemStack.asBukkitCopy(any)
    }

    override fun itemStackToNMS(item: ItemStack): Any {
        return CraftItemStack.asNMSCopy(item)
    }

    override fun setItemNBT(item: ItemStack, key: String, value: String): ItemStack {
        val nms = itemStackToNMS(item) as net.minecraft.server.v1_16_R3.ItemStack
        nms.tag!!.set(key,NBTTagCompound().getCompound(value))
        return CraftItemStack.asBukkitCopy(nms)
    }

    override fun setItemNBT(item: ItemStack, key: String, value: Int): ItemStack {
        val nms = itemStackToNMS(item) as net.minecraft.server.v1_16_R3.ItemStack
        nms.tag!!.set(key, NBTTagInt.a(value))
        return CraftItemStack.asBukkitCopy(nms)
    }

    override fun setItemNBT(item: ItemStack, key: String, value: Double): ItemStack {
        val nms = itemStackToNMS(item) as net.minecraft.server.v1_16_R3.ItemStack
        nms.tag!!.set(key, NBTTagDouble.a(value))
        return CraftItemStack.asBukkitCopy(nms)
    }

    override fun setItemNBT(item: ItemStack, key: String, value: Long): ItemStack {
        val nms = itemStackToNMS(item) as net.minecraft.server.v1_16_R3.ItemStack
        nms.tag!!.set(key, NBTTagLong.a(value))
        return CraftItemStack.asBukkitCopy(nms)
    }

    override fun setItemNBT(item: ItemStack, key: String, value: HiaXnNBTBaseBuilder<*>): ItemStack {
        val nms = itemStackToNMS(item) as net.minecraft.server.v1_16_R3.ItemStack
        nms.tag!!.set(key,value.toNBTTag() as NBTBase)
        return CraftItemStack.asBukkitCopy(nms)
    }

    override fun getItemNBT(item: ItemStack, key: String): Any? {
        val nms = itemStackToNMS(item) as net.minecraft.server.v1_16_R3.ItemStack
        nms.tag?:return null
        val getTag = nms.tag!!.get(key)
        return getTag
    }

    override fun hasNBT(item: ItemStack, key: String): Boolean {
        return getItemNBT(item,key) != null
    }

    override fun getNBTKeys(item: ItemStack): Set<String> {
        val nms = itemStackToNMS(item) as net.minecraft.server.v1_16_R3.ItemStack
        nms.tag?:return HashSet<String>()
        return nms.tag!!.keys
    }

    override fun setSkullItem(item: ItemStack, textureValue: String): ItemStack {
        val nms = itemStackToNMS(item) as net.minecraft.server.v1_16_R3.ItemStack
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