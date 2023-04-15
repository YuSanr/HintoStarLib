package cn.hiaxnlib;

import cn.hiaxnlib.lib.entity.armorStand.ArmorStandNode;
import cn.hiaxnlib.lib.entity.armorStand.tasks.ArmorStandDataSpawnTask;
import cn.hiaxnlib.lib.item.HiaXnItem;
import cn.hiaxnlib.lib.managers.ArmorStandManager;
import cn.hiaxnlib.lib.version.VersionUtil;
import cn.hiaxnlib.listener.ArmorStandGroupListener;
import cn.hiaxnlib.listener.BarrageListener;
import cn.hiaxnlib.listener.GuiActionListener;
import cn.hiaxnlib.listener.ItemActionListener;
import cn.hiaxnlib.lib.managers.BarrageManager;
import cn.hiaxnlib.lib.managers.GUIManager;
import cn.hiaxnlib.lib.managers.ParticleManager;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class HiaXnLib extends JavaPlugin {

    private static final HashMap<String,HiaXnItem> registerItems = new HashMap<>();
    private static BarrageManager barrageManager;
    private static ParticleManager particleManager;
    private static GUIManager guiManager;
    private static ArmorStandManager standManner;
    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new BarrageListener(),this);
        Bukkit.getPluginManager().registerEvents(new GuiActionListener(),this);
        Bukkit.getPluginManager().registerEvents(new ItemActionListener(),this);
        Bukkit.getPluginManager().registerEvents(new ArmorStandGroupListener(),this);
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        barrageManager = new BarrageManager(getConfig().getInt("BarrageThreadNumber"),this);
        particleManager = new ParticleManager(getConfig().getInt("ParticleThreadNumber"),this);
        guiManager = new GUIManager();
        standManner = new ArmorStandManager();
        // 加载实体*生成实体
        getServer().getPluginManager().registerEvents(new ItemActionListener(),this);
        getServer().getPluginManager().registerEvents(new BarrageListener(),this);
        standManner.loadEntity();
        standManner.standTask();
        new ArmorStandDataSpawnTask().runTaskTimer(this,0,10);
    }
    public static HiaXnLib getInstance() {
        return HiaXnLib.getPlugin(HiaXnLib.class);
    }
    @Override
    public void onDisable() {
        // 卸载实体
        for (ArmorStandNode queue : standManner.getSpawnEntityQueue()) {
            standManner.getArmorStandGroupsMap().put(queue.getEntityUUID(),queue);
        }
        standManner.unloadAllEntity();
    }

    public static BarrageManager getBarrageManager() {
        return barrageManager;
    }
    public static ParticleManager getParticleManager() {
        return particleManager;
    }
    public static ArmorStandManager getArmorStandManner(){return standManner;}
    public static GUIManager getGUIManager() {
        return guiManager;
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
