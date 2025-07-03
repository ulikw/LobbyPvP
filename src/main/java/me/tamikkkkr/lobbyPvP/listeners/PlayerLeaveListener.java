package me.tamikkkkr.lobbyPvP.listeners;

import me.tamikkkkr.lobbyPvP.ItemsManager;
import me.tamikkkkr.lobbyPvP.Plugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener {

    private final ItemSlotChangeListener itemSlotChangeListener;
    private final ItemsManager itemsManager;
    private final Plugin plugin;

    public PlayerLeaveListener(ItemSlotChangeListener itemSlotChangeListener, ItemsManager itemsManager, Plugin plugin) {
        this.itemSlotChangeListener = itemSlotChangeListener;
        this.itemsManager = itemsManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {

        if (plugin.getConfig().getStringList("disabled-worlds").contains(event.getPlayer().getWorld().getName())) {
            return;
        }

        Player player = event.getPlayer();

        if (itemSlotChangeListener.isInPvp(player)) {

            itemSlotChangeListener.removeFromPvp(player);

        }

        itemsManager.takeSet(player);
        itemsManager.takeSword(player);

    }

}
