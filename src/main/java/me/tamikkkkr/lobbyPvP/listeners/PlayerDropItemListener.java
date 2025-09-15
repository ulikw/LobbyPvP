package me.tamikkkkr.lobbyPvP.listeners;

import me.tamikkkkr.lobbyPvP.utils.IsPvpWorld;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class PlayerDropItemListener implements Listener {

    private final Plugin plugin;
    private final IsPvpWorld isPvpWorld;

    public PlayerDropItemListener(Plugin plugin, IsPvpWorld isPvpWorld) {
        this.plugin = plugin;
        this.isPvpWorld = isPvpWorld;
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {

        if (!isPvpWorld.isPvpWorld(event.getPlayer().getWorld())) {
            return;
        }

        ItemStack item = event.getItemDrop().getItemStack();
        Material material = event.getItemDrop().getItemStack().getType();

        if (material == Material.getMaterial(plugin.getConfig().getString("items.weapon.material")) && item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("pvpsword"))) {

           event.setCancelled(true);


        }

        else if (material == Material.getMaterial(plugin.getConfig().getString("items.helmet.material")) && item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("pvphelmet"))) {

            event.setCancelled(true);

        }

        else if (material == Material.getMaterial(plugin.getConfig().getString("items.chestplate.material")) && item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("pvpchestplate"))) {

            event.setCancelled(true);

        }

        else if (material == Material.getMaterial(plugin.getConfig().getString("items.leggings.material")) && item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("pvpleggings"))) {

            event.setCancelled(true);

        }

        else if (material == Material.getMaterial(plugin.getConfig().getString("items.boots.material")) && item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("pvpboots"))) {

            event.setCancelled(true);

        }




    }

    @EventHandler
    public void onPlayerClick(InventoryClickEvent event) {

        if (!isPvpWorld.isPvpWorld(event.getWhoClicked().getWorld())) {
            return;
        }

        ItemStack item = event.getCurrentItem();

        if (item != null) {

            if (item.getType() == Material.getMaterial(plugin.getConfig().getString("items.weapon.material")) && item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("pvpsword"))) {

                event.setCancelled(true);

            }

            else if (item.getType() == Material.getMaterial(plugin.getConfig().getString("items.helmet.material")) && item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("pvphelmet"))) {

                event.setCancelled(true);

            }

            else if (item.getType() == Material.getMaterial(plugin.getConfig().getString("items.chestplate.material")) && item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("pvpchestplate"))) {

                event.setCancelled(true);

            }

            else if (item.getType() == Material.getMaterial(plugin.getConfig().getString("items.leggings.material")) && item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("pvpleggings"))) {

                event.setCancelled(true);

            }

            else if (item.getType() == Material.getMaterial(plugin.getConfig().getString("items.boots.material")) && item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("pvpboots"))) {

                event.setCancelled(true);

            }


        }
    }

    @EventHandler
    public void onPlayerLeftHand(PlayerSwapHandItemsEvent event) {

        if (plugin.getConfig().getStringList("disabled-worlds").contains(event.getPlayer().getWorld().getName())) {
            return;
        }

        ItemStack item = event.getOffHandItem();

        if (item != null) {

            Material material = item.getType();

            if (material == Material.getMaterial(plugin.getConfig().getString("items.weapon.material")) && item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("pvpsword"))) {

                event.setCancelled(true);

            }
        }

    }

}
