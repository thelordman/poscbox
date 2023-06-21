package me.lord.poscbox.listeners;

import me.lord.poscbox.data.DataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().contains("Shop")) {
            event.setCancelled(true);
            if (event.getInventory().getItem(event.getSlot()) != null && event.getViewers().get(0).getInventory().firstEmpty() != -1) {
                if (event.getClick().isLeftClick() && !event.getClick().isShiftClick()) {
                    event.getViewers().get(0).getInventory().addItem(new ItemStack(event.getInventory().getItem(event.getSlot()).getType(), 10));
                } else if (event.getClick().isRightClick() && !event.getClick().isShiftClick()) {
                    event.getViewers().get(0).getInventory().addItem(new ItemStack(event.getInventory().getItem(event.getSlot()).getType(), 25));
                } else if (event.getClick().isShiftClick()) {
                    event.getViewers().get(0).getInventory().addItem(new ItemStack(event.getInventory().getItem(event.getSlot()).getType(), 50));
                }
            }
        }

        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().contains("Enchanter") || event.getView().getTitle().contains("Upgrader")) {
            DataManager.getPlayerData(player).getCurrentGUI().onInventoryClick(event);
        }
    }
}
