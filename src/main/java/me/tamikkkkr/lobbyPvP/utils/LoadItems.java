package me.tamikkkkr.lobbyPvP.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class LoadItems {

    private final Plugin plugin;
    public LoadItems(Plugin plugin) {
        this.plugin = plugin;
    }

    ItemStack weapon;
    ItemStack helmet;
    ItemStack chestPlate;
    ItemStack leggings;
    ItemStack boots;



    public void loadItems() {

        Material weaponMaterial = Material.getMaterial(plugin.getConfig().getString("items.weapon.material", "DIAMOND_SWORD"));
        Material helmetMaterial = Material.getMaterial(plugin.getConfig().getString("items.helmet.material", "DIAMOND_HELMET"));
        Material chestPlateMaterial = Material.getMaterial(plugin.getConfig().getString("items.chestplate.material", "DIAMOND_CHESTPLATE"));
        Material leggingsMaterial = Material.getMaterial(plugin.getConfig().getString("items.leggings.material", "DIAMOND_LEGGINGS"));
        Material bootsMaterial = Material.getMaterial(plugin.getConfig().getString("items.boots.material", "DIAMOND_BOOTS"));

        if (weaponMaterial != null && helmetMaterial != null && chestPlateMaterial != null && leggingsMaterial != null && bootsMaterial != null) {

            helmet = new ItemStack(helmetMaterial);
            chestPlate = new ItemStack(chestPlateMaterial);
            leggings = new ItemStack(leggingsMaterial);
            boots = new ItemStack(bootsMaterial);

            weapon = new ItemStack(weaponMaterial);

        }
    }

    public ItemStack getHelmet() {
        return helmet;
    }

    public ItemStack getChestPlate() {
        return chestPlate;
    }

    public ItemStack getLeggings() {
        return leggings;
    }

    public ItemStack getBoots() {
        return boots;
    }

    public ItemStack getWeapon() {
        return weapon;
    }

    public Material getWeaponMaterial() {

        return weapon.getType();

    }

}
