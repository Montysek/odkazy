package org.example;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class LinkCommand implements CommandExecutor {

    private final Odkazy plugin; // Uložíme instanci pluginu pro reloadConfig()
    private FileConfiguration config;

    // Konstruktor
    public LinkCommand(Odkazy plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig(); // Získáme aktuální konfiguraci
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        // --- NOVÁ LOGIKA PRO PŘÍKAZ /ODKAZY RELOAD ---
        if (label.equalsIgnoreCase("odkazy")) {
            // Kontrola, zda zadal argument 'reload'
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {

                // Kontrola oprávnění (sender.hasPermission se postará o kontrolu oprávnění z plugin.yml)
                if (sender.hasPermission("odkazy.reload") || sender.isOp() || !(sender instanceof Player)) {

                    // Provede skutečný reload konfigurace ze souboru
                    plugin.reloadConfig();

                    // Aktualizujeme interní referenci na konfiguraci pro okamžité použití
                    this.config = plugin.getConfig();

                    sender.sendMessage("§a[Odkazy] Konfigurace byla úspěšně znovu načtena.");
                    return true;
                }
            } else {
                // Zobrazí nápovědu, pokud zadají jen /odkazy
                sender.sendMessage("§e[Odkazy] Použití: /odkazy reload - Znovu načte konfiguraci.");
                return true;
            }
        }
        // --- KONEC NOVÉ LOGIKY ---


        // Původní logika pro klikací odkazy (vyžaduje hráče)
        String pouzeHrac = config.getString("Systemove_Zpravy.Pouze_Hrac", "§cTento příkaz mohou používat pouze hráči.");

        if (!(sender instanceof Player)) {
            sender.sendMessage(pouzeHrac);
            return true;
        }

        Player player = (Player) sender;

        if (label.equalsIgnoreCase("discord")) {
            sendClickableLink(player,
                    config.getString("Systemove_Zpravy.Prefix_Discord"),
                    config.getString("Zpravy.Discord_Zprava"),
                    config.getString("Odkazy.Discord_URL"));

        } else if (label.equalsIgnoreCase("youtube")) {
            sendClickableLink(player,
                    config.getString("Systemove_Zpravy.Prefix_YouTube"),
                    config.getString("Zpravy.YouTube_Zprava"),
                    config.getString("Odkazy.YouTube_URL"));

        } else if (label.equalsIgnoreCase("obchod")) {
            sendClickableLink(player,
                    config.getString("Systemove_Zpravy.Prefix_Obchod"),
                    config.getString("Zpravy.Obchod_Zprava"),
                    config.getString("Odkazy.Obchod_URL"));
        }

        return true;
    }

    // Metoda pro vytvoření a odeslání zprávy s klikacím odkazem (beze změny)
    private void sendClickableLink(Player player, String prefix, String message, String url) {

        String finalPrefix = prefix != null ? prefix.replace("&", "§") : "§7[LINK]";

        TextComponent component = new TextComponent("§8[§f" + finalPrefix + "§8] §r");

        TextComponent link = new TextComponent(message != null ? message : "§cCHYBA ODKAZU");
        link.setColor(net.md_5.bungee.api.ChatColor.AQUA);
        link.setUnderlined(true);

        link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url != null ? url : "http://chyba.cz"));

        component.addExtra(link);
        player.spigot().sendMessage(component);
    }
}