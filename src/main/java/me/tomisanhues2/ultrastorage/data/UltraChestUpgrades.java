package me.tomisanhues2.ultrastorage.data;

import me.tomisanhues2.ultrastorage.utils.UpgradeType;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class UltraChestUpgrades implements ConfigurationSerializable {
    private final Map<UpgradeType, Integer> upgrades;

    public UltraChestUpgrades() {
        upgrades = new HashMap<>();
        upgrades.put(UpgradeType.UPGRADE_SLOT, 1);
        upgrades.put(UpgradeType.UPGRADE_STORAGE, 1);
        upgrades.put(UpgradeType.UPGRADE_SPEED, 1);
        upgrades.put(UpgradeType.UPGRADE_MULTIPLIER, 1);
        upgrades.put(UpgradeType.UPGRADE_MEMBERS, 1);
        upgrades.put(UpgradeType.UPGRADE_SELL_QUANTITY, 1);
    }

    public UltraChestUpgrades(Map<UpgradeType, Integer> upgrades) {
        this.upgrades = upgrades;
    }

    public static UltraChestUpgrades deserialize(Map<String, Object> map) {
        Map<UpgradeType, Integer> upgrades =
                (Map<UpgradeType, Integer>) map.get("upgrades");
        return new UltraChestUpgrades(upgrades);
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("upgrades", upgrades);
        return map;
    }
}
