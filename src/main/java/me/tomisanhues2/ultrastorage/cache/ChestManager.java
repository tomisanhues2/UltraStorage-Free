package me.tomisanhues2.ultrastorage.cache;

import me.tomisanhues2.ultrastorage.UltraStorage;
import me.tomisanhues2.ultrastorage.data.UltraChest;
import me.tomisanhues2.ultrastorage.nbt.NBTTags;
import org.bukkit.Chunk;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Chest;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChestManager {
    private final List<UltraChest> ultraChestsList = new CopyOnWriteArrayList<>();
    private final UltraStorage plugin = UltraStorage.getInstance();
    private final PersistenceHandler persistenceHandler;

    public ChestManager(PersistenceHandler persistenceHandler) {
        this.persistenceHandler = persistenceHandler;
        if (!plugin.getDataFolder().exists()) {
            return;
        }
        // Load all chest data from inside a folder inside the plugin's data folder
        File ultraChestDataFolder = new File(plugin.getDataFolder(), "ultraChestData");
        if (!ultraChestDataFolder.exists()) {
            return;
        }
        File[] ultraChestDataFiles = ultraChestDataFolder.listFiles();
        if (ultraChestDataFiles == null) {
            return;
        }
        for (File ultraChestDataFile : ultraChestDataFiles) {
            if (ultraChestDataFile.isDirectory()) {
                continue;
            }
            String ultraChestDataFileName = ultraChestDataFile.getName();
            if (!ultraChestDataFileName.endsWith(".yml")) {
                continue;
            }
            String ultraChestDataFileNameWithoutExtension =
                    ultraChestDataFileName.substring(0, ultraChestDataFileName.length() - 4);
            UUID ultraChestUUID;
            try {
                ultraChestUUID = UUID.fromString(ultraChestDataFileNameWithoutExtension);
            } catch (IllegalArgumentException e) {
                continue;
            }
            loadChestData(ultraChestUUID, null);
        }

    }

    public void loadChestData(UUID ultraChestUUID, Chest chest) {
        UltraChest loadedChest = persistenceHandler.loadUltraChestData(ultraChestUUID);
        if (loadedChest == null) {
            if (chest != null) {
                chest.getPersistentDataContainer().remove(new NamespacedKey(plugin, NBTTags.ULTRA_CHEST_UUID));
            }
            return;
        }

        addChest(loadedChest);
    }

    public void unloadChestData(UltraChest ultraChest) {
        persistenceHandler.saveUltraChestData(ultraChest);
        ultraChestsList.remove(ultraChest);
    }

    public void addChest(UltraChest ultraChest) {
        if (ultraChestsList.contains(ultraChest)) {
            return;
        }
        ultraChestsList.add(ultraChest);
    }

    public void createNewChest(UltraChest ultraChest) {
        addChest(ultraChest);
    }

    public List<UltraChest> getUltraChestsList() {
        return ultraChestsList;
    }

    public UltraChest getChest(Chunk chunk) {
        return ultraChestsList.stream().filter(ultraChest -> ultraChest.getLocation().getChunk().equals(chunk)).findFirst().orElse(null);
    }

    public UltraChest getChest(UUID uuid) {
        return ultraChestsList.stream().filter(ultraChest -> ultraChest.ultraChestUUID.equals(uuid)).findFirst().orElse(null);
    }

    public UltraChest getChest(Chest chest) {
        return ultraChestsList.stream().filter(ultraChest -> ultraChest.getLocation().equals(chest.getLocation())).findFirst().orElse(null);
    }

    public boolean isUltraChest(Chest chest) {
        return ultraChestsList.stream().anyMatch(ultraChest -> ultraChest.getLocation().equals(chest.getLocation()));
    }

    public boolean hasUltraChest(Chunk chunk) {
        return ultraChestsList.stream().anyMatch(ultraChest -> ultraChest.getLocation().getChunk().equals(chunk));
    }

    public boolean saveAll() {
        for (UltraChest ultraChest : ultraChestsList) {
            persistenceHandler.saveUltraChestData(ultraChest);
        }

        return true;
    }
}