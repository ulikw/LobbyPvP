package me.tamikkkkr.lobbyPvP;

import me.tamikkkkr.lobbyPvP.utils.IsPvpWorld;
import me.tamikkkkr.lobbyPvP.utils.LoadItems;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ItemsManager {

    private final LoadItems loadItems;
    private final Plugin plugin;

    public ItemsManager(LoadItems loadItems, Plugin plugin) {
        this.loadItems = loadItems;
        this.plugin = plugin;
    }

    public void giveSword(Player player) {

        ItemStack sword = loadItems.getWeapon();
        ItemMeta swordMeta = sword.getItemMeta();

        if (swordMeta == null) {
            return;
        }

        swordMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("items.weapon.name", "&aPvP Sword &e(Hold to PvP)")));
        swordMeta.setUnbreakable(true);
        if (plugin.getConfig().getInt("items.weapon.sharpness") > 0) {
            swordMeta.addEnchant(Enchantment.SHARPNESS, plugin.getConfig().getInt("items.weapon.sharpness"), true);
        }
        swordMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        swordMeta.getPersistentDataContainer().set(NamespacedKey.fromString("pvpsword"), PersistentDataType.STRING, "pvpsword");

        sword.setItemMeta(swordMeta);

        boolean needDelay = plugin.getConfig().getBoolean("delay-before-giving-sword", false);

        if (needDelay) {

            new BukkitRunnable() {

                int timer = 1;

                @Override
                public void run() {

                    if (timer == 0) {
                        player.getInventory().setItem(plugin.getConfig().getInt("items.weapon.slot", 4), sword);
                        cancel();
                    }
                    else {
                        timer--;
                    }

                }

            }.runTaskTimer(plugin, 0L, 20L);

        }

        else {
            player.getInventory().setItem(plugin.getConfig().getInt("items.weapon.slot", 4), sword);
        }

    }


    public void giveSet(Player player) {

        ItemStack[] items = createArmor();

        player.getInventory().setHelmet(items[0]);
        player.getInventory().setChestplate(items[1]);
        player.getInventory().setLeggings(items[2]);
        player.getInventory().setBoots(items[3]);

    }

    public void takeSet(Player player) {

        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);

    }

    public void takeSword(Player player) {

        for (ItemStack item: player.getInventory()) {
            if (item != null) {
                if (item.getItemMeta() != null) {
                    if (item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("pvpsword"))) {
                        player.getInventory().remove(item);
                    }
                }
            }
        }
    }

    public ItemStack[] createArmor() {

        ItemStack helmet = loadItems.getHelmet();
        ItemMeta helmetMeta = helmet.getItemMeta();

        if (helmetMeta != null) {

            helmetMeta.setUnbreakable(true);
            if (plugin.getConfig().getInt("items.helmet.protection") > 0) {
                helmetMeta.addEnchant(Enchantment.PROTECTION, plugin.getConfig().getInt("items.helmet.protection"), true);
            }
            helmetMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            helmetMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("items.helmet.name", "&aPvP Helmet")));
            helmetMeta.getPersistentDataContainer().set(NamespacedKey.fromString("pvphelmet"), PersistentDataType.STRING, "pvphelmet");
            helmet.setItemMeta(helmetMeta);

        }

        ItemStack chestplate = loadItems.getChestPlate();
        ItemMeta chestplateMeta = chestplate.getItemMeta();

        if (chestplateMeta != null) {

            chestplateMeta.setUnbreakable(true);
            if (plugin.getConfig().getInt("items.chestplate.protection") > 0) {
                chestplateMeta.addEnchant(Enchantment.PROTECTION, plugin.getConfig().getInt("items.chestplate.protection"), true);
            }
            chestplateMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            chestplateMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("items.chestplate.name", "&aPvP Chestplate")));
            chestplateMeta.getPersistentDataContainer().set(NamespacedKey.fromString("pvpchestplate"), PersistentDataType.STRING, "pvpchestplate");
            chestplate.setItemMeta(chestplateMeta);
        }

        ItemStack leggings = loadItems.getLeggings();
        ItemMeta leggingsMeta = leggings.getItemMeta();

        if (leggingsMeta != null) {

            leggingsMeta.setUnbreakable(true);
            if (plugin.getConfig().getInt("items.leggings.protection") > 0) {
                leggingsMeta.addEnchant(Enchantment.PROTECTION, plugin.getConfig().getInt("items.leggings.protection"), true);
            }
            leggingsMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            leggingsMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("items.leggings.name", "&aPvP Leggings")));
            leggingsMeta.getPersistentDataContainer().set(NamespacedKey.fromString("pvpleggings"), PersistentDataType.STRING, "pvpleggings");
            leggings.setItemMeta(leggingsMeta);
        }

        ItemStack boots = loadItems.getBoots();
        ItemMeta bootsMeta = boots.getItemMeta();

        if (bootsMeta != null) {

            bootsMeta.setUnbreakable(true);
            if (plugin.getConfig().getInt("items.boots.protection") > 0) {
                bootsMeta.addEnchant(Enchantment.PROTECTION, plugin.getConfig().getInt("items.boots.protection"), true);
            }
            bootsMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            bootsMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("items.boots.name", "&aPvP Boots")));
            bootsMeta.getPersistentDataContainer().set(NamespacedKey.fromString("pvpboots"), PersistentDataType.STRING, "pvpboots");
            boots.setItemMeta(bootsMeta);

        }
        return new ItemStack[] {helmet, chestplate, leggings, boots};

    }

    public boolean isPvpSword(ItemStack sword) {
        if (sword.getItemMeta() != null) {
            if (sword.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("pvpsword"))) {
                return true;
            }
        }
        return false;
    }



}
