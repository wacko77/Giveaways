package me.wacko.giveaways.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class ItemStackUtil {

    public static ItemStack getItem(String name, Material mat, int amount, Player player, List<String> lore){

        ItemStack i = new ItemStack(mat, amount);

        ItemMeta meta = i.getItemMeta();

        meta.setDisplayName(name);

        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

        meta.setLore(lore);

        if (mat == Material.PLAYER_HEAD){

            SkullMeta sMeta = (SkullMeta) meta;
            sMeta.setOwningPlayer(player);
        }

        i.setItemMeta(meta);

        return i;
    }

    public static ItemStack getItem(String name, Material mat, int amount, List<String> lore) {
        ItemStack i = new ItemStack(mat, amount);

        ItemMeta meta = i.getItemMeta();

        meta.setDisplayName(name);

        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

        meta.setLore(lore);

        i.setItemMeta(meta);

        return i;
    }

}
