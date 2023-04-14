package me.tomisanhues2.ultrastorage.tasks;

import me.tomisanhues2.ultrastorage.UltraStorage;
import me.tomisanhues2.ultrastorage.data.UltraChest;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class HologramTimerTask extends BukkitRunnable {
    private final UltraStorage plugin;

    public HologramTimerTask(UltraStorage plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (plugin.getChestManager().getUltraChestsList().isEmpty()) return;
        handle();
    }

    private void handle() {

        if (Bukkit.getOnlinePlayers().size() == 0) return;
        for (UltraChest ultraChest : plugin.getChestManager().getUltraChestsList()) {
            ultraChest.getHologram().updateHologram();
        }
    }
}
