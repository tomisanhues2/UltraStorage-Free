package me.tomisanhues2.ultrastorage.gui.abs;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class InventoryUpgradeGUI extends InventoryGUI {
    public InventoryUpgradeGUI() {
        super();
    }

    @Override
    public Inventory getInventory() {
        return super.getInventory();
    }

    @Override
    public void addButton(int slot, InventoryButton button) {
        super.addButton(slot, button);
    }

    @Override
    public void addItem(int slot, InventoryItem item) {
        super.addItem(slot, item);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        super.onClick(event);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        super.onOpen(event);
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        super.onClose(event);
    }

    @Override
    public ItemStack getButton(String materialOrBase64, String name, List<String> lore) {
        return super.getButton(materialOrBase64, name, lore);
    }

    @Override
    public ItemStack getButton(Material material, String name, List<String> lore) {
        return super.getButton(material, name, lore);
    }

    @Override
    public ItemStack getButton(String materialOrBase64, String name, List<String> lore, boolean doEnchant) {
        return super.getButton(materialOrBase64, name, lore, doEnchant);
    }

    @Override
    protected abstract Inventory createInventory();

    @Override
    public void decorate(Player player) {

        int inventorySize = this.getInventory().getSize();
        InventoryItem backgroundItem =
                new InventoryItem().creator(player1 -> createBackgroundItem());
        for (int i = 0; i < inventorySize; i++) {
            if (i == 13) {
                continue;
            }
            this.addItem(i, backgroundItem);
        }
        super.decorate(player);
    }
}
