package me.fishergee.rileygiveall;

import org.bukkit.plugin.java.JavaPlugin;

public final class RileyGiveall extends JavaPlugin {

    public static RileyGiveall plugin;

    @Override
    public void onEnable(){
        plugin = this;
        loadConfig();
        registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
    public void registerCommands(){
        getCommand("giveall").setExecutor(new GiveallCmd());
    }

}
