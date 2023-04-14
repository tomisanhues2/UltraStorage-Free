package me.tomisanhues2.ultrastorage.config;

import me.tomisanhues2.ultrastorage.UltraStorage;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GuiConfig {
    private static YamlConfiguration guiConfig;
    private final UltraStorage plugin;

    public GuiConfig(final UltraStorage plugin) {
        this.plugin = plugin;
        try (InputStream stream = plugin.getResource("gui.yml"); InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            guiConfig = YamlConfiguration.loadConfiguration(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }

    public static List<String> getStringList(ConfigPath path) {
        return guiConfig.getStringList(path.getPath());
    }

    public static String getStringName(ConfigPath path) {
        return guiConfig.getString(path.getPath() + ".name");
    }

    public static String getStringMaterial(ConfigPath path) {
        return guiConfig.getString(path.getPath() + ".material");
    }

    public static List<String> getStringListLore(ConfigPath path) {
        return guiConfig.getStringList(path.getPath() + ".lore");
    }
}
