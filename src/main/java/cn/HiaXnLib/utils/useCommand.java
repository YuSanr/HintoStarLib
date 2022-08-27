package cn.HiaXnLib.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class useCommand {

    /**
     * 检测指令开头 来判断是哪一种执行者
     * @param AllCommand 指令全称
     * @return 执行者类别
     */
    public static senderType getSenderType(String AllCommand){

        String theCommand = "";
        char[] chars = AllCommand.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[0] == '[' &&chars[i] == ']'){

                theCommand = new String(chars,0,i+1);
                break;
            }
        }
        switch (theCommand.toLowerCase()){
            case "player":
                return senderType.player;
            case "console":
                return senderType.console;
        }


        return senderType.player;
    }

    public static void sendCommand (Player player,String command){
        String theCommand = "";
        char[] chars = command.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[0] == '[' &&chars[i] == ']'){

                theCommand = new String(chars,0,i+1);
                break;
            }
        }
        command = command.replace(theCommand,"");
        player.chat("/" + setColor.setColor(PlaceholderAPI.setPlaceholders(player,command)));

    }
    public static void sendCommand (ConsoleCommandSender sender, String command,Player target){
        String theCommand = "";
        char[] chars = command.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[0] == '[' &&chars[i] == ']'){

                theCommand = new String(chars,0,i+1);
                break;
            }
        }
        command = command.replace(theCommand,"");
        Bukkit.dispatchCommand(sender,setColor.setColor(PlaceholderAPI.setPlaceholders(target,command)));

    }
    public enum senderType{
        player,console
    }
}
