package me.tamikkkkr.lobbyPvP.listeners;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

public class PlayerInteractListener implements Listener {

    private final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private final Plugin plugin;
    private final ItemSlotChangeListener itemSlotChangeListener;

    public PlayerInteractListener(Plugin plugin, ItemSlotChangeListener itemSlotChangeListener) {
        this.plugin = plugin;
        this.itemSlotChangeListener = itemSlotChangeListener;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {

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
                if (!plugin.getConfig().getString("lang.ability-cooldown").isEmpty()) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("lang.ability-cooldown", "&cThe ability is recharging! Left: &e%time% &cseconds").replace("%time%", Long.toString(secondsLeft)))));
                }

                return;
            }
        }

        givePotionEffect(player);
        int cooldownSeconds = plugin.getConfig().getInt("right-click-ability.cooldown");
        cooldowns.put(player.getUniqueId(), System.currentTimeMillis() + cooldownSeconds * 1000L);

    }

    private void givePotionEffect(Player player) {

        int speedDuration = plugin.getConfig().getInt("right-click-ability.speed.duration");
        int speedAmplifier = plugin.getConfig().getInt("right-click-ability.speed.amplifier");

        int strengthDuration = plugin.getConfig().getInt("right-click-ability.strength.duration");
        int strengthAmplifier = plugin.getConfig().getInt("right-click-ability.strength.amplifier");


        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, speedDuration * 20, speedAmplifier, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, strengthDuration * 20, strengthAmplifier, false, false));

        if (!plugin.getConfig().getString("lang.ability-activate").isEmpty()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(
                    "lang.ability-activate", "&aYou've gained extra &6damage &aand &6speed&a!")));
        }


    }

}
