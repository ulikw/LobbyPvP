package me.tamikkkkr.lobbyPvP.listeners;

import me.tamikkkkr.lobbyPvP.ItemsManager;
import me.tamikkkkr.lobbyPvP.Plugin;
import me.tamikkkkr.lobbyPvP.utils.IsPvpWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener {

    private final ItemSlotChangeListener itemSlotChangeListener;
    private final ItemsManager itemsManager;
    private final IsPvpWorld isPvpWorld;

    public PlayerLeaveListener(ItemSlotChangeListener itemSlotChangeListener, ItemsManager itemsManager, IsPvpWorld isPvpWorld) {
        this.itemSlotChangeListener = itemSlotChangeListener;
        this.itemsManager = itemsManager;
        this.isPvpWorld = isPvpWorld;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {

        if (!isPvpWorld.isPvpWorld(event.getPlayer().getWorld())) {
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
