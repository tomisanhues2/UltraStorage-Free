package me.tomisanhues2.ultrastorage.utils;

import me.tomisanhues2.ultrastorage.UltraStorage;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

public class MessageUtils {
    private final YamlConfiguration messageConfig;

    public MessageUtils(UltraStorage plugin) {
        try (InputStream stream = plugin.getResource("messages.yml"); InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            messageConfig = YamlConfiguration.loadConfiguration(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public String getMessage(String path) {
        return messageConfig.getString(path);
    }
}
