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

public class PlayerJoinListener implements Listener {

    private final ItemsManager giveItems;
    private final IsPvpWorld isPvpWorld;

    public PlayerJoinListener(ItemsManager giveItems, IsPvpWorld isPvpWorld) {
        this.giveItems = giveItems;
        this.isPvpWorld = isPvpWorld;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        if (!isPvpWorld.isPvpWorld(event.getPlayer().getWorld())) {
            return;
        }

        Player player = event.getPlayer();

        player.setHealth(20);

        giveItems.giveSword(player);


    }

}
