package me.tomisanhues2.ultrastorage.listeners;

import eu.decentsoftware.holograms.event.HologramClickEvent;
import me.tomisanhues2.ultrastorage.UltraStorage;
import me.tomisanhues2.ultrastorage.config.Config;
import me.tomisanhues2.ultrastorage.data.UltraChest;
import me.tomisanhues2.ultrastorage.utils.Cases;
import me.tomisanhues2.ultrastorage.utils.UltraChestUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class UltraChestListener implements Listener {
    private final UltraStorage plugin;

    public UltraChestListener(UltraStorage plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent event) {
        if (Bukkit.getOnlinePlayers().isEmpty()) return;
        if (isUltraChestListEmpty()) return;
        if (!chunkHasUltraChest(event.getBlock().getChunk())) return;
        UltraChest ultraChest =
                plugin.getChestManager().getChest(event.getBlock().getChunk());
        Player player = event.getPlayer();
        if (event.getBlock().getType() == Material.CHEST) {
            if (ultraChest.getOwner() == player.getUniqueId()) {
                UltraChestUtils.removeStorage(event.getBlock().getChunk());
                Config.send(player, Cases.MSG_ULTRACHEST_REMOVED);
                return;
            }
            Config.send(player, Cases.MSG_ULTRACHEST_NO_PERMISSION);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void entityExplodeEvent(EntityExplodeEvent event) {
        if (Bukkit.getOnlinePlayers().isEmpty()) return;
        if (isUltraChestListEmpty()) return;
        if (!chunkHasUltraChest(event.getLocation().getChunk())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void blockBreakEvent(BlockDropItemEvent event) {
        if (Bukkit.getOnlinePlayers().isEmpty()) return;
        if (isUltraChestListEmpty()) return;
        if (!chunkHasUltraChest(event.getBlock().getChunk())) return;
        UltraChest ultraChest =
                plugin.getChestManager().getChest(event.getBlock().getChunk());
        if (event.getBlockState() instanceof Ageable) {
            if (!ultraChest.isCropCollectEnabled()) return;
            itemIterator(event, ultraChest);
            return;
        }
        if (!ultraChest.isBlockCollectEnabled()) return;
        itemIterator(event, ultraChest);
    }

    private void itemIterator(BlockDropItemEvent event, UltraChest ultraChest) {
        Iterator<Item> iterator = event.getItems().iterator();
        while (iterator.hasNext()) {
            ItemStack itemStack = iterator.next().getItemStack();
            if (!ultraChest.isItemStackInList(itemStack)) continue;
            if (itemStack.getType() == Material.AIR) continue;
            if (ultraChest.addMaterialCount(itemStack.getType(), itemStack.getAmount())) {
                iterator.remove();
                //debug
                Bukkit.broadcastMessage("Added " + itemStack.getAmount() + " " + itemStack.getType() + " to " + ultraChest.getLocation().toString());
            }
        }
    }

    //Mob death collection event
    @EventHandler
    public void mobDeathEvent(EntityDeathEvent event) {
        if (Bukkit.getOnlinePlayers().isEmpty()) return;
        if (isUltraChestListEmpty()) return;
        if (!chunkHasUltraChest(event.getEntity().getLocation().getChunk())) return;
        UltraChest ultraChest =
                plugin.getChestManager().getChest(event.getEntity().getLocation().getChunk());
        if (event.getEntity().getKiller() instanceof Player) {
            if (!ultraChest.isMobCollectByKillingEnabled()) return;
            itemStackIterator(event, ultraChest);
        } else {
            if (!ultraChest.isMobCollectOtherEnabled()) return;
            itemStackIterator(event, ultraChest);
        }
    }

    private void itemStackIterator(EntityDeathEvent event, UltraChest ultraChest) {
        Iterator<ItemStack> iterator = event.getDrops().iterator();
        while (iterator.hasNext()) {
            ItemStack itemStack = iterator.next();
            if (!ultraChest.isItemStackInList(itemStack)) continue;
            if (itemStack.getType() == Material.AIR) continue;
            if (ultraChest.addMaterialCount(itemStack.getType(), itemStack.getAmount())) {
                iterator.remove();
                //debug
                Bukkit.broadcastMessage("Added " + itemStack.getAmount() + " " + itemStack.getType() + " to " + ultraChest.getLocation().toString());
            }
        }
    }

    //Furnace collection event
    @EventHandler
    public void furnaceSmeltEvent(FurnaceSmeltEvent event) {
        if (Bukkit.getOnlinePlayers().size() == 0) return;
        if (isUltraChestListEmpty()) return;
        if (!chunkHasUltraChest(event.getBlock().getChunk())) return;
        UltraChest ultraChest =
                plugin.getChestManager().getChest(event.getBlock().getChunk());
        ItemStack itemStack = event.getResult();
        if (!ultraChest.isItemStackInList(itemStack)) return;
        if (itemStack.getType() == Material.AIR) return;
        if (ultraChest.addMaterialCount(itemStack.getType(), itemStack.getAmount())) {
            //debug
            Bukkit.broadcastMessage("Added " + itemStack.getAmount() + " " + itemStack.getType() + " to " + ultraChest.getLocation().toString());
        }
    }

    @EventHandler
    public void onHologramClick(HologramClickEvent event) {
        if (!chunkHasUltraChest(event.getHologram().getLocation().getChunk())) return;
        UltraChest ultraChest =
                plugin.getChestManager().getChest(event.getHologram().getLocation().getChunk());
        if (ultraChest.getOwner() == event.getPlayer().getUniqueId() || (!(ultraChest.getAllowedPlayers().contains(event.getPlayer().getUniqueId())))) {
            ultraChest.incrementPage();
            ultraChest.getHologram().updateHologram();
            return;
        }
        Config.send(event.getPlayer(), Cases.MSG_ULTRACHEST_NO_PERMISSION);
    }

    private boolean isUltraChestListEmpty() {
        return plugin.getChestManager().getUltraChestsList().isEmpty();
    }

    private boolean chunkHasUltraChest(Chunk chunk) {
        return plugin.getChestManager().hasUltraChest(chunk);
    }

}
