package cn.HiaXnLib.NMS.Gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GUIUtil {
    public static final Set<Inventory> registeredInv = new HashSet<>();
    public static final HashMap<Inventory,ItemClickAction> registerAction = new HashMap<>();
    public static void OpenGUIForPlayer(Player player, Inventory inv){
        player.openInventory(inv);
    }
    public static Inventory CreateInventory(Player player,int size,String title){
        return Bukkit.createInventory(player,size,title);
    }
    public static Inventory CreateInventory(Player player, String title, InventoryType type){
        return Bukkit.createInventory(player,type,title);
    }
    public static Inventory drewInventory(Inventory inventory, int position, ItemStack itemStack){
        inventory.setItem(position,itemStack);
        return inventory;
    }
    public static void RegisterGui(Inventory inv){
        registeredInv.add(inv);
    }
    public static void registerAction(Inventory inventory, ItemClickAction action){
        registerAction.put(inventory,action);
    }
    public static boolean isRegisterGUI(Inventory inv){
        return registeredInv.contains(inv);
    }
    public static boolean isRegisterAction(Inventory inv){
        return registerAction.containsKey(inv);
    }

}
