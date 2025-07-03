package me.tamikkkkr.lobbyPvP.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatUtil {

    private final JavaPlugin plugin;

    public ChatUtil(JavaPlugin plugin) {
        this.plugin = plugin;
    }


    public void sendMessage(Player player, String msg) {

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));

    }

    public void sendTitle(Player player, String msg, String subMsg, int fadeIn, int stay, int fadeOut) {

        player.sendTitle(
                msg,
                subMsg,
                fadeIn,
                stay,
                fadeOut
        );

    }

    public void sendTitle(Player player, String msg, String subMsg) {

        player.sendTitle(
                msg,
                subMsg,
                10,
                70,
                20
        );

    }

    public void broadcastMessage(String msg) {

        for (Player player: plugin.getServer().getOnlinePlayers()) {
            sendMessage(player, msg);
        }

    }

    public void broadcastTitle(String msg, String subMsg, int fadeIn, int stay, int fadeOut) {

        for (Player player: plugin.getServer().getOnlinePlayers()) {
            sendTitle(player, msg, subMsg, fadeIn, stay, fadeOut);
        }

    }

    public void broadcastTitle(String msg, String subMsg) {

        for (Player player: plugin.getServer().getOnlinePlayers()) {
            sendTitle(player, msg, subMsg);
        }

    }

}
