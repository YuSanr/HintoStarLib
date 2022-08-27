package cn.HiaXnLib.NMS.item;

import cn.HiaXnLib.utils.getCodePath;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class ItemNMS {
    /**
     * 将NMS的物品堆转换为BukkitAPI的物品堆
     * @param obj NMS的物品堆
     * @return Bukkit的物品堆
     */
    public ItemStack NMSStackToBukkit(Object obj) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //非导包用法
        Class<?> Craft_ItemStack_Cls = Class.forName("org.bukkit.craftbukkit." + getCodePath.getVersion() + ".inventory.CraftItemStack");
        Method getBukkitItemStack = Craft_ItemStack_Cls.getMethod("asBukkitCopy", getCodePath.getClass("ItemStack"));
        // 由于调用的是静态方法 则输入CLS对象
        Object ItemStack = getBukkitItemStack.invoke(Craft_ItemStack_Cls, obj);
        return (org.bukkit.inventory.ItemStack) ItemStack;
    }
    /**
     * 将物品转换为NMS的ItemStack
     * @param item 转换的物品
     * @return 由于不能导包 则在使用内部方法时需要用到反射 返回包含NMS ItemStack数据的Object
     */
    public Object itemStackToNMS(ItemStack item) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 导包用法
//        net.minecraft.server.v1_8_R3.ItemStack itemStack = CraftItemStack.asNMSCopy(item);
        // 非导包 反射用法
        Class<?> Craft_ItemStack_cls = Class.forName("org.bukkit.craftbukkit." + getCodePath.getVersion() + ".inventory.CraftItemStack");
        // 通过调用CraftItemStack的asNMSCopy方法获取NMS对象
        Method getNMSStack = Craft_ItemStack_cls.getMethod("asNMSCopy", ItemStack.class);
        //由于是静态调用 则不需要输入对象
        //输入方法所需要的参数
        //返回值则为我们需要的itemStack 直接返回即可
        return getNMSStack.invoke(Craft_ItemStack_cls, item);
    }

    /**
     * 设置物品的NBT 并且返回Bukkit的ItemStack
     * @param item 被设置的物品堆
     * @param key NBT_KEY
     * @param value NBT_VALUE_String
     * @return 返回设置NBT后的物品堆
     */
    public ItemStack setItemNBT(ItemStack item,String key,String value) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        // 导包用法
//        net.minecraft.server.v1_8_R3.ItemStack itemStack = CraftItemStack.asNMSCopy(item);
//        NBTTagCompound tag = itemStack.getTag();
//        if(tag==null){
//            tag = new NBTTagCompound();
//        }
//        tag.setString(key,value);
//        itemStack.setTag(tag);
        // 非导包用法
        // 利用已经制定好的方法itemStackToNMS 获取物品堆的NMS对象
        Object NMS_ItemStack = itemStackToNMS(item);
        // 获取对应的Class
        Class<?> NMS_ItemStack_Cls = NMS_ItemStack.getClass();
        Method getTag = NMS_ItemStack_Cls.getMethod("getTag");
        Object tag = getTag.invoke(NMS_ItemStack);
        // 获取NBT的Class
        Class<?> NBTTag_Cls = getCodePath.getClass("NBTTagCompound");
        if(tag == null){
            tag = NBTTag_Cls.newInstance();
        }
        //获取设置NBT的方法
        Method setNBT_STR = NBTTag_Cls.getMethod("setString", String.class, String.class);
        // 设置NBT
        setNBT_STR.invoke(tag, key, value);
        // 在设置NBT之后 将Tag设置回ItemStack
        Method setTag = NMS_ItemStack_Cls.getMethod("setTag", NBTTag_Cls);
        setTag.invoke(NMS_ItemStack,tag);
        return NMSStackToBukkit(NMS_ItemStack);
//        return itemStack;
    }
    /**
     * 设置物品的NBT 并且返回Bukkit的ItemStack
     * @param item 被设置的物品堆
     * @param key NBT_KEY
     * @param value NBT_VALUE_INT
     * @return 返回设置NBT后的物品堆
     */
    public ItemStack setItemNBT(ItemStack item,String key,int value) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
