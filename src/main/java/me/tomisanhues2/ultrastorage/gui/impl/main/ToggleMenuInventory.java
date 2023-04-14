package me.tomisanhues2.ultrastorage.gui.impl.main;

import com.jeff_media.jefflib.TextUtils;
import me.tomisanhues2.ultrastorage.UltraStorage;
import me.tomisanhues2.ultrastorage.config.Config;
import me.tomisanhues2.ultrastorage.config.ConfigPath;
import me.tomisanhues2.ultrastorage.config.GuiConfig;
import me.tomisanhues2.ultrastorage.data.UltraChest;
import me.tomisanhues2.ultrastorage.gui.GUI;
import me.tomisanhues2.ultrastorage.gui.abs.InventoryButton;
import me.tomisanhues2.ultrastorage.gui.abs.InventoryGUI;
import me.tomisanhues2.ultrastorage.gui.abs.InventoryItem;
import me.tomisanhues2.ultrastorage.utils.Cases;
import me.tomisanhues2.ultrastorage.utils.PlaceholderUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

public class ToggleMenuInventory extends InventoryGUI {
    private final UltraChest ultraChest;
    private final UltraStorage plugin = UltraStorage.getInstance();

    public ToggleMenuInventory(UltraChest ultraChest) {
        super();
        this.ultraChest = ultraChest;
    }

    @Override
    protected Inventory createInventory() {
        return Bukkit.createInventory(null, 3 * 9, TextUtils.color("&8&lUltraStorage Toggles"));
    }

    @Override
    public void decorate(Player player) {
        int inventorySize = this.getInventory().getSize();
        InventoryItem backGroundItem =
                new InventoryItem().creator(player1 -> createBackgroundItem());

        for (int i = 0; i < inventorySize; i++) {
            this.addItem(i, backGroundItem);
        }

        this.addButton(GUI.SLOT_TOGGLE_AUTO_SELL, createAutoSellToggleButton());
        this.addButton(GUI.SLOT_TOGGLE_BLOCK_COLLECTION, createToggleBlockButton());
        this.addButton(GUI.SLOT_TOGGLE_MOB_KILL_COLLECTION, createToggleMobKillButton());
        this.addItem(GUI.SLOT_TOGGLE_INFO_COLLECTION, createToggleInfoItem());
        this.addButton(GUI.SLOT_TOGGLE_MOB_OTHER_COLLECTION, createToggleMobOtherButton());
        this.addButton(GUI.SLOT_TOGGLE_FURNACE_COLLECTION, createToggleFurnaceButton());
        this.addButton(GUI.SLOT_TOGGLE_CROP_COLLECTION, createToggleCropButton());
        super.decorate(player);
    }

    private InventoryItem createToggleInfoItem() {
        return new InventoryItem().creator(player -> getButton(Objects.requireNonNull(GuiConfig.getStringMaterial(ConfigPath.GUI_TOGGLE_INFO_ITEM)), TextUtils.color(" "), PlaceholderUtils.replacePlaceholders(GuiConfig.getStringListLore(ConfigPath.GUI_TOGGLE_INFO_ITEM), ultraChest)));
    }

