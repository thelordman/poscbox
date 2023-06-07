package me.lord.poscbox.commands;

import me.lord.poscbox.utilities.Cmd;
import me.lord.poscbox.utilities.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockShopCommand implements Cmd {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            Inventory inventory = Bukkit.createInventory(player, 18, TextUtil.cs("&8Block Shop &r$25&8/&r5 Wool"));
            inventory.setItem(0, new ItemStack(Material.RED_WOOL));
            inventory.setItem(1, new ItemStack(Material.ORANGE_WOOL));
            inventory.setItem(2, new ItemStack(Material.YELLOW_WOOL));
            inventory.setItem(3, new ItemStack(Material.LIME_WOOL));
            inventory.setItem(4, new ItemStack(Material.GREEN_WOOL));
            inventory.setItem(5, new ItemStack(Material.CYAN_WOOL));
            inventory.setItem(6, new ItemStack(Material.BLUE_WOOL));
            inventory.setItem(7, new ItemStack(Material.LIGHT_BLUE_WOOL));
            inventory.setItem(8, new ItemStack(Material.PURPLE_WOOL));
            inventory.setItem(9, new ItemStack(Material.MAGENTA_WOOL));
            inventory.setItem(10, new ItemStack(Material.PINK_WOOL));
            inventory.setItem(13, new ItemStack(Material.BROWN_WOOL));
            inventory.setItem(14, new ItemStack(Material.WHITE_WOOL));
            inventory.setItem(15, new ItemStack(Material.LIGHT_GRAY_WOOL));
            inventory.setItem(16, new ItemStack(Material.GRAY_WOOL));
            inventory.setItem(17, new ItemStack(Material.BLACK_WOOL));
            for (int i = 0; i < inventory.getContents().length; i++) {
                if (inventory.getContents()[i] != null)
                    inventory.getContents()[i].setLore(List.of(TextUtil.cs("&eLeft-Click &6Buy 10 Wool"), TextUtil.cs("&eRight-Click &6Buy 25 Wool"), TextUtil.cs("&eShift-Click &6Buy 50 Wool")));
            }
            /*
             *   00 01 02 03 04 05 06 07 08
             *   09 10 11 12 13 14 15 16 17
             *   18 19 20 21 22 23 24 25 26
             */
            player.openInventory(inventory);
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }

    @Override
    public String name() {
        return "shop";
    }
}