//         导包用法
//        net.minecraft.server.v1_8_R3.ItemStack itemStack = CraftItemStack.asNMSCopy(item);
//        NBTTagCompound tag = itemStack.getTag();
//        if(tag==null){
//            tag = new NBTTagCompound();
//        }
//        tag.setInt(key,value);
//        itemStack.setTag(tag);
        // 非导包用法
        // 利用已经制定好的方法itemStackToNMS 获取物品堆的NMS对象
        Object NMS_ItemStack = itemStackToNMS(item);
        // 获取对应的Class
        Class<?> NMS_ItemStack_Cls = NMS_ItemStack.getClass();
        Method getTag = NMS_ItemStack_Cls.getMethod("getTag");
        Object tag = getTag.invoke(NMS_ItemStack);
        // 获取NBT的Class
        Class<?> NBTTag_Cls = getCodePath.getClass("NBTTagCompound");
        if(tag == null){
            tag = NBTTag_Cls.newInstance();
        }
        //获取设置NBT的方法
        Method setNBT_STR = NBTTag_Cls.getMethod("setInt", String.class, int.class);
        // 设置NBT
        setNBT_STR.invoke(tag, key, value);
        // 在设置NBT之后 将Tag设置回ItemStack
        Method setTag = NMS_ItemStack_Cls.getMethod("setTag", NBTTag_Cls);
        setTag.invoke(NMS_ItemStack,tag);
        return NMSStackToBukkit(NMS_ItemStack);
//        return itemStack;
    }
    /**
     * 设置物品的NBT 并且返回Bukkit的ItemStack
     * @param item 被设置的物品堆
     * @param key NBT_KEY
     * @param value NBT_VALUE_Double
     * @return 返回设置NBT后的物品堆
     */
    public ItemStack setItemNBT(ItemStack item,String key,double value) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        // 导包用法
//        net.minecraft.server.v1_8_R3.ItemStack itemStack = CraftItemStack.asNMSCopy(item);
//        NBTTagCompound tag = itemStack.getTag();
//        if(tag==null){
//            tag = new NBTTagCompound();
//        }
//        tag.set(key,value);
//        itemStack.setTag(tag);
        // 非导包用法
        // 利用已经制定好的方法itemStackToNMS 获取物品堆的NMS对象
        Object NMS_ItemStack = itemStackToNMS(item);
        // 获取对应的Class
        Class<?> NMS_ItemStack_Cls = NMS_ItemStack.getClass();
        Method getTag = NMS_ItemStack_Cls.getMethod("getTag");
        Object tag = getTag.invoke(NMS_ItemStack);
        // 获取NBT的Class
        Class<?> NBTTag_Cls = getCodePath.getClass("NBTTagCompound");
        if(tag == null){
            tag = NBTTag_Cls.newInstance();
        }
        //获取设置NBT的方法
        Method setNBT_STR = NBTTag_Cls.getMethod("setDouble", String.class, double.class);
        // 设置NBT
        setNBT_STR.invoke(tag, key, value);
        // 在设置NBT之后 将Tag设置回ItemStack
        Method setTag = NMS_ItemStack_Cls.getMethod("setTag", NBTTag_Cls);
        setTag.invoke(NMS_ItemStack,tag);
        return NMSStackToBukkit(NMS_ItemStack);
//        return itemStack;
    }
    /**
     * 设置物品的NBT 并且返回Bukkit的ItemStack
     * @param item 被设置的物品堆
     * @param key NBT_KEY
     * @param value NBT_VALUE_Long
     * @return 返回设置NBT后的物品堆
     */
    public ItemStack setItemNBT(ItemStack item,String key,long value) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        // 导包用法
//        net.minecraft.server.v1_8_R3.ItemStack itemStack = CraftItemStack.asNMSCopy(item);
//        NBTTagCompound tag = itemStack.getTag();
//        if(tag==null){
//            tag = new NBTTagCompound();
//        }
//        tag.set(key,value);
//        itemStack.setTag(tag);
        // 非导包用法
        // 利用已经制定好的方法itemStackToNMS 获取物品堆的NMS对象
        Class<?> NBTLong_CLS = getCodePath.getClass("NBTTagLong");
        Object nbtTagLong = NBTLong_CLS.getConstructor(long.class).newInstance(value);
        return setItemNBT(item,key,nbtTagLong);
