package me.tomisanhues2.ultrastorage.gui.impl.main;

import com.jeff_media.jefflib.TextUtils;
import me.tomisanhues2.ultrastorage.UltraStorage;
import me.tomisanhues2.ultrastorage.config.Config;
import me.tomisanhues2.ultrastorage.config.ConfigPath;
import me.tomisanhues2.ultrastorage.config.GuiConfig;
import me.tomisanhues2.ultrastorage.data.UltraChest;
import me.tomisanhues2.ultrastorage.gui.abs.InventoryButton;
import me.tomisanhues2.ultrastorage.gui.abs.InventoryGUI;
import me.tomisanhues2.ultrastorage.gui.abs.InventoryItem;
import me.tomisanhues2.ultrastorage.utils.PlaceholderUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemPaletteInventory extends InventoryGUI {
    private final UltraChest ultraChest;
    private final UltraStorage plugin = UltraStorage.getInstance();

    public ItemPaletteInventory(UltraChest ultraChest) {
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

        for (int i = 0; i < inventorySize; i++) {
            this.addItem(i, backgroundItem);
        }

        List<String> items =
                GuiConfig.getStringList(ConfigPath.GUI_ITEM_FILTER_ALLOWED_ITEMS);
        for (int i = 0; i < items.size(); i++) {
            String item = items.get(i);
            Material material = Material.getMaterial(item);
            if (material == null) {
                continue;
            }
            this.addButton(i, new InventoryButton().creator(player1 -> createFilterItemStack(material)).consumer(event -> {
                ClickType clickType = event.getClick();
                switch (clickType) {
                    case LEFT -> {
                        Config.send(player, ultraChest.removeItemSlot(material));
                    }
                    case RIGHT -> {
                        Config.send(player, ultraChest.addItemSlot(material));
                    }
                }
            }));
        }


        super.decorate(player);
    }

    private ItemStack createFilterItemStack(Material material) {
        return getButton(material, TextUtils.color(" "), PlaceholderUtils.replacePlaceholders(GuiConfig.getStringListLore(ConfigPath.GUI_ITEM_FILTER_ITEM), ultraChest, material));

    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        super.onClose(event);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            plugin.guiManager.openGUI(new SettingsMenuInventory(ultraChest), (Player) event.getPlayer());
        }, 1L);
    }
}
