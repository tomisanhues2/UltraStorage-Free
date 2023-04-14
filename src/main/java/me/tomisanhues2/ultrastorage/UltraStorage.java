package me.tomisanhues2.ultrastorage;

import co.aikar.commands.PaperCommandManager;
import com.jeff_media.jefflib.JeffLib;
import me.tomisanhues2.ultrastorage.cache.ChestDataPersistenceHandler;
import me.tomisanhues2.ultrastorage.cache.ChestManager;
import me.tomisanhues2.ultrastorage.commands.AdminCommands;
import me.tomisanhues2.ultrastorage.config.Config;
import me.tomisanhues2.ultrastorage.config.GuiConfig;
import me.tomisanhues2.ultrastorage.data.UltraChest;
import me.tomisanhues2.ultrastorage.data.UltraChestUpgrades;
import me.tomisanhues2.ultrastorage.gui.GUIListener;
import me.tomisanhues2.ultrastorage.gui.GUIManager;
import me.tomisanhues2.ultrastorage.listeners.ChestLoadListener;
import me.tomisanhues2.ultrastorage.listeners.PlayerListener;
import me.tomisanhues2.ultrastorage.listeners.UltraChestListener;
import me.tomisanhues2.ultrastorage.tasks.TaskManager;
import me.tomisanhues2.ultrastorage.utils.*;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class UltraStorage extends JavaPlugin {
    private static UltraStorage plugin;
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;
    public Config config;
    public GUIManager guiManager;
    public GuiConfig guiConfig;
    ChestDataPersistenceHandler chestDataPersistenceHandler;
    private ChestManager chestManager;
    private UpgradeUtils upgradeUtils;
    private MessageUtils messageUtils;

    public static UltraStorage getInstance() {
        return plugin;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static Permission getPermissions() {
        return perms;
    }

    @Override
    public void onEnable() {
        new UpdateChecker(this).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info("There is not a new update available.");
            } else {
                getLogger().severe("There is a new update available. Old versions are NOT supported!");
                getLogger().severe(String.format("Current version: %s - New version: %s", this.getDescription().getVersion(), version));
            }
        });
        if (!getDataFolder().exists()) {
            saveDefaultConfig();
            getLogger().warning("Config file not found, creating a new one, Please edit the file and restart the server");
        }

        getConfig();
        JeffLib.init(this);
        chestDataPersistenceHandler = new ChestDataPersistenceHandler(this);
        chestManager = new ChestManager(chestDataPersistenceHandler);
        setupVault();
        guiManager = new GUIManager();
        GUIListener guiListener = new GUIListener(guiManager);
        getServer().getPluginManager().registerEvents(new ChestLoadListener(this), this);
        getServer().getPluginManager().registerEvents(new UltraChestListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(guiListener, this);
        registerCommands();
        ConfigurationSerialization.registerClass(UltraChest.class);
        ConfigurationSerialization.registerClass(UltraChestUpgrades.class);
        ConfigurationSerialization.registerClass(UpgradeType.class);
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        getChestManager().saveAll();
    }

    private void registerCommands() {
        //Register a Bukkit command without acf
        PaperCommandManager manager = new PaperCommandManager(this);
        manager.getCommandReplacements();
        manager.registerCommand(new AdminCommands());
    }

    private void setupVault() {
        if (!setupEconomy()) {
            getLogger().severe("Vault not found, disabling plugin");
            getLogger().severe("Make sure you have Vault, an economy plugin and a permissions plugin installed.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
        setupChat();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp =
                getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public void reload() throws ReloadException {
        try {
            config = null;
            upgradeUtils = null;
            messageUtils = null;
            saveDefaultConfig();
            reloadConfig();
            config = new Config(this);
            upgradeUtils = new UpgradeUtils(this);
            messageUtils = new MessageUtils(this);
            Bukkit.getScheduler().cancelTasks(this);
            final TaskManager taskManager = TaskManager.getInstance();
            taskManager.attemptStartHologram();
            taskManager.attemptAutoSave();
        } catch (Exception e) {
            throw new ReloadException(Cases.MSG_RELOAD_ERROR);
        }
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp =
                getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp =
                getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    @Override
    public void onLoad() {
        plugin = this;
    }

    public ChestManager getChestManager() {
        return chestManager;
    }

    public MessageUtils getMessageUtils() {
        return messageUtils;
    }
}
