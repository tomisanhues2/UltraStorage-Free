package me.tomisanhues2.ultrastorage.gui.impl.main;

import com.jeff_media.jefflib.TextUtils;
import me.tomisanhues2.ultrastorage.UltraStorage;
import me.tomisanhues2.ultrastorage.config.Config;
import me.tomisanhues2.ultrastorage.config.ConfigPath;
import me.tomisanhues2.ultrastorage.data.UltraChest;
import me.tomisanhues2.ultrastorage.gui.GUI;
import me.tomisanhues2.ultrastorage.gui.abs.InventoryButton;
import me.tomisanhues2.ultrastorage.gui.abs.InventoryGUI;
import me.tomisanhues2.ultrastorage.gui.abs.InventoryItem;
import me.tomisanhues2.ultrastorage.utils.Cases;
import me.tomisanhues2.ultrastorage.utils.PlaceholderUtils;
import me.tomisanhues2.ultrastorage.utils.UltraChestUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

public class SettingsMenuInventory extends InventoryGUI {
    private final UltraChest ultraChest;
    private final UltraStorage plugin = UltraStorage.getInstance();

    public SettingsMenuInventory(UltraChest ultraChest) {
        super();
        this.ultraChest = ultraChest;
    }

    @Override
    protected Inventory createInventory() {
        return Bukkit.createInventory(null, 3 * 9, TextUtils.color("&8&lUltraStorage Settings"));
    }

    @Override
    public void decorate(Player player) {
        int inventorySize = this.getInventory().getSize();
        InventoryItem backgroundItem =
                new InventoryItem().creator(player1 -> createBackgroundItem());

        for (int i = 0; i < inventorySize; i++) {
            this.addItem(i, backgroundItem);
        }

        this.addButton(GUI.SLOT_SETTINGS_ITEMS, createItemMenuButton());
        this.addButton(GUI.SLOT_SETTINGS_MEMBERS, createMembersMenuButton());
        this.addButton(GUI.SLOT_SETTINGS_COLLECTIONS, createCollectionsMenuButton());
        this.addButton(22, createPickupUltraChestButton());
        super.decorate(player);
    }

    private InventoryButton createItemMenuButton() {
        return new InventoryButton().creator(player -> getButton(Objects.requireNonNull(Config.getStringMaterial(ConfigPath.GUI_SETTINGS_ITEMS_ITEM)), TextUtils.color(" "), PlaceholderUtils.replacePlaceholders(Config.getStringListLore(ConfigPath.GUI_SETTINGS_ITEMS_ITEM), ultraChest))).consumer(event -> {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                plugin.guiManager.openGUI(new ItemPaletteInventory(ultraChest), (Player) event.getWhoClicked());
            }, 1L);
        });
    }

    private InventoryButton createMembersMenuButton() {
        return new InventoryButton().creator(player -> getButton(Objects.requireNonNull(Config.getStringMaterial(ConfigPath.GUI_SETTINGS_MEMBERS_ITEM)), TextUtils.color(" "), PlaceholderUtils.replacePlaceholders(Config.getStringListLore(ConfigPath.GUI_SETTINGS_MEMBERS_ITEM), ultraChest))).consumer(event -> {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                plugin.guiManager.openGUI(new MembersMenuInventory(ultraChest), (Player) event.getWhoClicked());
            }, 1L);
        });
    }

    private InventoryButton createCollectionsMenuButton() {
        return new InventoryButton().creator(player -> getButton(Objects.requireNonNull(Config.getStringMaterial(ConfigPath.GUI_SETTINGS_COLLECTIONS_ITEM)), TextUtils.color(" "), PlaceholderUtils.replacePlaceholders(Config.getStringListLore(ConfigPath.GUI_SETTINGS_COLLECTIONS_ITEM), ultraChest))).consumer(event -> {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                plugin.guiManager.openGUI(new ToggleMenuInventory(ultraChest), (Player) event.getWhoClicked());
            }, 1L);
        });
    }

    private InventoryButton createPickupUltraChestButton() {
        return new InventoryButton().creator(player -> getButton(Objects.requireNonNull(Config.getStringMaterial(ConfigPath.GUI_SETTINGS_PICKUP_ITEM)), TextUtils.color(" "), PlaceholderUtils.replacePlaceholders(Config.getStringListLore(ConfigPath.GUI_SETTINGS_PICKUP_ITEM), ultraChest))).consumer(event -> {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                Chunk chunk = ultraChest.getLocation().getChunk();
                event.getWhoClicked().getInventory().addItem(UltraChestUtils.createUltraChest(ultraChest));
                ultraChest.getHologram().removeHologram();
                plugin.getChestManager().unloadChestData(ultraChest);
                UltraChestUtils.removeStorage(chunk);
                chunk.getWorld().getBlockAt(ultraChest.getLocation()).setType(Material.AIR, true);
                event.getWhoClicked().closeInventory();
                Config.send(event.getWhoClicked(), Cases.MSG_ULTRACHEST_REMOVED);

            }, 1L);
        });
    }
}

