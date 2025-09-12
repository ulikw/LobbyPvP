package me.tamikkkkr.lobbyPvP.listeners;

import me.tamikkkkr.lobbyPvP.ItemsManager;
import me.tamikkkkr.lobbyPvP.Plugin;
import me.tamikkkkr.lobbyPvP.utils.IsPvpWorld;
import me.tamikkkkr.lobbyPvP.utils.LoadItems;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;


public class ItemSlotChangeListener implements Listener {

    private final LoadItems loadItems;
    private final ItemsManager itemsManager;
    private final Plugin plugin;
    private final IsPvpWorld isPvpWorld;

    private final HashSet<UUID> playersInPvP = new HashSet<>();
    private final HashMap<UUID, BukkitRunnable> activeEnableTimers = new HashMap<>();
    private final HashMap<UUID, BukkitRunnable> activeDisableTimers = new HashMap<>();

    public ItemSlotChangeListener(LoadItems loadItems, ItemsManager itemsManager, Plugin plugin, IsPvpWorld isPvpWorld) {
        this.loadItems = loadItems;
        this.itemsManager = itemsManager;
        this.plugin = plugin;
        this.isPvpWorld = isPvpWorld;
    }


    public boolean isInPvp(Player player) {

        return playersInPvP.contains(player.getUniqueId());

    }

    public void removeFromPvp(Player player) {

        playersInPvP.remove(player.getUniqueId());

    }

    @EventHandler
    public void onSlotChange(PlayerItemHeldEvent event) {

        if (!isPvpWorld.isPvpWorld(event.getPlayer().getWorld())) {
            return;
        }

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        ItemStack oldItem = event.getPlayer().getInventory().getItem(event.getPreviousSlot());
        ItemStack newItem = event.getPlayer().getInventory().getItem(event.getNewSlot());
        ItemStack item = null;
        Material itemMaterial = null;

        if (newItem != null) {
            item = event.getPlayer().getInventory().getItem(event.getNewSlot());

            if (item != null)
                itemMaterial = item.getType();

        }

        if (activeDisableTimers.containsKey(uuid)) {

            if (newItem != null) {

                if (newItem.getType() == loadItems.getWeaponMaterial() && newItem.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("pvpsword"))) {

                    cancelActiveTimer(uuid, activeDisableTimers);

                }

            }

        }

        else if (activeEnableTimers.containsKey(uuid)) {

            if (newItem == null) {

                cancelActiveTimer(uuid, activeEnableTimers);

            }

            else if (newItem.getType() != loadItems.getWeaponMaterial()) {

                cancelActiveTimer(uuid, activeEnableTimers);

            }

        }





        if (itemMaterial == loadItems.getWeaponMaterial() && item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("pvpsword"))) {

            if (!(activeEnableTimers.containsKey(uuid)) && !(playersInPvP.contains(uuid))) {

                // Запускаем новый таймер
                 BukkitRunnable enableTask = new BukkitRunnable() {

                    int enableTimer = plugin.getConfig().getInt("enable-cooldown", 4);

                    @Override
                    public void run() {

                        if (isStillHoldingSword(player)) {

                            if (enableTimer == 0) {

                                if (!plugin.getConfig().getString("lang.pvp-enabled").isEmpty()) {
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lang.pvp-enabled", "&aPvP Enabled")));
                                }

                                if (plugin.getConfig().getBoolean("pvp-activation-deactivation-countdown-sound.enable")) {
                                    player.playSound(player, Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1.0F, 1.0F);
                                }

                                playersInPvP.add(player.getUniqueId());
                                itemsManager.giveSet(player);
                                activeEnableTimers.remove(player.getUniqueId());
                                cancel();

                            } else {

                                if (!plugin.getConfig().getString("lang.pvp-enabling").isEmpty()) {
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(
                                            "lang.pvp-enabling", "&aPvP enabling in %time% second(s).").replace("%time%", Integer.toString(enableTimer))));
                                }

                                if (plugin.getConfig().getBoolean("pvp-activation-deactivation-countdown-sound.enable")) {
                                    player.playSound(player, Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1.0F, 2.0F);
                                }

                                enableTimer--;

                            }

                        } else {

                            activeEnableTimers.remove(player.getUniqueId());
                            cancel();

                        }

                    }


                };

                 activeEnableTimers.put(uuid, enableTask);
                 enableTask.runTaskTimer(plugin, 0L, 20L);

            }

        }

        else  {

            if (!activeDisableTimers.containsKey(uuid) && playersInPvP.contains(uuid) && !(oldItem == null && newItem == null)) {

                BukkitRunnable disableTask = new BukkitRunnable() {

                    int disableTimer = plugin.getConfig().getInt("disable-cooldown", 4);

                    @Override
                    public void run() {

                        if (!(isStillHoldingSword(player))) {

                            if (disableTimer == 0) {

                                if (!plugin.getConfig().getString("lang.pvp-disabled").isEmpty()) {
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lang.pvp-disabled", "&cPvP Disabled")));
                                }

                                if (plugin.getConfig().getBoolean("pvp-activation-deactivation-countdown-sound.enable")) {
                                    player.playSound(player, Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1.0F, 1.0F);
                                }

                                removeFromPvp(player);
                                player.setHealth(20);
                                itemsManager.takeSet(player);
                                activeDisableTimers.remove(player.getUniqueId());
                                cancel();

                            } else {
                                if (!plugin.getConfig().getString("lang.pvp-disabling").isEmpty()) {
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(
                                            "lang.pvp-disabling", "&cPvP disabling in %time% second(s).").replace("%time%", Integer.toString(disableTimer))));
                                }

                                if (plugin.getConfig().getBoolean("pvp-activation-deactivation-countdown-sound.enable")) {
                                    player.playSound(player, Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1.0F, 2.0F);
                                }
                                disableTimer--;

                            }

                        }

                        else {

                            activeDisableTimers.remove(uuid);
                            cancel();

                        }


                    }

                };

                activeDisableTimers.put(uuid, disableTask);
                disableTask.runTaskTimer(plugin, 0L, 20L);

            }

        }

    }

    private void cancelActiveTimer(UUID uuid, HashMap<UUID, BukkitRunnable> timers) {

        if (timers.containsKey(uuid)) {
            timers.get(uuid).cancel();
            timers.remove(uuid);
        }

    }

    private boolean isStillHoldingSword(Player player) {

        ItemStack item = player.getInventory().getItemInMainHand();
        Material material = player.getInventory().getItemInMainHand().getType();

        return material == loadItems.getWeaponMaterial() && item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("pvpsword"));

    }


}
