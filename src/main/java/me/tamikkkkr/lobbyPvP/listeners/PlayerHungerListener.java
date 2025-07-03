package me.tamikkkkr.lobbyPvP.listeners;

import me.tamikkkkr.lobbyPvP.Plugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class PlayerHungerListener implements Listener {

    private final Plugin plugin;

    public PlayerHungerListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onHungerLoss(FoodLevelChangeEvent event) {

        if (plugin.getConfig().getStringList("disabled-worlds").contains(event.getEntity().getWorld().getName())) {
            return;
        }

        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

        event.setCancelled(true);
        player.setFoodLevel(20);

    }

}
