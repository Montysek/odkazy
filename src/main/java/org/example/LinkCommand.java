package org.example;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class LinkCommand implements CommandExecutor {

    private final Odkazy plugin;
    private FileConfiguration config;

    public LinkCommand(Odkazy plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (label.equalsIgnoreCase("odkazy")) {
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {

                if (sender.hasPermission("odkazy.reload") || sender.isOp() || !(sender instanceof Player)) {

                    plugin.reloadConfig();

                    this.config = plugin.getConfig();

                    sender.sendMessage("§a[Odkazy] Konfigurace byla úspěšně znovu načtena.");
                    return true;
                }
            } else {
                sender.sendMessage("§e[Odkazy] Použití: /odkazy reload - Znovu načte konfiguraci.");
                return true;
            }
        }


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
