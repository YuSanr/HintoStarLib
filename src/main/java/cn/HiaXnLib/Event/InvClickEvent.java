package cn.HiaXnLib.Event;

import cn.HiaXnLib.NMS.Gui.GUIUtil;
import cn.HiaXnLib.NMS.Gui.ItemClickAction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InvClickEvent implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getClickedInventory();
        if (GUIUtil.isRegisterGUI(inv)){
            event.setCancelled(true);
        }
        if (event.getRawSlot() < 0 || event.getRawSlot() > event.getInventory().getSize()) {
            // 这个方法来源于 Bukkit Development Note
            // 如果在合理的范围内，getRawSlot 会返回一个合适的编号（0 ~ 物品栏大小-1）
            return;
            // 结束处理，使用 return 避免了多余的 else
        }
        ItemStack item = event.getCurrentItem();
        if (!GUIUtil.isRegisterGUI(inv)){
            return;
        }
        if (item!=null){
            if(GUIUtil.isRegisterAction(inv)){
                // 传入事件 用于实际处理
                GUIUtil.registerAction.getOrDefault(inv,new ItemClickAction()).implementAction(item,event);
            }
        }
    }
}