//        return itemStack;
    }
    /**
     * 设置物品的NBT 并且返回Bukkit的ItemStack
     * @param item 被设置的物品堆
     * @param key NBT_KEY
     * @param value NBT_VALUE_Long
     * @return 返回设置NBT后的物品堆
     */
    public ItemStack setItemNBT(ItemStack item,String key,Long value) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        // 导包用法
//        net.minecraft.server.v1_8_R3.ItemStack itemStack = CraftItemStack.asNMSCopy(item);
//        NBTTagCompound tag = itemStack.getTag();
//        if(tag==null){
//            tag = new NBTTagCompound();
//        }
//        tag.set(key,value);
//        itemStack.setTag(tag);
        // 非导包用法
        // 利用已经制定好的方法itemStackToNMS 获取物品堆的NMS对象
        Class<?> NBTLong_CLS = getCodePath.getClass("NBTTagLong");
        Object nbtTagLong = NBTLong_CLS.getConstructor(long.class).newInstance(value);
        return setItemNBT(item,key,nbtTagLong);
//        return itemStack;
    }
    /**
     * 直接通过NBT设置物品堆
     * @param item 设置的物品
     * @param tag NBT
     * @return 设置后的物品堆
     */
    public ItemStack setItemTag(ItemStack item,Object tag) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Object itemNMS = itemStackToNMS(item);
        Class<?> itemNMS_CLS = getCodePath.getClass("ItemStack");
        Method method = itemNMS_CLS.getMethod("setTag", getCodePath.getClass("NBTTagCompound"));
        // setTag没有返回值
        method.invoke(itemNMS, tag);
        return NMSStackToBukkit(itemNMS);
    }
    /**
     * 设置物品的NBT 并且返回Bukkit的ItemStack
     * @param item 被设置的物品堆
     * @param key NBT_KEY
     * @param value NBT_VALUE_NBTBase
     * @return 返回设置NBT后的物品堆
     */
    public ItemStack setItemNBT(ItemStack item,String key,Object value) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        // 导包用法
//        net.minecraft.server.v1_8_R3.ItemStack itemStack = CraftItemStack.asNMSCopy(item);
//        NBTTagCompound tag = itemStack.getTag();
//        if(tag==null){
//            tag = new NBTTagCompound();
//        }
//        tag.set(key,value);
//        itemStack.setTag(tag);
        // 非导包用法
        // 利用已经制定好的方法itemStackToNMS 获取物品堆的NMS对象
        Object NMS_ItemStack = itemStackToNMS(item);
        // 获取对应的Class
        Class<?> NMS_ItemStack_Cls = NMS_ItemStack.getClass();
        Method getTag = NMS_ItemStack_Cls.getMethod("getTag");
        // 由于是NBTBase 包含各种子类 因为不能导包 于是参数只能选择Object
        Class<?> NBTBaseCls = getCodePath.getClass("NBTBase");
        Object tag = getTag.invoke(NMS_ItemStack);
        // 获取NBT的Class
        Class<?> NBTTag_Cls = getCodePath.getClass("NBTTagCompound");
        if(tag.getClass() == null){
            tag = NBTTag_Cls.newInstance();
        }
        //获取设置NBT的方法
        Method setNBT_STR = NBTTag_Cls.getMethod("set", String.class, NBTBaseCls);
        // 设置NBT
        setNBT_STR.invoke(tag, key, value);
        // 在设置NBT之后 将Tag设置回ItemStack
        Method setTag = NMS_ItemStack_Cls.getMethod("setTag", NBTTag_Cls);
        setTag.invoke(NMS_ItemStack,tag);
        return NMSStackToBukkit(NMS_ItemStack);
//        return itemStack;
    }

    /**
     * 获取NBTTagCompound 用于比较 设置
     * @param item Bukkit的物品堆
     * @return 物品堆附带的NBT
     */
    public Object getNMSItemStack_NBT(ItemStack item) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        // 将该Tag return
        // 导包用法
//        NBTTagCompound tag = CraftItemStack.asNMSCopy(item).getTag();
        // 非导包用法
        Object itemNMS = itemStackToNMS(item);
        if (itemNMS == null) return null;
        Class<?> itemNMS_Cls = getCodePath.getClass("ItemStack");
        return itemNMS_Cls.getMethod("getTag").invoke(itemNMS);
    }

    public boolean hasNBT(ItemStack item,String key) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        // 导包方法
