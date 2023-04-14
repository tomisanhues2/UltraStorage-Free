package me.tomisanhues2.ultrastorage.gui.impl.main;

import com.jeff_media.jefflib.TextUtils;
import me.tomisanhues2.ultrastorage.UltraStorage;
import me.tomisanhues2.ultrastorage.config.Config;
import me.tomisanhues2.ultrastorage.config.ConfigPath;
import me.tomisanhues2.ultrastorage.config.GuiConfig;
import me.tomisanhues2.ultrastorage.data.UltraChest;
import me.tomisanhues2.ultrastorage.gui.abs.InventoryButton;
import me.tomisanhues2.ultrastorage.gui.abs.InventoryGUI;
import me.tomisanhues2.ultrastorage.gui.abs.InventoryItem;
import me.tomisanhues2.ultrastorage.utils.PlaceholderUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MembersMenuInventory extends InventoryGUI {
    private final UltraChest ultraChest;
    private final UltraStorage plugin = UltraStorage.getInstance();

    public MembersMenuInventory(UltraChest ultraChest) {
        super();
        this.ultraChest = ultraChest;
    }

    @Override
    protected Inventory createInventory() {
        return Bukkit.createInventory(null, 6 * 9, TextUtils.color("UltraChest Members"));
    }

    @Override
    public void decorate(Player player) {
        int inventorySize = this.getInventory().getSize();
        InventoryItem backgroundItem =
                new InventoryItem().creator(player1 -> createBackgroundItem());

        for (int i = 0; i < inventorySize; i++) {
            this.addItem(i, backgroundItem);
        }
        //Convert Bukkit.getOnlinePlayers() to a list
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (int i = 0; i < players.size(); i++) {
            UUID uuid = players.get(i).getUniqueId();
            if (ultraChest.getOwner().equals(uuid)) {
                continue;
            }
            InventoryButton button =
                    new InventoryButton().creator(player2 -> createMemberFilterItemStack(uuid)).consumer(event -> {
                        ClickType clickType = event.getClick();
                        switch (clickType) {
                            case LEFT -> {
                                Config.send(player, ultraChest.removeMember(uuid));
                            }
                            case RIGHT -> {
                                Config.send(player, ultraChest.addMember(uuid));
                            }
                        }
                    });
            this.addButton(i, button);
        }
        super.decorate(player);
    }

    private ItemStack createMemberFilterItemStack(UUID uuid) {
        return getButton(String.valueOf(uuid), TextUtils.color(Objects.requireNonNull(Bukkit.getPlayer(uuid).getDisplayName())), PlaceholderUtils.replaceMemberPlaceholders(GuiConfig.getStringListLore(ConfigPath.GUI_MEMBER_FILTER_ITEM), ultraChest, uuid));
    }
}
