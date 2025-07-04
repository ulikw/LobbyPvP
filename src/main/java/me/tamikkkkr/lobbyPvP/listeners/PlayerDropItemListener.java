package me.tamikkkkr.lobbyPvP.listeners;

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

    public PlayerDropItemListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {

        if (plugin.getConfig().getStringList("disabled-worlds").contains(event.getPlayer().getWorld().getName())) {
            return;
        }

        ItemStack item = event.getItemDrop().getItemStack();
        Material material = event.getItemDrop().getItemStack().getType();

        if (material == Material.getMaterial(plugin.getConfig().getString("items.weapon.material")) && item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("pvpsword"))) {

           event.setCancelled(true);

        }

        else if (material == Material.getMaterial(plugin.getConfig().getString("items.helmet.material"))) {

            event.setCancelled(true);

        }

        else if (material == Material.getMaterial(plugin.getConfig().getString("items.chestplate.material"))) {

            event.setCancelled(true);

        }

        else if (material == Material.getMaterial(plugin.getConfig().getString("items.leggings.material"))) {

            event.setCancelled(true);

        }

        else if (material == Material.getMaterial(plugin.getConfig().getString("items.boots.material"))) {

            event.setCancelled(true);

        }




    }

    @EventHandler
    public void onPlayerClick(InventoryClickEvent event) {

        if (plugin.getConfig().getStringList("disabled-worlds").contains(event.getWhoClicked().getWorld().getName())) {
            return;
        }

        ItemStack item = event.getCurrentItem();

        if (item != null) {

            if (item.getType() == Material.getMaterial(plugin.getConfig().getString("items.weapon.material"))) {

                event.setCancelled(true);

            }

            else if (item.getType() == Material.getMaterial(plugin.getConfig().getString("items.helmet.material"))) {

                event.setCancelled(true);

            }

            else if (item.getType() == Material.getMaterial(plugin.getConfig().getString("items.chestplate.material"))) {

                event.setCancelled(true);

            }

            else if (item.getType() == Material.getMaterial(plugin.getConfig().getString("items.leggings.material"))) {

                event.setCancelled(true);

            }

            else if (item.getType() == Material.getMaterial(plugin.getConfig().getString("items.boots.material"))) {

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

            if (material == Material.getMaterial(plugin.getConfig().getString("items.weapon.material"))) {

                event.setCancelled(true);

            }
        }

    }

}