    private InventoryButton createToggleBlockButton() {
        return new InventoryButton().creator(player -> getButton(Objects.requireNonNull(GuiConfig.getStringMaterial(ConfigPath.GUI_TOGGLE_BLOCKS_ITEM)), TextUtils.color(" "), PlaceholderUtils.replacePlaceholders(GuiConfig.getStringListLore(ConfigPath.GUI_TOGGLE_BLOCKS_ITEM), ultraChest))).consumer(event -> {
            Config.send((Player) (getInventory().getHolder()), Cases.MSG_TOGGLE_BLOCKS);
            ultraChest.toggleBlockCollect();
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                plugin.guiManager.openGUI(new ToggleMenuInventory(ultraChest), (Player) event.getWhoClicked());
            }, 1L);
        });
    }

    private InventoryButton createToggleMobKillButton() {
        return new InventoryButton().creator(player -> getButton(Objects.requireNonNull(GuiConfig.getStringMaterial(ConfigPath.GUI_TOGGLE_MOB_KILL_ITEM)), TextUtils.color(" "), PlaceholderUtils.replacePlaceholders(GuiConfig.getStringListLore(ConfigPath.GUI_TOGGLE_MOB_KILL_ITEM), ultraChest))).consumer(event -> {
            Config.send((Player) (getInventory().getHolder()), Cases.MSG_TOGGLE_MOB_KILL);
            ultraChest.toggleMobCollectByKilling();
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                plugin.guiManager.openGUI(new ToggleMenuInventory(ultraChest), (Player) event.getWhoClicked());
            }, 1L);
        });
    }

    private InventoryButton createToggleMobOtherButton() {
        return new InventoryButton().creator(player -> getButton(Objects.requireNonNull(GuiConfig.getStringMaterial(ConfigPath.GUI_TOGGLE_MOB_OTHER_ITEM)), TextUtils.color(" "), PlaceholderUtils.replacePlaceholders(GuiConfig.getStringListLore(ConfigPath.GUI_TOGGLE_MOB_OTHER_ITEM), ultraChest))).consumer(event -> {
            Config.send((Player) (getInventory().getHolder()), Cases.MSG_TOGGLE_MOB_OTHER);
            ultraChest.toggleMobCollectOther();
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                plugin.guiManager.openGUI(new ToggleMenuInventory(ultraChest), (Player) event.getWhoClicked());
            }, 1L);
        });
    }

    private InventoryButton createToggleFurnaceButton() {
        return new InventoryButton().creator(player -> getButton(Objects.requireNonNull(GuiConfig.getStringMaterial(ConfigPath.GUI_TOGGLE_FURNACE_ITEM)), TextUtils.color(" "), PlaceholderUtils.replacePlaceholders(GuiConfig.getStringListLore(ConfigPath.GUI_TOGGLE_FURNACE_ITEM), ultraChest))).consumer(event -> {
            Config.send((Player) (getInventory().getHolder()), Cases.MSG_TOGGLE_FURNACE);
            ultraChest.toggleFurnaceCollect();
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                plugin.guiManager.openGUI(new ToggleMenuInventory(ultraChest), (Player) event.getWhoClicked());
            }, 1L);
        });
    }

    private InventoryButton createToggleCropButton() {
        return new InventoryButton().creator(player -> getButton(Objects.requireNonNull(GuiConfig.getStringMaterial(ConfigPath.GUI_TOGGLE_CROP_ITEM)), TextUtils.color(" "), PlaceholderUtils.replacePlaceholders(GuiConfig.getStringListLore(ConfigPath.GUI_TOGGLE_CROP_ITEM), ultraChest))).consumer(event -> {
            Config.send((Player) (getInventory().getHolder()), Cases.MSG_TOGGLE_CROP);
            ultraChest.toggleCropCollect();
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                plugin.guiManager.openGUI(new ToggleMenuInventory(ultraChest), (Player) event.getWhoClicked());
            }, 1L);
        });
    }

    private InventoryButton createAutoSellToggleButton() {
        return new InventoryButton().creator(player -> getButton(Objects.requireNonNull(GuiConfig.getStringMaterial(ConfigPath.GUI_TOGGLE_AUTO_SELL_ITEM)), TextUtils.color(" "), PlaceholderUtils.replacePlaceholders(GuiConfig.getStringListLore(ConfigPath.GUI_TOGGLE_AUTO_SELL_ITEM), ultraChest))).consumer(event -> {
            Config.send((Player) (getInventory().getHolder()), Cases.MSG_TOGGLE_AUTOSELL);
            ultraChest.toggleAutoSell();
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                plugin.guiManager.openGUI(new ToggleMenuInventory(ultraChest), (Player) event.getWhoClicked());
            }, 1L);
        });
    }
}
