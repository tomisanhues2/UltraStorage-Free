package me.tomisanhues2.ultrastorage.config;

import com.jeff_media.jefflib.Msg;
import me.tomisanhues2.ultrastorage.UltraStorage;
import me.tomisanhues2.ultrastorage.utils.Cases;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Config {
    private final UltraStorage plugin;

    public Config(final UltraStorage plugin) {
        this.plugin = plugin;

    }

    public static void send(final CommandSender receiver, final String... message) {
        if (receiver == null) return;
        for (final String line : message) {
            send(receiver, line);
        }
    }

    public static void send(final CommandSender receiver, Cases cases) {
        if (receiver == null) return;
        if (cases == null) return;
        Msg.send(receiver, UltraStorage.getInstance().getMessageUtils().getMessage(cases.getPath()));
    }

    public static List<String> getStringListLore(ConfigPath path) {
        return UltraStorage.getInstance().getConfig().getStringList(path.getPath() + ".lore");
    }

    public static String getStringMaterial(ConfigPath path) {
        return UltraStorage.getInstance().getConfig().getString(path.getPath() + ".material");
    }

    public static String getString(ConfigPath path) {
        return UltraStorage.getInstance().getConfig().getString(path.getPath());
    }
}
