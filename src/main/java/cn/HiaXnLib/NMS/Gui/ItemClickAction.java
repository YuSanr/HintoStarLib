package cn.HiaXnLib.NMS.Gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ItemClickAction {
    // GUI的物品堆 记录位置 物品等
    private final Map<ItemStack,functionWithParse> itemAction = new HashMap<>();
    public void addAction(ItemStack item,functionWithParse action){
        itemAction.put(item,action);
    }
    public void implementAction(ItemStack item,Object... objects){
        itemAction.getOrDefault(item,(o)->{}).run(objects);
    }
    /**
     * 该方法用于单一物品点击事件执行方法
     */
    public void implementAction(Object... obj){
        for (Map.Entry<ItemStack, functionWithParse> e : itemAction.entrySet()) {
            e.getValue().run(obj);
        }
    }
}