//        net.minecraft.server.v1_8_R3.ItemStack itemStack = CraftItemStack.asNMSCopy(item);
//        NBTTagCompound tag = itemStack.getTag();
//        if(tag==null){
//            tag = new NBTTagCompound();
//        }
//        tag.hasKey(key);
        // 非导包方法
        Object nmsNBT = getNMSItemStack_NBT(item);
        if (nmsNBT == null)return false;
        Class<?> nmsNBT_CLS = nmsNBT.getClass();
        Method nbtBase = nmsNBT_CLS.getMethod("get", String.class);
        Object invoke = nbtBase.invoke(nmsNBT, key);
        return invoke!=null;
    }

    /**
     * 获取物品的NBTBase
     * @param item 要获取的物品
     * @param key NBT的索引
     * @return 获取的物品 key不存在时 返回Null
     */
    public Object getNbtBase(ItemStack item,String key) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        // 在获取NBT之前首先要判断物品是否有这个NBT选项
        if (hasNBT(item,key)){
            // 导包方法
//            net.minecraft.server.v1_8_R3.ItemStack itemStack = CraftItemStack.asNMSCopy(item);
//            return itemStack.getTag().get(key);
            // 非导包方法
            Object nmsNBT = getNMSItemStack_NBT(item);
            Class<?> nmsNBT_CLS = nmsNBT.getClass();
            Method nbtBase = nmsNBT_CLS.getMethod("get", String.class);
            return nbtBase.invoke(nmsNBT, key);
        }else {
            return null;
        }
    }

    /**
     * 设置头颅的皮肤
     * @param item 头颅物品
     * @param textures 头颅材质
     * @return 设置后的头颅物品
     */
    public ItemStack setSkullItem(ItemStack item,String textures) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
//         导包方法
//        net.minecraft.server.v1_8_R3.ItemStack itemNMS = (net.minecraft.server.v1_8_R3.ItemStack) itemStackToNMS(item);
//        itemNMS.getTag().c()
//        NBTTagCompound tag = itemNMS.getTag();
//        NBTTagCompound skullOwner = new NBTTagCompound();
//        skullOwner.setString("Id",ownerID);
//        NBTTagCompound Properties = new NBTTagCompound();
//        NBTTagList texture = new NBTTagList();
//        NBTTagCompound value = new NBTTagCompound();
//        value.setString("Value",textures);
//        texture.add(value);
//        Properties.set("textures",texture);
//        skullOwner.set("Properties",Properties);
//        tag.set("SkullOwner",skullOwner);
//        itemNMS.setTag(tag);
        // 非导包方法
        Object itemNMS = itemStackToNMS(item);
        Class<?> itemNMS_Cls = getCodePath.getClass("ItemStack");
        Object Tag = itemNMS_Cls.getMethod("getTag").invoke(itemNMS);
        Class<?> tag_CLS = getCodePath.getClass("NBTTagCompound");
        Class<?> tagList_CLS = getCodePath.getClass("NBTTagList");
        Object skullOwner = tag_CLS.newInstance();
        Object Properties = tag_CLS.newInstance();
        Object value = tag_CLS.newInstance();
        Object texture = tagList_CLS.newInstance();
        tag_CLS.getMethod("setString",String.class,String.class).invoke(skullOwner,"Id", UUID.randomUUID().toString());
        tag_CLS.getMethod("setString",String.class,String.class).invoke(value,"Value",textures);
        // 把Value写入Textures -- NBT的格式是这样的 就纯傻逼
        tagList_CLS.getMethod("add",getCodePath.getClass("NBTBase")).invoke(texture,value);
        // 吧textures传入Properties
        tag_CLS.getMethod("set",String.class,getCodePath.getClass("NBTBase")).invoke(Properties,"textures",texture);
        // 最后吧Properties传入SkullOwner
        tag_CLS.getMethod("set",String.class,getCodePath.getClass("NBTBase")).invoke(skullOwner,"Properties",Properties);
        // 最后在吧SkullOwner写入Item的NBT里
        tag_CLS.getMethod("set",String.class,getCodePath.getClass("NBTBase")).invoke(Tag,"SkullOwner",skullOwner);
        return setItemTag(item,Tag);
    }
}
