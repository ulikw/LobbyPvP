package me.tamikkkkr.lobbyPvP.listeners;

import me.tamikkkkr.lobbyPvP.Plugin;
import me.tamikkkkr.lobbyPvP.utils.IsPvpWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class PlayerHungerListener implements Listener {

    private final IsPvpWorld isPvpWorld;

    public PlayerHungerListener(IsPvpWorld isPvpWorld) {
        this.isPvpWorld = isPvpWorld;
    }

    @EventHandler
    public void onHungerLoss(FoodLevelChangeEvent event) {

        if (!isPvpWorld.isPvpWorld(event.getEntity().getWorld())) {
            return;
        }

        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

        event.setCancelled(true);
        player.setFoodLevel(20);

    }

}
