package cn.HiaXnLib.utils;

import org.bukkit.Bukkit;

public class NMSUtil {
    /**
     * 返回反射包信息
     * @param className 类名
     * @return 用于Class.forName的一个String
     */
    public static String path(String className){
        return "net.minecraft.server."+ getVersion()+ "." + className;
    }
    public static Class getClass(String className) throws ClassNotFoundException{
        return Class.forName(path(className));
    }
    public static String getVersion(){
        String[] split = Bukkit.getServer().getClass().getPackage().toString().split("\\.");
        String[] split1 = split[3].split(",");
        return split1[0];
    }
}
