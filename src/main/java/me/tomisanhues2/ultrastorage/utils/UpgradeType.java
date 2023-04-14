package me.tomisanhues2.ultrastorage.utils;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public enum UpgradeType implements ConfigurationSerializable {
    UPGRADE_SLOT,
    UPGRADE_STORAGE,
    UPGRADE_SPEED,
    UPGRADE_MULTIPLIER,
    UPGRADE_MEMBERS,
    UPGRADE_SELL_QUANTITY;

    public static UpgradeType deserialize(Map<String, Object> map) {
        return UpgradeType.valueOf((String) map.get("upgradeType"));
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("upgradeType", this.name());
        return map;
    }
}
