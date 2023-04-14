package me.tomisanhues2.ultrastorage.listeners;

import com.jeff_media.jefflib.PDCUtils;
import me.tomisanhues2.ultrastorage.UltraStorage;
import me.tomisanhues2.ultrastorage.config.Config;
import me.tomisanhues2.ultrastorage.nbt.NBTTags;
import me.tomisanhues2.ultrastorage.utils.Cases;
import me.tomisanhues2.ultrastorage.utils.UltraChestUtils;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

public class ChestLoadListener implements Listener {
    private final UltraStorage plugin;

    public ChestLoadListener(UltraStorage plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onShopBuy(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) {
            return;
        }
        if (!(event.getClickedBlock().getState() instanceof Chest chest)) {
            return;
        }
        if (!PDCUtils.has((Chest) event.getClickedBlock().getState(), NBTTags.SHOP_KEY)) {
            return;
        }
        event.setCancelled(true);
        double price = PDCUtils.get(chest, NBTTags.SHOP_KEY, PersistentDataType.DOUBLE);
        if (!UltraStorage.getEconomy().has(event.getPlayer(), price)) {
            Config.send(event.getPlayer(), Cases.MSG_NOT_ENOUGH_MONEY);
            return;
        }
        UltraStorage.getEconomy().withdrawPlayer(event.getPlayer(), price);
        Config.send(event.getPlayer(), Cases.MSG_BOUGHT_ITEM);
        event.getPlayer().getInventory().addItem(UltraChestUtils.createChestItem());
    }
}
