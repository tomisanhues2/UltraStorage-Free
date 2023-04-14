package me.tomisanhues2.ultrastorage.cache;

import me.tomisanhues2.ultrastorage.data.UltraChest;

import java.util.UUID;

public interface PersistenceHandler {
    void saveUltraChestData(UltraChest ultraChest);

    UltraChest loadUltraChestData(UUID chestUUID);
}
