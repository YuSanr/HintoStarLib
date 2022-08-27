package cn.HiaXnLib.NMS.item;

import cn.HiaXnLib.myException.itemNotEnoughException;
import cn.HiaXnLib.utils.ArrayEquals;
import cn.HiaXnLib.utils.getCodePath;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;
import java.util.Map;

public class itemUtil extends ItemNMS {

    /**
     * 在玩家的背包容器内移除物品
     * @param player 被移除的玩家
     * @param item 移除的物品堆 [检测的是lore name 和材质]  忽视了物品堆的amount
     * @param Amount 移除的数量
     * @throws itemNotEnoughException 玩家背包内的该物品太少
     */
    public void removeItem(Player player,ItemStack item,int Amount) throws itemNotEnoughException {
        int i;
        int am = Amount;
        if ((i = getInvItemAmount(player,item)) !=0){
            if (i>=Amount && am >0){
                int slot = 0;
                for (ItemStack itemInv : player.getInventory().getContents()) {
                    if (itemInv != null) {
                        if (itemEquals(item,itemInv)) {
                            int theAmont = itemInv.getAmount();
                                if (am>0){
                                    if (theAmont - am < 0) {
                                        am -= theAmont;
                                        itemInv.setAmount(0);
                                        player.getInventory().setItem(slot, itemInv);
                                    } else {
                                        if (theAmont - am != 0) {
                                            itemInv.setAmount(theAmont - am);
                                            am = 0;
                                            player.getInventory().setItem(slot, itemInv);
                                        } else {
                                            am = 0;
                                            player.getInventory().setItem(slot, null);
                                        }
                                    }
                                }else {
                                    break;
                                }
                        }
                    }
                    slot++;
                }
            }else {
                throw new itemNotEnoughException();
            }
        }
    }
    public void removeItem(Player player,ItemStack item,int Amount,equalsFunction function) throws itemNotEnoughException {
        int i;
        int am = Amount;
        if ((i = getInvItemAmount(player,item)) !=0){
            if (i>=Amount && am >0){
                int slot = 0;
                for (ItemStack itemInv : player.getInventory().getContents()) {
                    if (itemInv != null) {
                        if (function.equals(item,itemInv)) {
                            int theAmont = itemInv.getAmount();
                            if (am>0){
                                if (theAmont - am < 0) {
                                    am -= theAmont;
                                    itemInv.setAmount(0);
                                    player.getInventory().setItem(slot, itemInv);
                                } else {
                                    if (theAmont - am != 0) {
                                        itemInv.setAmount(theAmont - am);
                                        am = 0;
                                        player.getInventory().setItem(slot, itemInv);
                                    } else {
                                        am = 0;
                                        player.getInventory().setItem(slot, null);
                                    }
                                }
                            }else {
                                break;
                            }
                        }
                    }
                    slot++;
                }
            }else {
                throw new itemNotEnoughException();
            }
        }
    }
    /**
     * 在玩家背包内添加物品
     * @param player 被添加物品的玩家
     * @param item 添加的物品
     * @param Amount 添加的物品数量
     */
    public void addItem(Player player, ItemStack item, int Amount){
        int total = Amount;
        ItemStack[] contents = player.getInventory().getContents();
        for (int slot = 0; slot < contents.length; slot++) {

            if (contents[slot] == null) {
                // 检测物品是否已经完全添加
                if (total == 0)return;
                if (total >= item.getMaxStackSize()) {
                    // 由于物品数量>64 则需要分次给予
                    total -= item.getAmount();
                    item.setAmount(item.getMaxStackSize());
                    player.getInventory().setItem(slot, item);
                } else {
                    item.setAmount(total);
                    // 由于物品不足64个 则代表已经完全添加 则设置为0
                    total = 0;
                    player.getInventory().setItem(slot, item);
                }
            }
        }
    }
    /**
     * 获取背包内某个物品的数量
     * @param player 被获取玩家的对象
     * @param itemStack 检测物品的数量的物品
     * @return 背包拥有那个物品的数量
     */
    public int getInvItemAmount(Player player, ItemStack itemStack){
        int amount = 0;
        // lore 检测
        // displayName 检测
        for (ItemStack content : player.getInventory().getContents()) {
            if (content !=null){
                if (itemEquals(itemStack,content)){
                    amount += content.getAmount();
                }
            }
        }
        return amount;
    }
    /**
     * 判断物品是否为同一个物品 - 屏蔽了不同的数量
     * @param item1 比较物品1
     * @param item2 比较物品2
     * @return 物品是否相同 - Lore displayname 材料  本检测忽略了数量
     */
    public boolean itemEquals(ItemStack item1,ItemStack item2){
        if (item1 == null || item2 == null){
            return false;
        }else {
            ItemMeta itemMeta = item1.getItemMeta();
            ItemMeta itemMeta1 = item2.getItemMeta();
            if (item1.getType().equals(item2.getType())){
                if (item1.getEnchantments().isEmpty() && item2.getEnchantments().isEmpty()){
                    if (itemMeta.getLore() == null && itemMeta1.getLore() == null){
                        return itemMeta.getDisplayName().equals(itemMeta1.getDisplayName());
                    }else if(itemMeta.getLore() ==null || itemMeta1.getLore() == null){
                        return false;
                    }else return ArrayEquals.listEquals(itemMeta.getLore(), itemMeta1.getLore());
                }else if (item1.getEnchantments().isEmpty() || item2.getEnchantments().isEmpty()){
                    return false;
                }else {
                    // 拥有附魔
                    Map<Enchantment, Integer> en1 = item1.getEnchantments();
                    Map<Enchantment, Integer> en2 = item2.getEnchantments();
                    if (ArrayEquals.mapEquals(en1,en2)){
                        if (itemMeta.getLore() == null && itemMeta1.getLore() == null){
                            return itemMeta.getDisplayName().equals(itemMeta1.getDisplayName());
                        }else if(itemMeta.getLore() ==null || itemMeta1.getLore() == null){
                            return false;
                        }else if(ArrayEquals.listEquals(itemMeta.getLore(),itemMeta1.getLore())){
                            return true;
                        }else {
                            return false;
                        }
                    }else {
                        return false;
                    }
                }
            }else {
                return false;
            }
        }
    }
    public boolean ListContains(List<ItemStack> items,ItemStack item){
        boolean f = false;
        for (ItemStack itemStack : items) {
            if (itemEquals(item,itemStack)){
                return !f;
            }
        }
        return f;
    }
    public boolean MapContainsKey(Map<ItemStack,?> items,ItemStack item){
        boolean f = false;
        for (Map.Entry<ItemStack, ?> en : items.entrySet()) {
            if (itemEquals(item,en.getKey())){
                return !f;
            }
        }
        return f;
    }
    public boolean MapContainsValue(Map<?,ItemStack> items,ItemStack item){
        boolean f = false;
        for (Map.Entry<?, ItemStack> en : items.entrySet()) {
            if (itemEquals(item,en.getValue())){
                return !f;
            }
        }
        return f;
    }
    /**
     * 将玩家背包内的物品修改为另外一个物品
     * @param player 修改的玩家 目标
     * @param itemTarget 要修改的物品
     * @param itemSet 修改之后的物品
     * @param itsAll 是否修改背包内所有符合itemTarget的物品
     */
    public void setItemInv(Player player,ItemStack itemTarget,ItemStack itemSet,boolean itsAll){
        ItemStack[] contents = player.getInventory().getContents();
        for (int i = 0; i < contents.length; i++) {

            if (contents[i] != null){

                if (!itsAll){
                    if (itemEquals(contents[i],itemTarget)){
                        player.getInventory().setItem(i,itemSet);
                        break;
                    }
                }else {
                    if (itemEquals(contents[i],itemTarget)){
                        player.getInventory().setItem(i,itemSet);
                    }
                }
            }
        }
    }
}
