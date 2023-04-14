package me.tomisanhues2.ultrastorage.utils;

import me.tomisanhues2.ultrastorage.UltraStorage;
import me.tomisanhues2.ultrastorage.config.ConfigPath;
import me.tomisanhues2.ultrastorage.data.UltraUpgrade;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class UpgradeUtils {
    private final YamlConfiguration upgradeConfig;
    private final Map<UpgradeType, Map<Integer, UltraUpgrade>> upgrades = new HashMap<>();

    public UpgradeUtils(UltraStorage plugin) {
        try (InputStream stream = plugin.getResource("upgrades.yml"); InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            upgradeConfig = YamlConfiguration.loadConfiguration(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        loadUpgrades();
    }

    private void loadUpgrades() {
        loadSlotUpgrades();
        loadStorageUpgrades();
        loadSpeedUpgrades();
        loadMultiplierUpgrades();
        loadMembersUpgrades();
        loadSellQuantityUpgrades();
    }

    private void loadSlotUpgrades() {
        Map<Integer, UltraUpgrade> upgrades = new HashMap<>();
        String key =
                upgradeConfig.getConfigurationSection(ConfigPath.GUI_UPGRADES_SLOT_TIER_CONFIG.getPath()).getKeys(false).toArray()[0].toString();
        double price =
                getUpgradePrice(ConfigPath.GUI_UPGRADES_SLOT_TIER_CONFIG.getPath(), key);
        double value =
                getUpgradeValue(ConfigPath.GUI_UPGRADES_SLOT_TIER_CONFIG.getPath(), key);
        String permission =
                getUpgradePermission(ConfigPath.GUI_UPGRADES_SLOT_TIER_CONFIG.getPath(), key);
        upgrades.put(Integer.parseInt(key), new UltraUpgrade(price, value, permission));
        this.upgrades.put(UpgradeType.UPGRADE_SLOT, upgrades);
    }

    private void loadStorageUpgrades() {
        Map<Integer, UltraUpgrade> upgrades = new HashMap<>();
        String key =
                upgradeConfig.getConfigurationSection(ConfigPath.GUI_UPGRADES_STORAGE_TIER_CONFIG.getPath()).getKeys(false).toArray()[0].toString();
        double price =
                getUpgradePrice(ConfigPath.GUI_UPGRADES_STORAGE_TIER_CONFIG.getPath(), key);
        double value =
                getUpgradeValue(ConfigPath.GUI_UPGRADES_STORAGE_TIER_CONFIG.getPath(), key);
        String permission =
                getUpgradePermission(ConfigPath.GUI_UPGRADES_STORAGE_TIER_CONFIG.getPath(), key);
        upgrades.put(Integer.parseInt(key), new UltraUpgrade(price, value, permission));
        this.upgrades.put(UpgradeType.UPGRADE_STORAGE, upgrades);
    }

    private void loadSpeedUpgrades() {
        Map<Integer, UltraUpgrade> upgrades = new HashMap<>();
        String key =
                upgradeConfig.getConfigurationSection(ConfigPath.GUI_UPGRADES_SPEED_TIER_CONFIG.getPath()).getKeys(false).toArray()[0].toString();
        double price =
                getUpgradePrice(ConfigPath.GUI_UPGRADES_SPEED_TIER_CONFIG.getPath(), key);
        double value =
                getUpgradeValue(ConfigPath.GUI_UPGRADES_SPEED_TIER_CONFIG.getPath(), key);
        String permission =
                getUpgradePermission(ConfigPath.GUI_UPGRADES_SPEED_TIER_CONFIG.getPath(), key);
        upgrades.put(Integer.parseInt(key), new UltraUpgrade(price, value, permission));

        this.upgrades.put(UpgradeType.UPGRADE_SPEED, upgrades);
    }

    private void loadMultiplierUpgrades() {
        Map<Integer, UltraUpgrade> upgrades = new HashMap<>();
        String key =
                upgradeConfig.getConfigurationSection(ConfigPath.GUI_UPGRADES_MULTIPLIER_TIER_CONFIG.getPath()).getKeys(false).toArray()[0].toString();
        double price =
                getUpgradePrice(ConfigPath.GUI_UPGRADES_MULTIPLIER_TIER_CONFIG.getPath(), key);
        double value =
                getUpgradeValue(ConfigPath.GUI_UPGRADES_MULTIPLIER_TIER_CONFIG.getPath(), key);
        String permission =
                getUpgradePermission(ConfigPath.GUI_UPGRADES_MULTIPLIER_TIER_CONFIG.getPath(), key);
        upgrades.put(Integer.parseInt(key), new UltraUpgrade(price, value, permission));
        this.upgrades.put(UpgradeType.UPGRADE_MULTIPLIER, upgrades);
    }

    private void loadMembersUpgrades() {
        Map<Integer, UltraUpgrade> upgrades = new HashMap<>();
        String key =
                upgradeConfig.getConfigurationSection(ConfigPath.GUI_UPGRADES_MEMBERS_TIER_CONFIG.getPath()).getKeys(false).toArray()[0].toString();
        double price =
                getUpgradePrice(ConfigPath.GUI_UPGRADES_MEMBERS_TIER_CONFIG.getPath(), key);
        double value =
                getUpgradeValue(ConfigPath.GUI_UPGRADES_MEMBERS_TIER_CONFIG.getPath(), key);
        String permission =
                getUpgradePermission(ConfigPath.GUI_UPGRADES_MEMBERS_TIER_CONFIG.getPath(), key);
        upgrades.put(Integer.parseInt(key), new UltraUpgrade(price, value, permission));
        this.upgrades.put(UpgradeType.UPGRADE_MEMBERS, upgrades);
    }

    private void loadSellQuantityUpgrades() {
        Map<Integer, UltraUpgrade> upgrades = new HashMap<>();
        String key =
                upgradeConfig.getConfigurationSection(ConfigPath.GUI_UPGRADES_SELL_QUANTITY_TIER_CONFIG.getPath()).getKeys(false).toArray()[0].toString();
        double price =
                getUpgradePrice(ConfigPath.GUI_UPGRADES_SELL_QUANTITY_TIER_CONFIG.getPath(), key);
        double value =
                getUpgradeValue(ConfigPath.GUI_UPGRADES_SELL_QUANTITY_TIER_CONFIG.getPath(), key);
        String permission =
                getUpgradePermission(ConfigPath.GUI_UPGRADES_SELL_QUANTITY_TIER_CONFIG.getPath(), key);
        upgrades.put(Integer.parseInt(key), new UltraUpgrade(price, value, permission));
        this.upgrades.put(UpgradeType.UPGRADE_SELL_QUANTITY, upgrades);
    }

    private double getUpgradePrice(String path, String tier) {
        return upgradeConfig.getDouble(path + "." + tier + ".price");
    }

    private double getUpgradeValue(String path, String tier) {
        return upgradeConfig.getDouble(path + "." + tier + ".value");
    }

    private String getUpgradePermission(String path, String tier) {
        return upgradeConfig.getString(path + "." + tier + ".permission-node");
    }
}

