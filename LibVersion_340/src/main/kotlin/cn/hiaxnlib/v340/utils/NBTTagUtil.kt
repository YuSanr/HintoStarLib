package cn.hiaxnlib.v340.utils

import cn.hiaxnlib.lib.item.HiaXnTag
import cn.hiaxnlib.lib.utils.INBTTagUtil
import cn.hiaxnlib.lib.version.VersionUtil
import org.bukkit.inventory.ItemStack
import java.util.*

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
        val nms = craftItemStackCLS.getMethod("asNMSCopy",ItemStack::class.java).invoke(null,item)?:return null
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
        //public void addSkullNBT(ItemStack item, String base64) {
        //  try {
        //    // 获取ItemStack类和net.minecraft.server.ItemStack类的Class对象
        //    Class<?> itemClass = Class.forName("org.bukkit.inventory.ItemStack");
        //    Class<?> nmsItemClass = Class.forName("net.minecraft.server.ItemStack");
        //    // 获取CraftItemStack类的asNMSCopy方法的Method对象
        //    Method asNMSCopy = Class.forName("org.bukkit.craftbukkit.inventory.CraftItemStack").getMethod("asNMSCopy", itemClass);
        //    // 调用asNMSCopy方法，将ItemStack对象转换为net.minecraft.server.ItemStack对象
        //    Object nmsItem = asNMSCopy.invoke(null, item);
        //    // 获取net.minecraft.server.ItemStack类的getTag和setTag方法的Method对象
        //    Method getTag = nmsItemClass.getMethod("getTag");
        //    Method setTag = nmsItemClass.getMethod("setTag", Class.forName("net.minecraft.server.NBTTagCompound"));
        //    // 调用getTag方法，获取或创建ItemStack NBT标签对象
        //    Object itemTag = getTag.invoke(nmsItem);
        //    if (itemTag == null) {
        //      itemTag = Class.forName("net.minecraft.server.NBTTagCompound").newInstance();
        //    }
        //    // 创建头颅NBT数据对象
        //    Object skullTag = Class.forName("net.minecraft.server.NBTTagCompound").newInstance();
        //    // 创建材质数据对象
        //    Object textureTag = Class.forName("net.minecraft.server.NBTTagCompound").newInstance();
        //    Method setString = textureTag.getClass().getMethod("setString", String.class, String.class);
        //    setString.invoke(textureTag, "Value", base64);
        //
        //  } catch (Exception e) {
        //      e.printStackTrace();
        //  }
        //}
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
        val itemStackNMSCLS = Class.forName("net.minecraft.server.${VersionUtil.getVersionString()}.ItemStack")
        val craftItemStackCLS = Class.forName("org.bukkit.craftbukkit.${VersionUtil.getVersionString()}.inventory.CraftItemStack")
        val nbtTagBaseCLS = Class.forName("net.minecraft.server.${VersionUtil.getVersionString()}.NBTBase")
        val nbtTagCompoundCLS = Class.forName("net.minecraft.server.${VersionUtil.getVersionString()}.NBTTagCompound")
        val nbtTagListCLS = Class.forName("net.mincraft.server.${VersionUtil.getVersionString()}.NBTTagList")
        val nms = craftItemStackCLS.getMethod("asNMSCopy",ItemStack::class.java).invoke(null,item)
        val tag = itemStackNMSCLS.getMethod("getTag").invoke(nms)?:let{
            return@let nbtTagCompoundCLS.newInstance()
        }
        val skullOwner = nbtTagCompoundCLS.newInstance()
        nbtTagCompoundCLS.getMethod("setString",String::class.java,String::class.java).invoke(skullOwner,"Id", UUID.randomUUID().toString())
        val properties = nbtTagCompoundCLS.newInstance()
        val textureList = nbtTagListCLS.newInstance()
        val value = nbtTagCompoundCLS.newInstance()
        nbtTagCompoundCLS.getMethod("setStrng",String::class.java,String::class.java).invoke(value,"Value",texture)
        nbtTagListCLS.getMethod("add",nbtTagCompoundCLS).invoke(textureList,value)
        nbtTagCompoundCLS.getMethod("set",String::class.java,nbtTagBaseCLS).invoke(properties,"textures",textureList)
        nbtTagCompoundCLS.getMethod("set",String::class.java,nbtTagBaseCLS).invoke(skullOwner,"Properties",properties)
        nbtTagCompoundCLS.getMethod("set",String::class.java,nbtTagBaseCLS).invoke(tag,"SkullOwner",skullOwner)
        itemStackNMSCLS.getMethod("setTag",nbtTagCompoundCLS).invoke(nms,tag)
        return craftItemStackCLS.getMethod("asBukkitCopy",itemStackNMSCLS).invoke(null,nms)  as ItemStack
    }
}