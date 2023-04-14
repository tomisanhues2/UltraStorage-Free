package me.tomisanhues2.ultrastorage.listeners;

import com.jeff_media.jefflib.PDCUtils;
import me.tomisanhues2.ultrastorage.UltraStorage;
import me.tomisanhues2.ultrastorage.config.Config;
import me.tomisanhues2.ultrastorage.data.UltraChest;
import me.tomisanhues2.ultrastorage.gui.impl.main.MainMenuInventory;
import me.tomisanhues2.ultrastorage.nbt.NBTTags;
import me.tomisanhues2.ultrastorage.utils.Cases;
import me.tomisanhues2.ultrastorage.utils.UltraChestUtils;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;
import java.util.UUID;

public class PlayerListener implements Listener {
    final static UltraStorage plugin = UltraStorage.getInstance();

    @EventHandler
    public void onUltraChestClick(final PlayerInteractEvent event) {
        final Player p = event.getPlayer();

        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getClickedBlock() == null) return;
        final Block block = event.getClickedBlock();
        if (!(block.getState() instanceof Chest chest)) return;
        if (!plugin.getChestManager().isUltraChest(chest)) {
            if (PDCUtils.has((Chest) event.getClickedBlock().getState(), NBTTags.ULTRA_CHEST_UUID)) {
                plugin.getChestManager().loadChestData(UUID.fromString(Objects.requireNonNull(PDCUtils.get((Chest) event.getClickedBlock().getState(), NBTTags.ULTRA_CHEST_UUID, PersistentDataType.STRING))), (Chest) event.getClickedBlock().getState());
                event.setCancelled(true);
            }
            return;
        }
        event.setCancelled(true);

        final UltraChest ultraChest = plugin.getChestManager().getChest(chest);

        if (ultraChest == null) return;

        if (!ultraChest.getOwner().equals(p.getUniqueId())) {
            if (!ultraChest.getAllowedPlayers().contains(p.getUniqueId()) || !p.hasPermission("ultrastorage.admin")) {
                Config.send(p, Cases.MSG_ULTRACHEST_NOT_ALLOWED_TO_OPEN);
                return;
            }
        }
        //Get if the click was left or right click
        Config.send(p, Cases.MSG_ULTRACHEST_OPENED);
        openUltraChest(p, ultraChest);

    }

    private void openUltraChest(final Player p, final UltraChest ultraChest) {
        plugin.guiManager.openGUI(new MainMenuInventory(ultraChest), p);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlaceUltraChestItem(final BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        if (item == null || item.getAmount() == 0 || item.getItemMeta() == null) return;
        if (PDCUtils.has(item, NBTTags.ULTRA_CHEST_ITEM, PersistentDataType.STRING)) {
            Player player = event.getPlayer();
            if (!player.hasPermission("ultrastorage.player")) {
                Config.send(player, Cases.MSG_ULTRACHEST_NO_PLACE_PERMISSION);
                event.setCancelled(true);
                return;
            }

            Chunk chunk = event.getBlock().getChunk();
            if (plugin.getChestManager().hasUltraChest(chunk)) {
                Config.send(player, Cases.MSG_ULTRACHEST_ALREADY_PLACED);
                event.setCancelled(true);
                return;
            }

            UltraChestUtils.createNewStorage(player, (Chest) event.getBlock().getState());
            Config.send(player, Cases.MSG_ULTRACHEST_CREATED);
        } else if (PDCUtils.has(item, NBTTags.ULTRA_CHEST_UUID, PersistentDataType.STRING)) {
            Player player = event.getPlayer();
            if (!player.hasPermission("ultrastorage.player")) {
                Config.send(player, Cases.MSG_ULTRACHEST_NO_PLACE_PERMISSION);
                event.setCancelled(true);
                return;
            }
            Chunk chunk = event.getBlock().getChunk();
            if (plugin.getChestManager().hasUltraChest(chunk)) {
                Config.send(player, Cases.MSG_ULTRACHEST_ALREADY_PLACED);
                event.setCancelled(true);
                return;
            }
            UUID uuid =
                    UUID.fromString(Objects.requireNonNull(PDCUtils.get(item, NBTTags.ULTRA_CHEST_UUID, PersistentDataType.STRING)));
            plugin.getChestManager().loadChestData(uuid, null);
            UltraChestUtils.createOldStorage(player, (Chest) event.getBlock().getState(), uuid);
            Config.send(player, Cases.MSG_ULTRACHEST_CREATED);
        }
    }
}
