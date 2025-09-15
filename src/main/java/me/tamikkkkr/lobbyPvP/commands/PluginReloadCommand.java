package me.tamikkkkr.lobbyPvP.commands;

import me.tamikkkkr.lobbyPvP.ItemsManager;
import me.tamikkkkr.lobbyPvP.Plugin;
import me.tamikkkkr.lobbyPvP.listeners.ItemSlotChangeListener;
import me.tamikkkkr.lobbyPvP.utils.LoadItems;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class PluginReloadCommand implements CommandExecutor {

    private final Plugin plugin;
    private final LoadItems loadItems;
    private final ItemsManager itemsManager;
    private final ItemSlotChangeListener itemSlotChangeListener;

    public PluginReloadCommand(Plugin plugin, LoadItems loadItems, ItemsManager itemsManager, ItemSlotChangeListener itemSlotChangeListener) {
        this.plugin = plugin;
        this.loadItems = loadItems;
        this.itemsManager = itemsManager;
        this.itemSlotChangeListener = itemSlotChangeListener;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, String[] args) {

        if (args.length == 1) {

            if (args[0].equalsIgnoreCase("reload")) {

                if (sender instanceof Player) {

                    if (sender.hasPermission("lobbypvp.reload")) {

                        plugin.reloadConfig();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(
                                "lang.reloaded", "&aSuccessfully reloaded the HubPvP config!")));
                        loadItems.loadItems();

                        for (Player player: Bukkit.getServer().getOnlinePlayers()) {
                            itemsManager.takeSet(player);
                            itemsManager.takeSword(player);
                            itemSlotChangeListener.removeFromPvp(player);
                            itemsManager.giveSword(player);
                        }

                        return true;

                    }

                    else {

                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lang.no-permissions", "&cYou don't have permission!")));
                        return true;

                    }

                } else {

                    plugin.reloadConfig();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(
                            "lang.reloaded", "&aSuccessfully reloaded the HubPvP config!")));
                    loadItems.loadItems();

                    for (Player player: Bukkit.getServer().getOnlinePlayers()) {
                        itemsManager.takeSet(player);
                        itemsManager.takeSword(player);
                        itemSlotChangeListener.removeFromPvp(player);
                        itemsManager.giveSword(player);
                    }

                    return true;
                }

            }

            else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lang.usage", "&cUsage: /lobbypvp reload")));
                return true;
            }

        }

        else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lang.usage", "&cUsage: /lobbypvp reload")));
            return true;
        }

    }
}
