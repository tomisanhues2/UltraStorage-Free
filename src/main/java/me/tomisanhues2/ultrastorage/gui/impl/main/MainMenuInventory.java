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
import me.tomisanhues2.ultrastorage.gui.impl.upgrade.*;
import me.tomisanhues2.ultrastorage.utils.Cases;
import me.tomisanhues2.ultrastorage.utils.PlaceholderUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;
import java.util.Objects;

public class MainMenuInventory extends InventoryGUI {
    private final UltraChest ultraChest;
    private final UltraStorage plugin = UltraStorage.getInstance();

    public MainMenuInventory(UltraChest ultraChest) {
        super();
        this.ultraChest = ultraChest;
    }

    @Override
    protected Inventory createInventory() {
        return Bukkit.createInventory(null, 6 * 9, TextUtils.color("&8&lUltraStorage"));
    }

    @Override
    public void decorate(Player player) {
        int inventorySize = this.getInventory().getSize();
        InventoryItem backgroundItem =
                new InventoryItem().creator(player1 -> createBackgroundItem());

        InventoryItem barrierItem =
                new InventoryItem().creator(player1 -> createBarrierItem());

        int unlockedSlots = 1;

        for (int i = 18; i < inventorySize; i++) {
            this.addItem(i, backgroundItem);
        }

        addItemList();

        for (int i = unlockedSlots; i < 18; i++) {
            this.addItem(i, barrierItem);
        }

        this.addButton(GUI.SLOT_CHEST_SETTINGS, this.createSettingsButton());
        this.addButton(GUI.SLOT_CHEST_INFO, this.createInfoButton());
        this.addButton(GUI.SLOT_CHEST_UPGRADE_SLOTS, this.createUpgradeSlotsButton());
        this.addButton(GUI.SLOT_CHEST_UPGRADE_STORAGE, this.createUpgradeStorageButton());
        this.addButton(GUI.SLOT_CHEST_UPGRADE_SELL_QUANTITY, this.createUpgradeSellQuantityButton());
        this.addButton(GUI.SLOT_CHEST_UPGRADE_SPEED, this.createUpgradeSpeedButton());
        this.addButton(GUI.SLOT_CHEST_UPGRADE_MULTIPLIER, this.createUpgradeMultiplierButton());
        this.addButton(GUI.SLOT_CHEST_UPGRADE_MEMBERS, this.createUpgradeMembersButton());

        super.decorate(player);
    }

    private void addItemList() {
        Map<Material, Integer> items = ultraChest.getItems();
        if (items == null || items.isEmpty()) return;
        for (int i = 0; i < items.size(); i++) {
            Material material = (Material) items.keySet().toArray()[i];
            addButton(i, createUltraChestItemButton(material));
        }
    }

    private InventoryButton createUltraChestItemButton(Material material) {
        return new InventoryButton().creator(player -> getButton(material, TextUtils.color(" "), PlaceholderUtils.replacePlaceholders(Config.getStringListLore(ConfigPath.GUI_MAIN_MENU_ITEM_DEFAULT_ITEM), ultraChest, material))).consumer(event -> {
            ClickType clickType = event.getClick();
            switch (clickType) {
                case LEFT -> {
                    Cases cases = ultraChest.withdrawItem(material, 1);
                    if (cases == Cases.ITEM_WITHDRAWN_FROM_CHEST) {
                        event.getWhoClicked().getInventory().addItem(new ItemStack(material, 1));
                    }
                    Config.send(event.getWhoClicked(), cases);
                }
                case RIGHT -> {
                    Cases cases = ultraChest.withdrawItem(material, 64);
                    if (cases == Cases.ITEM_WITHDRAWN_FROM_CHEST) {
                        event.getWhoClicked().getInventory().addItem(new ItemStack(material, 64));
                    }
                    Config.send(event.getWhoClicked(), cases);
                }
            }
        });
    }

