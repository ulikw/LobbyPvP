package me.tamikkkkr.lobbyPvP;

import me.tamikkkkr.lobbyPvP.commands.PluginReloadCommand;
import me.tamikkkkr.lobbyPvP.listeners.*;
import me.tamikkkkr.lobbyPvP.utils.ChatUtil;
import me.tamikkkkr.lobbyPvP.utils.IsPvpWorld;
import me.tamikkkkr.lobbyPvP.utils.LoadItems;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

    private ChatUtil chatUtil;
    private LoadItems loadItems;
    private IsPvpWorld isPvpWorld;
    private ItemsManager itemsManager;
    private ItemSlotChangeListener itemSlotChangeListener;

    @Override
    public void onEnable() {

        // Config loading
        saveDefaultConfig();

        // Initializing dependencies
        initializeDependencies();

        for (Player player: Bukkit.getOnlinePlayers()) {
            if (isPvpWorld.isPvpWorld(player.getWorld())) {
                itemsManager.giveSword(player);
            }
        }

        getLogger().info("LobbyPvP plugin has been enabled!");

        // Command registration
        getCommand("lobbypvp").setExecutor(new PluginReloadCommand(this, loadItems, itemsManager, itemSlotChangeListener));

        // Registration of listeners
        getServer().getPluginManager().registerEvents(new PlayerDamageListener(itemSlotChangeListener, isPvpWorld), this);
        getServer().getPluginManager().registerEvents(new PlayerDropItemListener(this, isPvpWorld), this);
        getServer().getPluginManager().registerEvents(itemSlotChangeListener, this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(itemsManager, isPvpWorld, this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(itemSlotChangeListener, chatUtil, this, isPvpWorld, itemsManager), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawnListener(itemsManager, isPvpWorld, this), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveListener(itemSlotChangeListener, itemsManager, isPvpWorld), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this, itemSlotChangeListener, isPvpWorld), this);
        getServer().getPluginManager().registerEvents(new PlayerHungerListener(isPvpWorld), this);
        getServer().getPluginManager().registerEvents(new PlayerTeleportJoinListener(isPvpWorld, itemsManager, itemSlotChangeListener), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("LobbyPvP plugin has been disabled!");

        for (Player player: Bukkit.getOnlinePlayers()) {
            itemsManager.takeSet(player);
            itemsManager.takeSword(player);
        }

    }


    private void initializeDependencies() {

        this.isPvpWorld = new IsPvpWorld(this);
        this.loadItems = new LoadItems(this);
        loadItems.loadItems();
        this.chatUtil = new ChatUtil(this);
        this.itemsManager = new ItemsManager(loadItems, this);
        this.itemSlotChangeListener = new ItemSlotChangeListener(loadItems, itemsManager, this, isPvpWorld);

    }

}
