package me.tomisanhues2.ultrastorage.mechanics;

import com.jeff_media.jefflib.TextUtils;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import me.tomisanhues2.ultrastorage.config.ConfigPath;
import me.tomisanhues2.ultrastorage.config.GuiConfig;
import me.tomisanhues2.ultrastorage.data.UltraChest;
import me.tomisanhues2.ultrastorage.utils.PlaceholderUtils;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static me.tomisanhues2.ultrastorage.utils.PlaceholderUtils.getPlaceholders;

public class UChestHologramDecentHolograms {
    private final UltraChest ultraChest;
    Hologram hologram;
    private String locString;

    public UChestHologramDecentHolograms(UltraChest ultraChest) {
        this.ultraChest = ultraChest;
        if (DHAPI.getHologram(convertLocationToString()) == null) createDefaultHologram();
        else hologram = DHAPI.getHologram(convertLocationToString());
    }

    public String convertLocationToString() {
        if (this.locString != null) return this.locString;
        Location location = ultraChest.getLocation().clone();
        String sLoc =
                location.getWorld().getName() + "_" + location.getBlockX() + "_" + location.getBlockY() + "_" + location.getBlockZ();
        sLoc = sLoc.replace(".", "_");
        sLoc = sLoc.replace(",", "_");
        return sLoc;
    }

    public boolean isHologramDeleted() {
        return false;
    }

    public Hologram getHologram() {
        return DHAPI.getHologram(convertLocationToString());
    }

    public void removeHologram() {
        if (DHAPI.getHologram(convertLocationToString()) != null)
            DHAPI.removeHologram(convertLocationToString());
    }

    public void createDefaultHologram() {
        Map<String, String> map = getPlaceholders(ultraChest);
        List<String> hologramMainText =
                GuiConfig.getStringList(ConfigPath.ULTRA_STORAGE_HOLOGRAM_MAIN_PAGE);
        Hologram hologram =
                DHAPI.createHologram(convertLocationToString(), ultraChest.getLocation().clone().add(0.5D, 5D, 0.5D));
        for (String str : hologramMainText)
            DHAPI.addHologramLine(hologram, PlaceholderUtils.placeHolder(TextUtils.color(str), map, false));
        hologram.showAll();
    }

    public void updateHologram() {

        if (isHologramDeleted()) {
            removeHologram();
            return;
        }
        Map<String, String> map = getPlaceholders(ultraChest);
        int page = ultraChest.getHologramPage();
        List<String> list = new ArrayList<>();
        if (page == 0) {
            list = GuiConfig.getStringList(ConfigPath.ULTRA_STORAGE_HOLOGRAM_MAIN_PAGE);
        } else if (page == 1) {
            list =
                    GuiConfig.getStringList(ConfigPath.ULTRA_STORAGE_HOLOGRAM_SETTINGS_PAGE);
        } else if (page == 2) {
            list = GuiConfig.getStringList(ConfigPath.ULTRA_STORAGE_HOLOGRAM_INFO_PAGE);
        }

        Hologram hologram = getHologram();
        byte b = 0;
        for (String str : list) {
            DHAPI.setHologramLine(hologram, b, PlaceholderUtils.placeHolder(TextUtils.color(str), map, false));
            b++;
        }

    }
}
