package me.tomisanhues2.ultrastorage.tasks;

import me.tomisanhues2.ultrastorage.UltraStorage;
import org.bukkit.scheduler.BukkitTask;

public class TaskManager {
    private static TaskManager instance;
    private final BukkitTask sellUpdateTask = null;
    private BukkitTask hologramUpdateTask = null;
    private BukkitTask saveUpdateTask = null;
    private BukkitTask reloadUpdateTask = null;

    private TaskManager() {
    }

    public static TaskManager getInstance() {
        return instance == null ? (instance = new TaskManager()) : instance;
    }

    public void attemptStartHologram() {
        if (this.hologramUpdateTask != null) {
            this.hologramUpdateTask.cancel();
            this.hologramUpdateTask = null;
        }

        this.hologramUpdateTask =
                (new HologramTimerTask(UltraStorage.getInstance())).runTaskTimer(UltraStorage.getInstance(), 20L, 20L);

    }

    public void attemptAutoSave() {
        if (this.saveUpdateTask != null) {
            this.saveUpdateTask.cancel();
            this.saveUpdateTask = null;
        }

        this.saveUpdateTask =
                (new SaveTimerTask(UltraStorage.getInstance())).runTaskTimer(UltraStorage.getInstance(), 20 * 60L, 20 * 60L);
    }

}
