package me.tomisanhues2.ultrastorage.gui.abs;

import com.google.common.base.Enums;
import jline.internal.Nullable;
import me.tomisanhues2.ultrastorage.UltraStorage;
import me.tomisanhues2.ultrastorage.utils.HeadCreator;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class InventoryGUI implements InventoryHandler {
    private final Inventory inventory;
    private final UltraStorage plugin = UltraStorage.getInstance();
    private final Map<Integer, InventoryButton> buttonMap = new HashMap<>();
    private final Map<Integer, InventoryItem> itemMap = new HashMap<>();

    public InventoryGUI() {
        this.inventory = this.createInventory();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void addButton(int slot, InventoryButton button) {
        this.buttonMap.put(slot, button);
    }

    public void decorate(Player player) {
        this.itemMap.forEach((slot, item) -> {
            ItemStack itemStack = item.getIconCreator().apply(player);
            this.inventory.setItem(slot, itemStack);
        });
        this.buttonMap.forEach((slot, button) -> {
            ItemStack item = button.getIconCreator().apply(player);
            this.inventory.setItem(slot, item);
        });
    }

    public void addItem(int slot, InventoryItem item) {
        this.itemMap.put(slot, item);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        int slot = event.getSlot();
        InventoryButton button = this.buttonMap.get(slot);
        if (button != null) {
            button.getEventConsumer().accept(event);
        }
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        this.decorate((Player) event.getPlayer());
    }

    @Override
    public void onClose(InventoryCloseEvent event) {

    }

    protected abstract Inventory createInventory();

    public ItemStack getButton(final String materialOrBase64, final String name, @Nullable final List<String> lore) {
        return getButton(materialOrBase64, name, lore, false);
    }

    public ItemStack getButton(final String materialOrBase64, @Nullable final List<String> lore) {
        return getButton(materialOrBase64, " ", lore, false);
    }

    public ItemStack getButton(final Material material, final String name, @Nullable final List<String> lore) {
        return getButton(material.name(), name, lore, false);
    }

    public ItemStack getButton(final String materialOrBase64, final String name, @Nullable final List<String> lore, boolean doEnchant) {
        final ItemStack item;
        final Material material =
                Enums.getIfPresent(Material.class, materialOrBase64.toUpperCase()).orNull();
        if (material != null) {
            item = new ItemStack(material);
        } else {
            item = HeadCreator.getHead(materialOrBase64);
        }
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        if (doEnchant) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS);
        }
        if (lore != null) meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    protected ItemStack createBackgroundItem() {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        return item;
    }
}
