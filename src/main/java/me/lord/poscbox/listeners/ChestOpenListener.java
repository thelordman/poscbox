package me.lord.poscbox.listeners;

import me.lord.poscbox.PoscBox;
import me.lord.poscbox.utilities.LootboxUtil;
import me.lord.poscbox.utilities.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ChestOpenListener implements Listener {

    @EventHandler
    public void onChestOpen(PlayerInteractEvent event) {
        if (event.getAction().isRightClick()) {
            if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.CHEST) {
                event.getClickedBlock().setType(Material.AIR);
                Bukkit.broadcast(TextUtil.c("&a&lLOOTBOX &7A loot box has been claimed! /buy to claim loot boxes!"));
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        LootboxUtil.loadBoxes();
                    }
                }.runTaskLater(PoscBox.get(), 15000);
            }
        }
    }
}