    private InventoryButton createSettingsButton() {
        return new InventoryButton().creator(player -> getButton(Config.getStringMaterial(ConfigPath.GUI_MAIN_MENU_SETTINGS_ITEM), TextUtils.color(" "), PlaceholderUtils.replacePlaceholders(Config.getStringListLore(ConfigPath.GUI_MAIN_MENU_SETTINGS_ITEM), ultraChest))).consumer(event -> {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                plugin.guiManager.openGUI(new SettingsMenuInventory(ultraChest), (Player) event.getWhoClicked());
            }, 1L);
        });
    }

    private InventoryButton createInfoButton() {
        return new InventoryButton().creator(player -> getButton(Objects.requireNonNull(Config.getStringMaterial(ConfigPath.GUI_MAIN_MENU_INFO_ITEM)), TextUtils.color(" "), PlaceholderUtils.replacePlaceholders(Config.getStringListLore(ConfigPath.GUI_MAIN_MENU_INFO_ITEM), ultraChest))).consumer(event -> {

        });
    }

    private ItemStack createBarrierItem() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(TextUtils.color("&c&lYou have not unlocked this item slot!, Head over to the upgrade menu to unlock it!"));
        item.setItemMeta(meta);
        return item;
    }

    protected InventoryButton createUpgradeSlotsButton() {
        return new InventoryButton().creator(player -> getButton(Objects.requireNonNull(Config.getStringMaterial(ConfigPath.GUI_MAIN_MENU_UPGRADE_SLOTS_ITEM)), TextUtils.color(" "), PlaceholderUtils.replacePlaceholders(Config.getStringListLore(ConfigPath.GUI_MAIN_MENU_UPGRADE_SLOTS_ITEM), ultraChest))).consumer(event -> {
            Bukkit.getScheduler().runTask(plugin, () -> {
                plugin.guiManager.openGUI(new SlotUpgradeInventory(ultraChest), (Player) event.getWhoClicked());
            });
        });
    }

    protected InventoryButton createUpgradeStorageButton() {
        return new InventoryButton().creator(player -> getButton(Objects.requireNonNull(Config.getStringMaterial(ConfigPath.GUI_MAIN_MENU_UPGRADE_STORAGE_ITEM)), TextUtils.color(" "), PlaceholderUtils.replacePlaceholders(Config.getStringListLore(ConfigPath.GUI_MAIN_MENU_UPGRADE_STORAGE_ITEM), ultraChest))).consumer(event -> {
            Bukkit.getScheduler().runTask(plugin, () -> {
                plugin.guiManager.openGUI(new StorageUpgradeInventory(ultraChest), (Player) event.getWhoClicked());
            });
        });
    }

    protected InventoryButton createUpgradeSpeedButton() {
        return new InventoryButton().creator(player -> getButton(Objects.requireNonNull(Config.getStringMaterial(ConfigPath.GUI_MAIN_MENU_UPGRADE_SPEED_ITEM)), TextUtils.color(" "), PlaceholderUtils.replacePlaceholders(Config.getStringListLore(ConfigPath.GUI_MAIN_MENU_UPGRADE_SPEED_ITEM), ultraChest))).consumer(event -> {
            Bukkit.getScheduler().runTask(plugin, () -> {
                plugin.guiManager.openGUI(new SpeedUpgradeInventory(ultraChest), (Player) event.getWhoClicked());
            });
        });
    }

    protected InventoryButton createUpgradeMultiplierButton() {
        return new InventoryButton().creator(player -> getButton(Objects.requireNonNull(Config.getStringMaterial(ConfigPath.GUI_MAIN_MENU_UPGRADE_MULTIPLIER_ITEM)), TextUtils.color(" "), PlaceholderUtils.replacePlaceholders(Config.getStringListLore(ConfigPath.GUI_MAIN_MENU_UPGRADE_MULTIPLIER_ITEM), ultraChest))).consumer(event -> {
            Bukkit.getScheduler().runTask(plugin, () -> {
                plugin.guiManager.openGUI(new MultiplierUpgradeInventory(ultraChest), (Player) event.getWhoClicked());
            });
        });
    }

    protected InventoryButton createUpgradeMembersButton() {
        return new InventoryButton().creator(player -> getButton(Objects.requireNonNull(Config.getStringMaterial(ConfigPath.GUI_MAIN_MENU_UPGRADE_MEMBERS_ITEM)), TextUtils.color(" "), PlaceholderUtils.replacePlaceholders(Config.getStringListLore(ConfigPath.GUI_MAIN_MENU_UPGRADE_MEMBERS_ITEM), ultraChest))).consumer(event -> {
            Bukkit.getScheduler().runTask(plugin, () -> {
                plugin.guiManager.openGUI(new MembersUpgradeInventory(ultraChest), (Player) event.getWhoClicked());
            });
        });
    }

    protected InventoryButton createUpgradeSellQuantityButton() {
        return new InventoryButton().creator(player -> getButton(Objects.requireNonNull(Config.getStringMaterial(ConfigPath.GUI_MAIN_MENU_UPGRADE_SELL_QUANTITY_ITEM)), TextUtils.color(" "), PlaceholderUtils.replacePlaceholders(Config.getStringListLore(ConfigPath.GUI_MAIN_MENU_UPGRADE_SELL_QUANTITY_ITEM), ultraChest))).consumer(event -> {
            Bukkit.getScheduler().runTask(plugin, () -> {
                plugin.guiManager.openGUI(new SellQuantityUpgradeInventory(ultraChest), (Player) event.getWhoClicked());
            });
        });
    }
}
