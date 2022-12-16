package cn.HiaXnLib;

import cn.HiaXnLib.API.LibUtil;
import cn.HiaXnLib.API.LibUtilCls;
import cn.HiaXnLib.Listener.BarrageListener;
import cn.HiaXnLib.Particle.ParticleOwner.Barrages.util.BarrageTask;
import cn.HiaXnLib.Particle.util.ParticleTask;
import cn.HiaXnLib.command.testCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin {

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        new ParticleTask(this).runTaskTimer(this,10,1);
        new BarrageTask().runTaskTimer(this,10,1);
        Bukkit.getServicesManager().register(LibUtil.class,new LibUtilCls(),this, ServicePriority.High);
        Bukkit.getPluginCommand("testlib").setExecutor(new testCommand());
        Bukkit.getPluginManager().registerEvents(new BarrageListener(),this);
    }
    public static Plugin getInstance() {
        return main.getPlugin(main.class);
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
