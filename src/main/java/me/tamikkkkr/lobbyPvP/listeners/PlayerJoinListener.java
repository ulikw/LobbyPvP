package me.tamikkkkr.lobbyPvP.listeners;

import me.tamikkkkr.lobbyPvP.ItemsManager;
import me.tamikkkkr.lobbyPvP.Plugin;
import me.tamikkkkr.lobbyPvP.utils.IsPvpWorld;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerJoinListener implements Listener {

    private final ItemsManager giveItems;
    private final IsPvpWorld isPvpWorld;
    private final Plugin plugin;

    public PlayerJoinListener(ItemsManager giveItems, IsPvpWorld isPvpWorld, Plugin plugin) {
        this.giveItems = giveItems;
        this.isPvpWorld = isPvpWorld;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

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

        player.setHealth(20);

        giveItems.giveSword(player);


    }

}
