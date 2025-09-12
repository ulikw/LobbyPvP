package me.tamikkkkr.lobbyPvP.utils;

import me.tamikkkkr.lobbyPvP.Plugin;
import org.bukkit.World;

public class IsPvpWorld {

    private final Plugin plugin;
    private final boolean whiteListMode;

    public IsPvpWorld(Plugin plugin) {
        this.plugin = plugin;
        this.whiteListMode = plugin.getConfig().getBoolean("worlds-white-list-mode.enable");
    }

    public boolean isPvpWorld(World world) {

        if (whiteListMode) {

            if (plugin.getConfig().getStringList("worlds-white-list-mode.white-list-worlds").contains(world.getName())) {
                return true;
            }
            else return false;
        }

        else {

            if ((plugin.getConfig().getStringList("disabled-worlds").contains(world.getName()))) {
                return false;
            }

            else return true;

        }

    }

}
