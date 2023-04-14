package me.tomisanhues2.ultrastorage.utils;

import com.google.common.collect.Maps;
import com.jeff_media.jefflib.TextUtils;
import me.tomisanhues2.ultrastorage.data.UltraChest;
import org.apache.commons.lang3.Validate;
import org.bukkit.Material;

import java.util.*;

public class PlaceholderUtils {
    public static String placeHolder(String paramString, Map<String, String> paramMap, boolean paramBoolean) {
        Validate.notNull(paramString, "The string can't be null!");
        if (paramMap == null) return paramString;
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            paramString = paramBoolean
                          ? replaceIgnoreCase(paramString, entry.getKey(), entry.getValue())
                          : paramString.replace(entry.getKey(), entry.getValue());
        }
        return TextUtils.color(paramString);
    }

    public static Map<String, String> getPlaceholders(UltraChest ultraChest) {
        return getItemPlaceholders(ultraChest, null);
    }

    public static Map<String, String> getItemPlaceholders(UltraChest ultraChest, Material material) {
        HashMap<String, String> placeholders = Maps.newHashMap();
        if (material != null) {
            placeholders.put("%item%", material.name());
            placeholders.put("%item_amount%", ultraChest.getItemAmountPlaceholder(material));
        }
        placeholders.put("%owner%", ultraChest.getOwnerPlaceholder());
        placeholders.put("%time_left%", ultraChest.getSecondsLeftPlaceholder());
        placeholders.put("%is_item_in_chest%",
                ultraChest.isMaterialInList(material) ? "&aYes" : "&4No");
        placeholders.put("%auto_sell_enabled%",
                ultraChest.isAutoSellEnabled() ? "&aActive" : "&4Off");
        placeholders.put("%block_collection%",
                ultraChest.isBlockCollectEnabled() ? "&aActive" : "&4Off");
        placeholders.put("%mob_collection%",
                ultraChest.isMobCollectByKillingEnabled() ? "&aActive" : "&4Off");
        placeholders.put("%mob_other_collection%",
                ultraChest.isMobCollectOtherEnabled() ? "&aActive" : "&4Off");
        placeholders.put("%furnace_collection%",
                ultraChest.isFurnaceCollectEnabled() ? "&aActive" : "&4Off");
        placeholders.put("%crop_collection%",
                ultraChest.isCropCollectEnabled() ? "&aActive" : "&4Off");


        placeholders.put("%slot_count%", ultraChest.getUsedSlotsPlaceholder());
        placeholders.put("%storage_count%", ultraChest.getTotalItemAmountPlaceholder());
        placeholders.put("%user_count%", ultraChest.getUsedUsersPlaceholder());

        //Upgrade placeholders

        return placeholders;
    }

    public static List<String> replacePlaceholders(List<String> list, UltraChest ultraChest) {
        return replacePlaceholders(list, ultraChest, null);
    }

    public static List<String> replaceShopPlaceholders(List<String> list, double price) {
        list.replaceAll(text -> text.replace("%price%", String.valueOf(price)));
        return list;
    }

    public static List<String> replaceMemberPlaceholders(List<String> list, UltraChest ultraChest, UUID uuid) {
        list.replaceAll(text -> text.replace("%is_member_in_chest%",
                ultraChest.getAllowedPlayers().contains(uuid) ? "§aYes" : "§cNo"));
        list.forEach(text -> TextUtils.color(TextUtils.color(text)));
        return list;
    }

    public static List<String> replacePlaceholders(List<String> list, UltraChest ultraChest, Material material) {
        Map<String, String> map = getItemPlaceholders(ultraChest, material);
        list.replaceAll(text -> PlaceholderUtils.placeHolder(TextUtils.color(text), map, false));
        return list;
    }

    private static String replaceIgnoreCase(String paramString1, String paramString2, String paramString3) {
        if (paramString1 == null || paramString1.length() == 0) return paramString1;
        if (paramString2 == null || paramString2.length() == 0) return paramString1;
        if (paramString3 == null) return paramString1;
        byte b = -1;
        String str = paramString1.toLowerCase();
        paramString2 = paramString2.toLowerCase();
        int i = 0;
        int j = str.indexOf(paramString2, i);
        if (j == -1) return paramString1;
        int k = paramString2.length();
        int m = paramString3.length() - k;
        m = Math.max(m, 0);
        m *= 16;
        StringBuilder stringBuilder = new StringBuilder(paramString1.length() + m);
        while (j != -1) {
            stringBuilder.append(paramString1, i, j).append(paramString3);
            i = j + k;
            if (--b == 0) break;
            j = str.indexOf(paramString2, i);
        }
        return stringBuilder.append(paramString1, i, paramString1.length()).toString();
    }
}

class RomanUtils {
    public static String RomanNumerals(int Int) {
        LinkedHashMap<String, Integer> roman_numerals =
                new LinkedHashMap<String, Integer>();
        roman_numerals.put("M", 1000);
        roman_numerals.put("CM", 900);
        roman_numerals.put("D", 500);
        roman_numerals.put("CD", 400);
        roman_numerals.put("C", 100);
        roman_numerals.put("XC", 90);
        roman_numerals.put("L", 50);
        roman_numerals.put("XL", 40);
        roman_numerals.put("X", 10);
        roman_numerals.put("IX", 9);
        roman_numerals.put("V", 5);
        roman_numerals.put("IV", 4);
        roman_numerals.put("I", 1);
        StringBuilder res = new StringBuilder();
        for (Map.Entry<String, Integer> entry : roman_numerals.entrySet()) {
            int matches = Int / entry.getValue();
            res.append(repeat(entry.getKey(), matches));
            Int = Int % entry.getValue();
        }
        return res.toString();
    }

    public static String repeat(String s, int n) {
        if (s == null) {
            return null;
        }
        return s.repeat(Math.max(0, n));
    }
}

class Beautify {
    public static String doubleString(double d) {
        //Round this to 1 deical place and return as a string
        return String.format("%.1f", d);
    }

    public static String intString(int i) {
        //Return this as a string
        return String.valueOf(i);
    }

    public static String intString(double d) {
        //Return this as a string without any decimals
        return String.valueOf((int) d);
    }
}

