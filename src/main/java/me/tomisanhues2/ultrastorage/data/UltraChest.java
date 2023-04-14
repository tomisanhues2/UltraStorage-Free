package me.tomisanhues2.ultrastorage.data;

import me.tomisanhues2.ultrastorage.UltraStorage;
import me.tomisanhues2.ultrastorage.mechanics.UChestHologramDecentHolograms;
import me.tomisanhues2.ultrastorage.utils.Cases;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public final class UltraChest implements ConfigurationSerializable {
    private final List<UUID> allowedPlayers;
    //Storage variables
    private final UltraChestUpgrades upgrades;
    private final Map<Material, Integer> items;
    private final int secondsLeft;
    private final boolean hasHologram;
    public UUID ultraChestUUID;
    //General variables
    private Location location;
    private UUID owner;
    private int totalItems = 0;
    //Hologram variables
    private UChestHologramDecentHolograms hologram;
    private int hologramPage;
    //Boolean variables
    private boolean isAutoSellEnabled;
    private boolean isBlockCollectEnabled;
    private boolean isMobCollectByKillingEnabled;
    private boolean isMobCollectOtherEnabled;
    private boolean isFurnaceCollectEnabled;
    private boolean isCropCollectEnabled;

    public UltraChest() {
        this.ultraChestUUID = UUID.randomUUID();
        this.secondsLeft = 120;
        this.hologramPage = 0;
        this.hasHologram = false;
        this.isAutoSellEnabled = false;
        this.isBlockCollectEnabled = false;
        this.isMobCollectByKillingEnabled = false;
        this.isMobCollectOtherEnabled = false;
        this.isFurnaceCollectEnabled = false;
        this.isCropCollectEnabled = false;
        this.items = new HashMap<>();
        this.allowedPlayers = new ArrayList<>();
        this.upgrades = new UltraChestUpgrades();
    }

    public UltraChest(UUID ultraChestUUID, Location location, UUID owner, List<UUID> allowedPlayers, UltraChestUpgrades upgrades, int secondsLeft, Map<Material, Integer> items, UChestHologramDecentHolograms hologram, boolean hasHologram, boolean isAutoSellEnabled, boolean isBlockCollectEnabled, boolean isMobCollectByKillingEnabled, boolean isMobCollectOtherEnabled, boolean isFurnaceCollectEnabled, boolean isCropCollectEnabled) {
        this.ultraChestUUID = ultraChestUUID;
        this.upgrades = upgrades;
        this.location = location;
        this.owner = owner;
        this.allowedPlayers = allowedPlayers;
        this.secondsLeft = secondsLeft;
        this.items = items;
        this.hasHologram = hasHologram;
        this.isAutoSellEnabled = isAutoSellEnabled;
        this.isBlockCollectEnabled = isBlockCollectEnabled;
        this.isMobCollectByKillingEnabled = isMobCollectByKillingEnabled;
        this.isMobCollectOtherEnabled = isMobCollectOtherEnabled;
        this.isFurnaceCollectEnabled = isFurnaceCollectEnabled;
        this.isCropCollectEnabled = isCropCollectEnabled;
        if (hasHologram) {
            this.hologram = hologram;
        } else {
            hasHologram = true;
        }
    }

    public static UltraChest deserialize(Map<String, Object> map) {
        UUID ultraChestUUID = UUID.fromString((String) map.get("ultraChestUUID"));
        Location location = (Location) map.get("Location");
        UUID owner = UUID.fromString((String) map.get("owner"));
        List<UUID> allowedPlayers =
                ((List<String>) map.get("allowedPlayers")).stream().map(UUID::fromString).collect(Collectors.toList());
        Map<String, Integer> itemString = (Map<String, Integer>) map.get("items");
        Map<Material, Integer> items = new HashMap<>();
        for (Map.Entry<String, Integer> entry : itemString.entrySet()) {
            items.put(Material.valueOf(entry.getKey()), entry.getValue());
        }
        UltraChestUpgrades upgrades = (UltraChestUpgrades) map.get("upgrades");
        int secondsLeft = (int) map.get("secondsLeft");
        boolean hasHologram = (boolean) map.get("hasHologram");
        boolean isAutoSellEnabled = (boolean) map.get("autoSell");
        boolean isBlockCollectEnabled = (boolean) map.get("isBlockCollectEnabled");
        boolean isMobCollectByKillingEnabled =
                (boolean) map.get("isMobCollectByKillingEnabled");
        boolean isMobCollectOtherEnabled = (boolean) map.get("isMobCollectOtherEnabled");
        boolean isFurnaceCollectEnabled = (boolean) map.get("isFurnaceCollectEnabled");
        boolean isCropCollectEnabled = (boolean) map.get("isCropCollectEnabled");
        UChestHologramDecentHolograms hologram = null;
        if (hasHologram) {
            hologram = (UChestHologramDecentHolograms) map.get("hologram");
        } else {
            hasHologram = false;
        }
        return new UltraChest(ultraChestUUID, location, owner, allowedPlayers, upgrades, secondsLeft, items, hologram, hasHologram, isAutoSellEnabled, isBlockCollectEnabled, isMobCollectByKillingEnabled, isMobCollectOtherEnabled, isFurnaceCollectEnabled, isCropCollectEnabled);
    }

    //Serialization
    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("ultraChestUUID", ultraChestUUID.toString());
        map.put("Location", location);
        map.put("owner", owner.toString());
        map.put("allowedPlayers", allowedPlayers.stream().map(UUID::toString).collect(Collectors.toList()));
        Map<String, Integer> itemString = new HashMap<>();
        for (Map.Entry<Material, Integer> entry : this.items.entrySet()) {
            itemString.put(entry.getKey().name(), entry.getValue());
        }
        map.put("items", itemString);
        map.put("upgrades", upgrades);
        map.put("autoSell", isAutoSellEnabled);
        map.put("secondsLeft", secondsLeft);
        map.put("hasHologram", hasHologram);
        map.put("isBlockCollectEnabled", isBlockCollectEnabled);
        map.put("isMobCollectByKillingEnabled", isMobCollectByKillingEnabled);
        map.put("isMobCollectOtherEnabled", isMobCollectOtherEnabled);
        map.put("isFurnaceCollectEnabled", isFurnaceCollectEnabled);
        map.put("isCropCollectEnabled", isCropCollectEnabled);
        if (hasHologram) {
            map.put("hologram", hologram);
        }
        return map;
    }

    //Getters
    public String getUsedUsersPlaceholder() {
        return String.valueOf(allowedPlayers.size() + 1);
    }

    public String getOwnerPlaceholder() {
        Player player =
                UltraStorage.getInstance().getServer().getOfflinePlayer(owner).getPlayer();
        if (player != null) {
            return player.getName();
        }
        return "Unknown";
    }

    public String getItemAmountPlaceholder(Material material) {
        if (items.containsKey(material)) {
            return items.get(material).toString();
        }
        return "0";
    }

    public String getUsedSlotsPlaceholder() {
        return String.valueOf(items.size());
    }

    public UChestHologramDecentHolograms getHologram() {
        if (hologram == null) {
            hologram = new UChestHologramDecentHolograms(this);
        }
        return hologram;
    }

    public String getTotalItemAmountPlaceholder() {
        return String.valueOf(getTotalItemAmount());
    }

    private void getTotalItems() {
        int total = 0;
        for (int i : items.values()) {
            total += i;
        }
        totalItems = total;
    }

    public String getSecondsLeftPlaceholder() {
        return String.valueOf(secondsLeft);
    }

    public UUID getOwner() {
        return owner;
    }

    //Setters
    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public List<UUID> getAllowedPlayers() {
        return allowedPlayers;
    }

    public Map<Material, Integer> getItems() {
        return items;
    }

    public int getTotalItemAmount() {
        getTotalItems();
        return totalItems;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getHologramPage() {
        return hologramPage;
    }

    //Boolean Getters
    public boolean isMaterialInList(Material material) {
        return items.containsKey(material);
    }

    public boolean isItemStackInList(ItemStack itemStack) {
        return items.containsKey(itemStack.getType());
    }

    public boolean isAutoSellEnabled() {
        return isAutoSellEnabled;
    }

    public boolean isBlockCollectEnabled() {
        return isBlockCollectEnabled;
    }

    public boolean isMobCollectByKillingEnabled() {
        return isMobCollectByKillingEnabled;
    }

    public boolean isMobCollectOtherEnabled() {
        return isMobCollectOtherEnabled;
    }

    public boolean isFurnaceCollectEnabled() {
        return isFurnaceCollectEnabled;
    }

    public boolean isCropCollectEnabled() {
        return isCropCollectEnabled;
    }

    //Functions
    public Cases addItemSlot(Material material) {
        if (items.size() < 1) {
            if (items.containsKey(material)) {
                return Cases.ITEM_ALREADY_IN_CHEST;
            }
            items.put(material, 0);
            return Cases.ITEM_ADDED_TO_CHEST;
        }
        return Cases.MAX_SLOTS_REACHED;
    }

    public Cases withdrawItem(Material material, int amount) {
        if (items.containsKey(material)) {
            if (items.get(material) < amount) {
                return Cases.ITEM_NOT_ENOUGH_IN_CHEST;
            }
            items.put(material, items.get(material) - amount);
            return Cases.ITEM_WITHDRAWN_FROM_CHEST;
        }
        return Cases.ITEM_NOT_IN_CHEST;
    }

    public Cases removeItemSlot(Material material) {
        if (items.containsKey(material)) {
            if (items.get(material) > 1) {
                return Cases.ITEM_HAS_ITEMS_IN_CHEST;
            }
            items.remove(material);
            return Cases.ITEM_REMOVED_FROM_CHEST;
        }
        return Cases.ITEM_NOT_IN_CHEST;
    }

    public Cases addMember(UUID uuid) {
        if (allowedPlayers.contains(uuid)) {
            return Cases.MSG_PLAYER_ALREADY_IN_CHEST;
        }
        if (allowedPlayers.size() >= 1) {
            return Cases.MSG_MAX_MEMBERS_REACHED;
        }
        allowedPlayers.add(uuid);
        return Cases.MSG_PLAYER_ADDED_TO_CHEST;
    }

    public Cases removeMember(UUID uuid) {
        if (allowedPlayers.contains(uuid)) {
            allowedPlayers.remove(uuid);
            return Cases.MSG_PLAYER_REMOVED_FROM_CHEST;
        }
        return Cases.MSG_PLAYER_NOT_IN_CHEST;
    }

    public boolean addMaterialCount(Material material, int amount) {
        if (items.containsKey(material)) {
            int currentAmount = totalItems;
            if (currentAmount + amount > 10000) {
                return false;
            }
            items.put(material, currentAmount + amount);
            return true;
        }
        return false;
    }

    public void incrementPage() {
        if (hologramPage == 2) {
            hologramPage = 0;
        } else {
            hologramPage++;
        }
    }

    public void toggleBlockCollect() {
        isBlockCollectEnabled = !isBlockCollectEnabled;
    }

    public void toggleMobCollectByKilling() {
        isMobCollectByKillingEnabled = !isMobCollectByKillingEnabled;
    }

    public void toggleMobCollectOther() {
        isMobCollectOtherEnabled = !isMobCollectOtherEnabled;
    }

    public void toggleFurnaceCollect() {
        isFurnaceCollectEnabled = !isFurnaceCollectEnabled;
    }

    public void toggleCropCollect() {
        isCropCollectEnabled = !isCropCollectEnabled;
    }

    public void toggleAutoSell() {
        isAutoSellEnabled = !isAutoSellEnabled;
    }


}
