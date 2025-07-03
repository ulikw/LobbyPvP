package me.tamikkkkr.lobbyPvP.listeners;

import me.tamikkkkr.lobbyPvP.ItemsManager;
import me.tamikkkkr.lobbyPvP.Plugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final ItemsManager giveItems;
    private final Plugin plugin;

    public PlayerJoinListener(ItemsManager giveItems, Plugin plugin) {
        this.giveItems = giveItems;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        if (plugin.getConfig().getStringList("disabled-worlds").contains(event.getPlayer().getWorld().getName())) {
            return;
        }

        Player player = event.getPlayer();

        player.setHealth(20);

        if (!(plugin.getConfig().getStringList("disabled-worlds").contains(player.getWorld().getName()))) {

            giveItems.giveSword(player);

        }

    }

}
