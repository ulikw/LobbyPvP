package me.tamikkkkr.lobbyPvP.listeners;

import me.tamikkkkr.lobbyPvP.ItemsManager;
import me.tamikkkkr.lobbyPvP.utils.IsPvpWorld;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleportJoinListener implements Listener {

    private final IsPvpWorld isPvpWorld;
    private final ItemsManager itemsManager;
    private final ItemSlotChangeListener itemSlotChangeListener;

    public PlayerTeleportJoinListener(IsPvpWorld isPvpWorld, ItemsManager itemsManager, ItemSlotChangeListener itemSlotChangeListener) {
        this.isPvpWorld = isPvpWorld;
        this.itemsManager = itemsManager;
        this.itemSlotChangeListener = itemSlotChangeListener;
    }

    @EventHandler
    public void onPlayerTeleportJoin(PlayerTeleportEvent event) {

        Player player = event.getPlayer();

        // Мир, В КОТОРЫЙ телепортируется игрок (целевой мир)
        World toWorld = event.getTo().getWorld();

        if (isPvpWorld.isPvpWorld(toWorld)) {
            itemsManager.giveSword(player);
        }
        else {
            itemSlotChangeListener.removeFromPvp(player);
            itemsManager.takeSet(player);
            itemsManager.takeSword(player);
        }

    }

}
