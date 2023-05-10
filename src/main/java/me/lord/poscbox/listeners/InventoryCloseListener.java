package me.lord.poscbox.listeners;

import me.lord.poscbox.data.DataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		DataManager.getPlayerData((Player) event.getPlayer()).setCurrentGUI(null);
	}
}
