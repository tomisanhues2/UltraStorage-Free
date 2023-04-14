package me.tomisanhues2.ultrastorage.utils;

import me.tomisanhues2.ultrastorage.UltraStorage;
import org.bukkit.Bukkit;

import java.util.logging.Level;

public class ReloadException extends Exception {
    public ReloadException(String message) {
        super(message);
    }

    public ReloadException(Cases cases) {
        super();
        Bukkit.getLogger().log(Level.SEVERE, UltraStorage.getInstance().getMessageUtils().getMessage(cases.getPath()));
    }
}
