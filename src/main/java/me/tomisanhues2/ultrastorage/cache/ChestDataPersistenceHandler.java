package me.tomisanhues2.ultrastorage.cache;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.tomisanhues2.ultrastorage.UltraStorage;
import me.tomisanhues2.ultrastorage.data.UltraChest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
public class ChestDataPersistenceHandler implements PersistenceHandler {
    private final UltraStorage plugin;
    private FileConfiguration ultraChestData;

    @SneakyThrows
    @Override
    public void saveUltraChestData(UltraChest ultraChest) {
        File ultraChestDataFile =
                new File(plugin.getDataFolder(), "ultraChestData/" + ultraChest.ultraChestUUID + ".yml");
        ultraChestData = YamlConfiguration.loadConfiguration(ultraChestDataFile);
        ultraChestData.set(" ", ultraChest);
        try {
            ultraChestData.save(ultraChestDataFile);
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    @SneakyThrows
    @Override
    public UltraChest loadUltraChestData(UUID ultraChestUUID) {
        try {
            File ultraChestDataFile =
                    new File(plugin.getDataFolder(), "ultraChestData/" + ultraChestUUID + ".yml");
            if (!ultraChestDataFile.exists()) {
                return null;
            }
            ultraChestData = YamlConfiguration.loadConfiguration(ultraChestDataFile);
            return (UltraChest) ultraChestData.get(" ");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
