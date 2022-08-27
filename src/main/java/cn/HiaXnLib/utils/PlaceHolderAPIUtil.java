package cn.HiaXnLib.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

public class PlaceHolderAPIUtil {
    public static void registerPlaceHolders(PlaceholderExpansion expansion){
        expansion.register();
    }
    public static String getPlaceHolderStr(OfflinePlayer player,String message){
        return PlaceholderAPI.setPlaceholders(player,setColor.setColor(message));
    }
}
