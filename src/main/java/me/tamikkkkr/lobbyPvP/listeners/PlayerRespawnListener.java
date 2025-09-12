package me.tamikkkkr.lobbyPvP.listeners;

import me.tamikkkkr.lobbyPvP.ItemsManager;
import me.tamikkkkr.lobbyPvP.utils.IsPvpWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

    private final ItemsManager itemsManager;
    private final IsPvpWorld isPvpWorld;

    public PlayerRespawnListener(ItemsManager itemsManager, IsPvpWorld isPvpWorld) {
        this.itemsManager = itemsManager;
        this.isPvpWorld = isPvpWorld;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {

        if (!isPvpWorld.isPvpWorld(event.getPlayer().getWorld())) {
            return;
        }

        Player player = event.getPlayer();

        itemsManager.giveSword(player);

    }

}
