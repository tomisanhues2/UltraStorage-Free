package me.tomisanhues2.ultrastorage.tasks;

import me.tomisanhues2.ultrastorage.UltraStorage;
import org.bukkit.scheduler.BukkitRunnable;

public class SaveTimerTask extends BukkitRunnable {
    private final UltraStorage plugin;

    public SaveTimerTask(UltraStorage plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (plugin.getChestManager().getUltraChestsList().isEmpty()) return;
        plugin.getChestManager().saveAll();
    }
}
