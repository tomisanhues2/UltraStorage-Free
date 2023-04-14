package me.tomisanhues2.ultrastorage.utils;

import me.tomisanhues2.ultrastorage.UltraStorage;

public enum Cases {
    ITEM_ALREADY_IN_CHEST("item_already_in_chest"),
    ITEM_ADDED_TO_CHEST("item_added_to_chest"),
    ITEM_REMOVED_FROM_CHEST("item_removed_from_chest"),
    ITEM_NOT_ENOUGH_IN_CHEST("item_not_enough_in_chest"),
    ITEM_WITHDRAWN_FROM_CHEST("item_withdrawn_from_chest"),
    MAX_SLOTS_REACHED("max_slots_reached"),
    MAX_STORAGE_REACHED("max_storage_reached"),
    ITEM_NOT_IN_CHEST("item_not_in_chest"),
    UPGRADE_SUCCESS("upgrade_success"),
    UPGRADE_MAX_TIER("upgrade_max_tier"),
    ITEM_HAS_ITEMS_IN_CHEST("item_has_items_in_chest"),
    UPGRADE_SLOT_NO_PERMISSION_UPGRADE("upgrade_slot_no_permission_upgrade"),
    UPGRADE_SLOT_NOT_ENOUGH_MONEY("upgrade_slot_not_enough_money"),
    UPGRADE_STORAGE_NO_PERMISSION_UPGRADE("upgrade_storage_no_permission_upgrade"),
    UPGRADE_STORAGE_NOT_ENOUGH_MONEY("upgrade_storage_not_enough_money"),
    UPGRADE_SPEED_NO_PERMISSION_UPGRADE("upgrade_speed_no_permission_upgrade"),
    UPGRADE_SPEED_NOT_ENOUGH_MONEY("upgrade_speed_not_enough_money"),
    UPGRADE_MULTIPLIER_NO_PERMISSION_UPGRADE("upgrade_multiplier_no_permission_upgrade"),
    UPGRADE_MULTIPLIER_NOT_ENOUGH_MONEY("upgrade_multiplier_not_enough_money"),
    UPGRADE_MEMBERS_NO_PERMISSION_UPGRADE("upgrade_members_no_permission_upgrade"),
    UPGRADE_MEMBERS_NOT_ENOUGH_MONEY("upgrade_members_not_enough_money"),
    UPGRADE_SELL_QUANTITY_NO_PERMISSION_UPGRADE("upgrade_sell_quantity_no_permission_upgrade"),
    UPGRADE_SELL_QUANTITY_NOT_ENOUGH_MONEY("upgrade_sell_quantity_not_enough_money"),
    MSG_ULTRACHEST_NOT_ALLOWED_TO_OPEN("msg_ultrachest_not_allowed_to_open"),
    MSG_ULTRACHEST_NO_PLACE_PERMISSION("msg_ultrachest_no_place_permission"),
    MSG_ULTRACHEST_ALREADY_PLACED("msg_ultrachest_already_placed"),
    MSG_ULTRACHEST_CREATED("msg_ultrachest_created"),
    MSG_ULTRACHEST_OPENED("msg_ultrachest_opened"),
    MSG_ULTRACHEST_REMOVED("msg_ultrachest_removed"),
    MSG_ULTRACHEST_NO_PERMISSION("msg_ultrachest_no_permission"),
    MSG_ULTRACHEST_NO_BREAK("msg_ultrachest_no_break"),
    MSG_CREATE_SHOP_ALREADY_EXISTS("msg_create_shop_already_exists"),
    MSG_NOT_ENOUGH_MONEY("msg_not_enough_money"),
    MSG_BOUGHT_ITEM("msg_bought_item"),
    MSG_CHEST_ITEM_NULL("msg_chest_item_null"),
    MSG_CREATE_SHOP_BLOCK_DOES_NOT_EXIST("msg_create_shop_block_does_not_exist"),
    MSG_CREATE_SHOP_SUCCESSFUL("msg_create_shop_successful"),
    MSG_SHOP_BLOCK_DOES_NOT_EXIST("msg_shop_block_does_not_exist"),
    MSG_REMOVE_SHOP_SUCCESSFUL("msg_remove_shop_successful"),
    MSG_TOGGLE_AUTOSELL("msg_toggle_autosell"),
    MSG_TOGGLE_BLOCKS("msg_toggle_block_collection"),
    MSG_TOGGLE_MOB_KILL("msg_toggle_mob_kill_collection"),
    MSG_TOGGLE_INFO("msg_toggle_info_collection"),
    MSG_TOGGLE_MOB_OTHER("msg_toggle_mob_other_collection"),
    MSG_TOGGLE_FURNACE("msg_toggle_furnace_collection"),
    MSG_TOGGLE_CROP("msg_toggle_crop_collection"),
    MSG_RELOAD_ERROR("msg_reload_error"),
    MSG_RELOAD_SUCCESSFUL("msg_reload_successful"),
    MSG_UPGRADE_MAX_TIER("msg_upgrade_max_tier"),
    MSG_PLAYER_ALREADY_IN_CHEST("msg_player_already_in_chest"),
    MSG_PLAYER_ADDED_TO_CHEST("msg_player_added_to_chest"),
    MSG_PLAYER_REMOVED_FROM_CHEST("msg_player_removed_from_chest"),
    MSG_PLAYER_NOT_IN_CHEST("msg_player_not_in_chest"),
    MSG_MAX_MEMBERS_REACHED("msg_max_members_reached");
    private final String path;

    Cases(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
