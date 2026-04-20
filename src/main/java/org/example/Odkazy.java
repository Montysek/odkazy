package org.example;

import org.bukkit.plugin.java.JavaPlugin;

public class Odkazy extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Plugin Odkazy byl spuštěn!");

        this.saveDefaultConfig();

        LinkCommand executor = new LinkCommand(this);

        getCommand("discord").setExecutor(executor);
        getCommand("youtube").setExecutor(executor);
        getCommand("obchod").setExecutor(executor);

        getCommand("odkazy").setExecutor(executor);
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin Odkazy byl vypnut.");
    }
}
