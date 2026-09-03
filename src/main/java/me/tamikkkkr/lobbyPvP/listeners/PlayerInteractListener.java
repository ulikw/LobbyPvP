package me.tamikkkkr.lobbyPvP.listeners;

import me.tamikkkkr.lobbyPvP.utils.IsPvpWorld;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class PlayerInteractListener implements Listener {

    private final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private final HashMap<UUID, BukkitRunnable> cooldownDisplayTasks = new HashMap<>();
    private final HashMap<UUID, Float> savedExp = new HashMap<>();
    private final HashMap<UUID, Integer> savedLevel = new HashMap<>();
    private final Plugin plugin;
    private final ItemSlotChangeListener itemSlotChangeListener;
    private final IsPvpWorld isPvpWorld;

    public PlayerInteractListener(Plugin plugin, ItemSlotChangeListener itemSlotChangeListener, IsPvpWorld isPvpWorld) {
        this.plugin = plugin;
        this.itemSlotChangeListener = itemSlotChangeListener;
        this.isPvpWorld = isPvpWorld;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {

        if (!isPvpWorld.isPvpWorld(event.getPlayer().getWorld())) {
            return;
        }

        ItemStack item = event.getItem();
        Player player = event.getPlayer();

        if (!(plugin.getConfig().getBoolean("right-click-ability.enable"))) {
            return;
        }

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (item == null) {
            return;
        }

        if (!(item.getType() == Material.getMaterial(plugin.getConfig().getString("items.weapon.material")))) {
            return;
        }

        if (!itemSlotChangeListener.isInPvp(player)) {
            return;
        }

        if (cooldowns.containsKey(player.getUniqueId())) {
            long secondsLeft = (cooldowns.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000;

            if (secondsLeft > 0) {
                return;
            }
        }

        givePotionEffect(player);
        int cooldownSeconds = plugin.getConfig().getInt("right-click-ability.cooldown");
        cooldowns.put(player.getUniqueId(), System.currentTimeMillis() + cooldownSeconds * 1000L);
        startCooldownDisplay(player, cooldownSeconds);

    }

    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event) {

        Player player = event.getPlayer();

        if (!cooldownDisplayTasks.containsKey(player.getUniqueId())) {
            return;
        }

        ItemStack newItem = player.getInventory().getItem(event.getNewSlot());

        if (isPvpSword(newItem)) {
            return;
        }

        if (savedExp.containsKey(player.getUniqueId())) {
            restoreExp(player, false);
        } else {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""));
        }

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        if (!cooldownDisplayTasks.containsKey(player.getUniqueId())) {
            return;
        }

        clearCooldownDisplay(player);

        if (savedExp.containsKey(player.getUniqueId())) {
            restoreExp(player, true);
        }

    }

    private boolean isPvpSword(ItemStack item) {

        if (item == null || item.getItemMeta() == null) {
            return false;
        }

        Material weaponMaterial = Material.getMaterial(plugin.getConfig().getString("items.weapon.material", "DIAMOND_SWORD"));

        return item.getType() == weaponMaterial && item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("pvpsword"));

    }

    private void startCooldownDisplay(Player player, int totalCooldownSeconds) {

        if (!plugin.getConfig().getBoolean("right-click-ability.cooldown-display.enable", true)) {
            return;
        }

        clearCooldownDisplay(player);

        boolean useXpBar = plugin.getConfig().getString("right-click-ability.cooldown-display.mode", "actionbar").equalsIgnoreCase("xp-bar");

        if (useXpBar) {
            savedExp.put(player.getUniqueId(), player.getExp());
            savedLevel.put(player.getUniqueId(), player.getLevel());
        }

        BukkitRunnable task = new BukkitRunnable() {

            @Override
            public void run() {

                Long cooldownEnd = cooldowns.get(player.getUniqueId());

                if (cooldownEnd == null) {
                    cooldownDisplayTasks.remove(player.getUniqueId());
                    if (useXpBar) {
                        restoreExp(player, true);
                    }
                    cancel();
                    return;
                }

                long millisLeft = cooldownEnd - System.currentTimeMillis();

                if (millisLeft <= 0) {
                    cooldowns.remove(player.getUniqueId());
                    cooldownDisplayTasks.remove(player.getUniqueId());
                    if (useXpBar) {
                        restoreExp(player, true);
                    }
                    playReadySound(player);
                    cancel();
                    return;
                }

                if (!isPvpSword(player.getInventory().getItemInMainHand())) {
                    return;
                }

                long secondsLeft = (long) Math.ceil(millisLeft / 1000.0);

                if (useXpBar) {
                    long totalMillis = totalCooldownSeconds * 1000L;
                    float progress = (float) Math.max(0.0, Math.min(1.0, (totalMillis - millisLeft) / (double) totalMillis));
                    player.setExp(progress);
                    player.setLevel((int) secondsLeft);
                } else {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(buildCooldownText(secondsLeft)));
                }

            }

        };

        cooldownDisplayTasks.put(player.getUniqueId(), task);
        task.runTaskTimer(plugin, 0L, 5L);

    }

    private String buildCooldownText(long secondsLeft) {

        return ChatColor.translateAlternateColorCodes('&', "&cAbility Cooldown: &e" + secondsLeft + "s");

    }

    private void restoreExp(Player player, boolean forget) {

        UUID uuid = player.getUniqueId();

        Float exp = forget ? savedExp.remove(uuid) : savedExp.get(uuid);
        Integer level = forget ? savedLevel.remove(uuid) : savedLevel.get(uuid);

        if (exp != null) {
            player.setExp(exp);
        }

        if (level != null) {
            player.setLevel(level);
        }

    }

    private void clearCooldownDisplay(Player player) {

        BukkitRunnable existingTask = cooldownDisplayTasks.remove(player.getUniqueId());

        if (existingTask != null) {
            existingTask.cancel();
        }

    }

    private void playReadySound(Player player) {

        if (!plugin.getConfig().getBoolean("right-click-ability.cooldown-display.enable", true)) {
            return;
        }

        Sound sound;
        try {
            sound = Sound.valueOf(plugin.getConfig().getString("right-click-ability.cooldown-display.ready-sound", "ENTITY_EXPERIENCE_ORB_PICKUP"));
        } catch (IllegalArgumentException exception) {
            sound = Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
        }

        player.playSound(player, sound, 1.0F, 1.0F);

    }

    private void givePotionEffect(Player player) {

        int speedDuration = plugin.getConfig().getInt("right-click-ability.speed.duration");
        int speedAmplifier = plugin.getConfig().getInt("right-click-ability.speed.amplifier");

        int strengthDuration = plugin.getConfig().getInt("right-click-ability.strength.duration");
        int strengthAmplifier = plugin.getConfig().getInt("right-click-ability.strength.amplifier");


        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, speedDuration * 20, speedAmplifier, false, false, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, strengthDuration * 20, strengthAmplifier, false, false, true));

        if (!plugin.getConfig().getString("lang.ability-activate").isEmpty()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(
                    "lang.ability-activate", "&aYou've gained extra &6damage &aand &6speed&a!")));
        }


    }

}
