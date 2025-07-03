package me.tamikkkkr.lobbyPvP;

import me.tamikkkkr.lobbyPvP.commands.PluginReloadCommand;
import me.tamikkkkr.lobbyPvP.listeners.*;
import me.tamikkkkr.lobbyPvP.utils.ChatUtil;
import me.tamikkkkr.lobbyPvP.utils.LoadItems;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

    private ChatUtil chatUtil;
    private LoadItems loadItems;
    private ItemsManager itemsManager;
    private ItemSlotChangeListener itemSlotChangeListener;

    @Override
    public void onEnable() {

        // Config loading
        saveDefaultConfig();

        // Initializing dependencies
        initializeDependencies();

        getLogger().info("LobbyPvP plugin has been enabled!");

        // Command registration
        getCommand("lobbypvp").setExecutor(new PluginReloadCommand(this, loadItems, itemsManager));

        // Registration of listeners
        getServer().getPluginManager().registerEvents(new PlayerDamageListener(itemSlotChangeListener, this), this);
        getServer().getPluginManager().registerEvents(new PlayerDropItemListener(this), this);
        getServer().getPluginManager().registerEvents(itemSlotChangeListener, this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(itemsManager, this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(itemSlotChangeListener, chatUtil, this), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawnListener(itemsManager), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveListener(itemSlotChangeListener, itemsManager, this), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this, itemSlotChangeListener), this);
        getServer().getPluginManager().registerEvents(new PlayerHungerListener(this), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("LobbyPvP plugin has been disabled!");
    }


    private void initializeDependencies() {

        this.loadItems = new LoadItems(this);
        loadItems.loadItems();
        this.chatUtil = new ChatUtil(this);
        this.itemsManager = new ItemsManager(loadItems, this);
        this.itemSlotChangeListener = new ItemSlotChangeListener(loadItems, itemsManager, this);

    }

}
