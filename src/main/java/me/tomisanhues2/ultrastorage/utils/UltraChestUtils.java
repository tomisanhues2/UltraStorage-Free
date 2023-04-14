package me.tomisanhues2.ultrastorage.utils;

import com.jeff_media.jefflib.PDCUtils;
import com.jeff_media.jefflib.TextUtils;
import com.jeff_media.morepersistentdatatypes.DataType;
import com.jeff_media.morepersistentdatatypes.datatypes.serializable.ConfigurationSerializableDataType;
import me.tomisanhues2.ultrastorage.UltraStorage;
import me.tomisanhues2.ultrastorage.config.Config;
import me.tomisanhues2.ultrastorage.config.ConfigPath;
import me.tomisanhues2.ultrastorage.data.UltraChest;
import me.tomisanhues2.ultrastorage.nbt.NBTTags;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.UUID;

public class UltraChestUtils {
    private static final PersistentDataType<byte[], UltraChest> dataType =
            new ConfigurationSerializableDataType(UltraChest.class);
    private static final UltraStorage plugin = UltraStorage.getInstance();

    public static PersistentDataType<byte[], UltraChest> getDataType() {
        return dataType;
    }

    public static void createNewStorage(Player player, Chest chest) {
        UltraChest ultraChest = new UltraChest();
        ultraChest.setOwner(player.getUniqueId());
        ultraChest.setLocation(chest.getLocation());

        plugin.getChestManager().createNewChest(ultraChest);
        PDCUtils.set(chest, NBTTags.ULTRA_CHEST_UUID, PersistentDataType.STRING, ultraChest.ultraChestUUID.toString());
        PDCUtils.set(chest.getChunk(), NBTTags.ULTRA_CHEST_LOCATION_KEY, DataType.LOCATION, chest.getLocation());
        chest.update();
    }

    public static void createOldStorage(Player player, Chest chest, UUID ultraChestUUID) {

        if (ultraChestUUID == null) {
            return;
        }
        UltraChest ultraChest = plugin.getChestManager().getChest(ultraChestUUID);
        ultraChest.setOwner(player.getUniqueId());
        ultraChest.setLocation(chest.getLocation());
        plugin.getChestManager().createNewChest(ultraChest);
        PDCUtils.set(chest, NBTTags.ULTRA_CHEST_UUID, PersistentDataType.STRING, ultraChest.ultraChestUUID.toString());
        PDCUtils.set(chest.getChunk(), NBTTags.ULTRA_CHEST_LOCATION_KEY, DataType.LOCATION, chest.getLocation());
        chest.update();
    }

    public static void removeStorage(Chunk chunk) {
        PDCUtils.remove(chunk, NBTTags.ULTRA_CHEST_LOCATION_KEY);
    }

    public static ItemStack createChestItem() {
        ItemStack itemStack = new ItemStack(Material.CHEST);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(TextUtils.color(Config.getString(ConfigPath.ULTRA_STORAGE_PHYSICAL_CHEST_ITEM_NAME)));
        List<String> lore =
                Config.getStringListLore(ConfigPath.ULTRA_STORAGE_PHYSICAL_CHEST_ITEM_LORE);
        lore.replaceAll(TextUtils::color);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        PDCUtils.set(itemStack, NBTTags.ULTRA_CHEST_ITEM, PersistentDataType.STRING, "UltraChestItem");
        return itemStack;
    }

    public static ItemStack createUltraChest(UltraChest ultraChest) {
        ItemStack itemStack = new ItemStack(Material.CHEST);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(TextUtils.color(Config.getString(ConfigPath.ULTRA_STORAGE_PHYSICAL_CHEST_ITEM_NAME)));
        itemStack.setItemMeta(itemMeta);
        PDCUtils.set(itemStack, NBTTags.ULTRA_CHEST_UUID, PersistentDataType.STRING, ultraChest.ultraChestUUID.toString());
        return itemStack;
    }
}