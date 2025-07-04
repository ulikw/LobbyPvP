package me.tamikkkkr.lobbyPvP.listeners;

import me.tamikkkkr.lobbyPvP.Plugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDamageListener implements Listener {

    private final ItemSlotChangeListener itemSlotChangeListener;
    private final Plugin plugin;

    public PlayerDamageListener(ItemSlotChangeListener itemSlotChangeListener, Plugin plugin) {
        this.itemSlotChangeListener = itemSlotChangeListener;
        this.plugin = plugin;
    }
    

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDamage(EntityDamageByEntityEvent event) {

        if (plugin.getConfig().getStringList("disabled-worlds").contains(event.getDamager().getWorld().getName())) {
            return;
        }

        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {

            Player attacker = (Player) event.getDamager();
            Player victim = (Player) event.getEntity();

            if (itemSlotChangeListener.isInPvp(attacker) && itemSlotChangeListener.isInPvp(victim)) {

                event.setCancelled(false);

            } else {

                event.setCancelled(true);

            }

        }
    }


}
