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
        TODO("Not yet implemented")
    }

    override fun getNBTTag(item: ItemStack, key: String): String? {
        TODO("Not yet implemented")
    }

    override fun removeTag(item: ItemStack, key: String): ItemStack {
        TODO("Not yet implemented")
    }

    override fun setSkullTexture(item: ItemStack, texture: String): ItemStack? {
        TODO("Not yet implemented")
    }
}