package cn.hiaxnlib;

import cn.hiaxnlib.lib.item.HiaXnItem;
import cn.hiaxnlib.lib.version.VersionUtil;
import cn.hiaxnlib.listener.BarrageListener;
import cn.hiaxnlib.listener.ItemActionListener;
import cn.hiaxnlib.lib.manner.BarrageManner;
import cn.hiaxnlib.lib.manner.GUIManner;
import cn.hiaxnlib.lib.manner.ParticleManner;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class HiaXnLib extends JavaPlugin {
    private static final HashMap<String,HiaXnItem> registerItems = new HashMap<>();
    private static BarrageManner barrageManner;
    private static ParticleManner particleManner;
    private static GUIManner guiManner;
    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new BarrageListener(),this);
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        barrageManner = new BarrageManner(getConfig().getInt("BarrageThreadNumber"),this);
        particleManner = new ParticleManner(getConfig().getInt("ParticleThreadNumber"),this);
        guiManner = new GUIManner();
        getServer().getPluginManager().registerEvents(new ItemActionListener(),this);
        getServer().getPluginManager().registerEvents(new BarrageListener(),this);
    }
    public static HiaXnLib getInstance() {
        return HiaXnLib.getPlugin(HiaXnLib.class);
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static BarrageManner getBarrageManner() {
        return barrageManner;
    }


    public static ParticleManner getParticleManner() {
        return particleManner;
    }

    public static GUIManner getGuiManner() {
        return guiManner;
    }

    public static void registerItem(HiaXnItem item){
        registerItems.put(item.getUuid(),item);
    }
    public static boolean itemRegistered(String uuid){
        return registerItems.containsKey(uuid);
    }
    public static boolean isHiaXnItem(ItemStack item){
        String uuid = VersionUtil.INSTANCE.getNBTTagUtil().getNBTTag(item, "UUID");
        if (uuid == null) return  false;
        return registerItems.containsKey(uuid);
    }
    public static HiaXnItem toHiaXnItem(ItemStack item){
        String uuid = VersionUtil.INSTANCE.getNBTTagUtil().getNBTTag(item, "UUID");
        if (uuid == null) return  null;
        return registerItems.get(uuid);
    }
}
