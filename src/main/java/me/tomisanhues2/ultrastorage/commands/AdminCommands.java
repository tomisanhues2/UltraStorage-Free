package me.tomisanhues2.ultrastorage.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import com.jeff_media.jefflib.PDCUtils;
import eu.decentsoftware.holograms.api.DHAPI;
import me.tomisanhues2.ultrastorage.UltraStorage;
import me.tomisanhues2.ultrastorage.config.Config;
import me.tomisanhues2.ultrastorage.config.ConfigPath;
import me.tomisanhues2.ultrastorage.config.GuiConfig;
import me.tomisanhues2.ultrastorage.nbt.NBTTags;
import me.tomisanhues2.ultrastorage.utils.Cases;
import me.tomisanhues2.ultrastorage.utils.PlaceholderUtils;
import me.tomisanhues2.ultrastorage.utils.UltraChestUtils;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Objects;

@CommandAlias("ultrastorageadmin|usa")
@CommandPermission("ultrastorage.admin")
public class AdminCommands extends BaseCommand {
    final static UltraStorage plugin = UltraStorage.getInstance();

    @Default
    @CatchUnknown
    @Subcommand("help")
    public static void onHelp(CommandSender sender) {
        //TODO: Add help message
    }

    @Subcommand("removechunkdata")
    @Description("Removes the UltraChest data from the chunk you are standing in, if there is any.")
    public static void onRemoveChunkData(Player sender) {
        if (PDCUtils.has(sender.getLocation().getChunk(), NBTTags.ULTRA_CHEST_LOCATION_KEY)) {
            UltraChestUtils.removeStorage(sender.getLocation().getChunk());
            sender.sendMessage("Removed chunk data");
        } else {
            sender.sendMessage("No chunk data found");
        }
    }

    @Subcommand("reload")
    public static void onReload(Player sender) {
        try {
            plugin.reload();
            Config.send(sender, Cases.MSG_RELOAD_SUCCESSFUL);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("shop create")
    @Syntax("<price>")
    @Description("Creates a shop with the specified price")
    public static void onCreateShop(Player sender, Double price) {
        if ((sender.getTargetBlockExact(6) == null) || !(sender.getTargetBlockExact(6).getState() instanceof Chest)) {
            Config.send(sender, Cases.MSG_CREATE_SHOP_BLOCK_DOES_NOT_EXIST);
            return;
        }
        Chest chest =
                (Chest) Objects.requireNonNull(sender.getTargetBlockExact(6)).getState();
        if (PDCUtils.has(chest, NBTTags.SHOP_KEY)) {
            Config.send(sender, Cases.MSG_CREATE_SHOP_ALREADY_EXISTS);
            return;
        }

        List<String> hologramText =
                GuiConfig.getStringList(ConfigPath.ULTRA_CHEST_HOLOGRAM_SHOP);
        DHAPI.createHologram(locationToString(chest.getLocation()), chest.getLocation().clone().add(0.5D, 3D, 0.5D), true, PlaceholderUtils.replaceShopPlaceholders(hologramText, price));

        PDCUtils.set(chest, NBTTags.SHOP_KEY, PersistentDataType.DOUBLE, price);
        chest.update();
        Config.send(sender, Cases.MSG_CREATE_SHOP_SUCCESSFUL);
    }

    @Subcommand("shop remove")
    @Syntax("")
    @Description("Deletes a shop from the block you are looking at")
    public static void onRemoveShop(Player sender) {
        if ((sender.getTargetBlockExact(6) == null) || !(sender.getTargetBlockExact(6).getState() instanceof Chest)) {
            Config.send(sender, Cases.MSG_SHOP_BLOCK_DOES_NOT_EXIST);
            return;
        }
        Chest chest =
                (Chest) Objects.requireNonNull(sender.getTargetBlockExact(6)).getState();
        PDCUtils.remove(chest, NBTTags.SHOP_KEY);
        chest.update();
        DHAPI.removeHologram(locationToString(chest.getLocation()));
        Config.send(sender, Cases.MSG_REMOVE_SHOP_SUCCESSFUL);
    }

    @Subcommand("giveitem")
    @Syntax("<player> <amount>")
    @Description("Gives a specified player a number of chest items")
    @CommandCompletion("@players @range:1-64")
    public static void onGiveItem(Player sender, @Optional OnlinePlayer playerName, @Optional @Default("1") Integer amount) {
        ItemStack item = UltraChestUtils.createChestItem();
        if (playerName == null) {
            playerName = new OnlinePlayer(sender);
            return;
        }
        if (item == null) {
            Config.send(sender, Cases.MSG_CHEST_ITEM_NULL);
            return;
        }
        item.setAmount(amount);
        Player player = playerName.getPlayer();
        player.getInventory().addItem(item);

        sender.sendMessage("Gave " + player.getName() + " " + amount + " chest items");
    }

    private static String locationToString(Location location) {
        String sLoc =
                "" + location.getWorld().getName() + "_" + location.getBlockX() + "_" + location.getBlockY() + "_" + location.getBlockZ();
        sLoc = sLoc.replace(".", "_");
        sLoc = sLoc.replace(",", "_");
        return sLoc;
    }

}
