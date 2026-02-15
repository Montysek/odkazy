package org.example;

import org.bukkit.plugin.java.JavaPlugin;

public class Odkazy extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Plugin Odkazy byl spuštěn!");

        this.saveDefaultConfig();

        // Předáme instanci pluginu! (LinkCommand ji potřebuje pro reloadConfig())
        LinkCommand executor = new LinkCommand(this);

        // Zaregistrování existujících příkazů
        getCommand("discord").setExecutor(executor);
        getCommand("youtube").setExecutor(executor);
        getCommand("obchod").setExecutor(executor);

        // Zaregistrování NOVÉHO příkazu pro administraci
        getCommand("odkazy").setExecutor(executor);
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin Odkazy byl vypnut.");
    }
}