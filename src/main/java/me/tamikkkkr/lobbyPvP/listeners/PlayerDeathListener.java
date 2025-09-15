package me.tamikkkkr.lobbyPvP.listeners;

import me.tamikkkkr.lobbyPvP.ItemsManager;
import me.tamikkkkr.lobbyPvP.Plugin;
import me.tamikkkkr.lobbyPvP.utils.ChatUtil;
import me.tamikkkkr.lobbyPvP.utils.IsPvpWorld;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerDeathListener implements Listener {

    private final ItemSlotChangeListener itemSlotChangeListener;
    private final ChatUtil chatUtil;
    private final Plugin plugin;
    private final IsPvpWorld isPvpWorld;
    private final ItemsManager itemsManager;

    public PlayerDeathListener(ItemSlotChangeListener itemSlotChangeListener, ChatUtil chatUtil, Plugin plugin, IsPvpWorld isPvpWorld, ItemsManager itemsManager) {
        this.itemSlotChangeListener = itemSlotChangeListener;
        this.chatUtil = chatUtil;
        this.plugin = plugin;
        this.isPvpWorld = isPvpWorld;
        this.itemsManager = itemsManager;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

            if (!isPvpWorld.isPvpWorld(event.getEntity().getWorld())) {
                return;
            }

            Player victim = event.getEntity().getPlayer();
            Player killer = event.getEntity().getKiller();

            event.getDrops().clear();

            if (victim != null && killer != null) {

                if (itemSlotChangeListener.isInPvp(victim) && itemSlotChangeListener.isInPvp(killer)) {

                    if (plugin.getConfig().getBoolean("lightning-effect-on-kill.enable")) {
                        if (victim.getLocation().getWorld() != null) {
                            victim.getLocation().getWorld().strikeLightningEffect(victim.getLocation());
                        }
                    }

                    if (plugin.getConfig().getBoolean("regeneration-on-kill.enable")) {
                        givePotionEffect(killer);
                    }

                    if (!plugin.getConfig().getString("lang.kill-message").isEmpty()) {
                        chatUtil.broadcastMessage(plugin.getConfig().getString("lang.kill-message", "&6>>>&a " + victim.getDisplayName()  + " &fwas killed by&c " + killer.getDisplayName()).replace("%victim%", victim.getDisplayName()).replace("%killer%", killer.getDisplayName()));
                    }
                    event.setDeathMessage(null);

                    itemsManager.takeSet(victim);
                    event.getDrops().clear();
                    itemSlotChangeListener.removeFromPvp(victim);

                }

            }

            if (itemSlotChangeListener.isInPvp(victim)) {

                itemsManager.takeSet(victim);
                event.getDrops().clear();
                itemSlotChangeListener.removeFromPvp(victim);

                // можно добавить сообщение о смерти при загадночных обстоятельствах

            }

            if (victim.getInventory().getChestplate() != null) {

                if (victim.getInventory().getChestplate().getItemMeta() != null) {

                    if (victim.getInventory().getChestplate().getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("pvpchestplate"))) {
                        itemsManager.takeSet(victim);
                    }

                }

            }


        }

        public void givePotionEffect(Player player) {

            int duration = plugin.getConfig().getInt("regeneration-on-kill.duration");
            int amplifier = plugin.getConfig().getInt("regeneration-on-kill.amplifier");

            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, duration * 20, amplifier, false, true));

        }


}
