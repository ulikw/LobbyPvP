package me.tamikkkkr.lobbyPvP.listeners;

import me.tamikkkkr.lobbyPvP.ItemsManager;
import me.tamikkkkr.lobbyPvP.utils.IsPvpWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerRespawnListener implements Listener {

    private final ItemsManager itemsManager;
    private final IsPvpWorld isPvpWorld;
    private final Plugin plugin;

    public PlayerRespawnListener(ItemsManager itemsManager, IsPvpWorld isPvpWorld, Plugin plugin) {
        this.itemsManager = itemsManager;
        this.isPvpWorld = isPvpWorld;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {

        if (!isPvpWorld.isPvpWorld(event.getPlayer().getWorld())) {
            return;
        }

        Player player = event.getPlayer();

        new BukkitRunnable() {
            @Override
            public void run() {
                event.getPlayer().getInventory().setHeldItemSlot(0);
            }
        }.runTaskLater(plugin, 1L);

        itemsManager.giveSword(player);

    }

}
