package me.tamikkkkr.lobbyPvP.listeners;

import me.tamikkkkr.lobbyPvP.Plugin;
import me.tamikkkkr.lobbyPvP.utils.IsPvpWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDamageListener implements Listener {

    private final ItemSlotChangeListener itemSlotChangeListener;
    private final IsPvpWorld isPvpWorld;

    public PlayerDamageListener(ItemSlotChangeListener itemSlotChangeListener, IsPvpWorld isPvpWorld) {
        this.itemSlotChangeListener = itemSlotChangeListener;
        this.isPvpWorld = isPvpWorld;
    }
    

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDamage(EntityDamageByEntityEvent event) {

        if (!isPvpWorld.isPvpWorld(event.getDamager().getWorld())) {
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
