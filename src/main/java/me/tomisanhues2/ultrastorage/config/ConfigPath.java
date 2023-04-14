package me.tomisanhues2.ultrastorage.config;

public enum ConfigPath {
    ULTRA_STORAGE_HOLOGRAM_MAIN_PAGE("ultrastorage-hologram-main-page"),
    ULTRA_STORAGE_HOLOGRAM_SETTINGS_PAGE("ultrastorage-hologram-settings-page"),
    ULTRA_STORAGE_HOLOGRAM_INFO_PAGE("ultrastorage-hologram-info-page"),
    ULTRA_STORAGE_PHYSICAL_CHEST_ITEM_NAME("ultrastorage-physical-chest-item-name"),
    ULTRA_STORAGE_PHYSICAL_CHEST_ITEM_LORE("ultrastorage-physical-chest-item-lore"),
    GUI_MAIN_MENU_INFO_ITEM("gui-main-menu-info-item"),
    GUI_MAIN_MENU_UPGRADE_SLOTS_ITEM("gui-main-menu-upgrade-slots-item"),
    GUI_MAIN_MENU_UPGRADE_STORAGE_ITEM("gui-main-menu-upgrade-storage-item"),
    GUI_MAIN_MENU_UPGRADE_SPEED_ITEM("gui-main-menu-upgrade-speed-item"),
    GUI_MAIN_MENU_UPGRADE_MULTIPLIER_ITEM("gui-main-menu-upgrade-multiplier-item"),
    GUI_MAIN_MENU_UPGRADE_MEMBERS_ITEM("gui-main-menu-upgrade-members-item"),
    GUI_MAIN_MENU_UPGRADE_SELL_QUANTITY_ITEM("gui-main-menu-upgrade-sell-quantity-item"),
    GUI_MAIN_MENU_ITEM_DEFAULT_ITEM("gui-main-menu-item-default-item"),
    GUI_MAIN_MENU_SETTINGS_ITEM("gui-main-menu-settings-item"),
    GUI_ITEM_FILTER_ALLOWED_ITEMS("gui-item-filter-allowed-items"),
    GUI_ITEM_FILTER_ITEM("gui-item-filter-item"),
    GUI_MEMBER_FILTER_ITEM("gui-member-filter-item"),
    GUI_TOGGLE_BLOCKS_ITEM("gui-toggle-blocks"),
    GUI_TOGGLE_MOB_KILL_ITEM("gui-toggle-mob-kill"),
    GUI_TOGGLE_MOB_OTHER_ITEM("gui-toggle-mob-other"),
    GUI_TOGGLE_FURNACE_ITEM("gui-toggle-furnace"),
    GUI_TOGGLE_CROP_ITEM("gui-toggle-crops"),
    GUI_TOGGLE_INFO_ITEM("gui-toggle-info"),
    GUI_TOGGLE_AUTO_SELL_ITEM("gui-toggle-autosell"),
    GUI_SETTINGS_MEMBERS_ITEM("gui-settings-members"),
    GUI_SETTINGS_COLLECTIONS_ITEM("gui-settings-collections"),
    GUI_SETTINGS_ITEMS_ITEM("gui-settings-items"),
    GUI_SETTINGS_PICKUP_ITEM("gui-settings-pickup"),
    //gui specific upgrade items
    GUI_UPGRADES_SLOT_ITEM("gui-upgrades-slot-item"),
    GUI_UPGRADES_STORAGE_ITEM("gui-upgrades-storage-item"),
    GUI_UPGRADES_SPEED_ITEM("gui-upgrades-speed-item"),
    GUI_UPGRADES_MULTIPLIER_ITEM("gui-upgrades-multiplier-item"),
    GUI_UPGRADES_MEMBERS_ITEM("gui-upgrades-members-item"),
    GUI_UPGRADES_SELL_QUANTITY_ITEM("gui-upgrades-sell-quantity-item"),
    //HOLOGRAM SHOP SECTION
    ULTRA_CHEST_HOLOGRAM_SHOP("ultrachest-hologram-shop"),

    //UPGRADE TIER SECTION

    GUI_UPGRADES_SLOT_TIER_CONFIG("gui-upgrades-slot-tier-config"),
    GUI_UPGRADES_STORAGE_TIER_CONFIG("gui-upgrades-storage-tier-config"),
    GUI_UPGRADES_SPEED_TIER_CONFIG("gui-upgrades-speed-tier-config"),
    GUI_UPGRADES_MULTIPLIER_TIER_CONFIG("gui-upgrades-multiplier-tier-config"),
    GUI_UPGRADES_MEMBERS_TIER_CONFIG("gui-upgrades-members-tier-config"),
    GUI_UPGRADES_SELL_QUANTITY_TIER_CONFIG("gui-upgrades-sell-quantity-tier-config");
    private final String path;

    ConfigPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
