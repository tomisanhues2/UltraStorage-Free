package me.tomisanhues2.ultrastorage.gui.impl.upgrade;

import com.jeff_media.jefflib.TextUtils;
import me.tomisanhues2.ultrastorage.UltraStorage;
import me.tomisanhues2.ultrastorage.config.ConfigPath;
import me.tomisanhues2.ultrastorage.config.GuiConfig;
import me.tomisanhues2.ultrastorage.data.UltraChest;
import me.tomisanhues2.ultrastorage.gui.abs.InventoryButton;
import me.tomisanhues2.ultrastorage.gui.abs.InventoryUpgradeGUI;
import me.tomisanhues2.ultrastorage.gui.impl.main.MainMenuInventory;
import me.tomisanhues2.ultrastorage.utils.PlaceholderUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class SellQuantityUpgradeInventory extends InventoryUpgradeGUI {
    private final UltraChest ultraChest;
    private final UltraStorage plugin = UltraStorage.getInstance();

    public SellQuantityUpgradeInventory(UltraChest ultraChest) {
        super();
        this.ultraChest = ultraChest;
    }

    @Override
    protected Inventory createInventory() {
        return Bukkit.createInventory(null, 3 * 9, TextUtils.color("Sell Quantity Upgrade"));
    }

    @Override
    public void decorate(Player player) {

        InventoryButton button =
                new InventoryButton().creator(player1 -> getButton(GuiConfig.getStringMaterial(ConfigPath.GUI_UPGRADES_SELL_QUANTITY_ITEM), TextUtils.color(" "), PlaceholderUtils.replacePlaceholders(GuiConfig.getStringListLore(ConfigPath.GUI_UPGRADES_SELL_QUANTITY_ITEM), ultraChest))).consumer(event -> {
                });
        super.addButton(13, button);
        super.decorate(player);
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        super.onClose(event);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            plugin.guiManager.openGUI(new MainMenuInventory(ultraChest), (Player) event.getPlayer());
        }, 1L);
    }
}
