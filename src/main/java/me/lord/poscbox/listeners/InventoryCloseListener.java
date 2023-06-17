package me.lord.poscbox.listeners;

import me.lord.poscbox.data.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.UUID;

public class InventoryCloseListener implements Listener {
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		DataManager.getPlayerData((Player) event.getPlayer()).setCurrentGUI(null);

		String title = event.getView().getTitle();

		if (title.contains("Vault")) {
			UUID target = event.getPlayer().getUniqueId();

			if (title.contains("'")) {
				target = Bukkit.getOfflinePlayer(title.substring(0, title.indexOf('\''))).getUniqueId();
			}

			DataManager.modifyPlayerData(target, data -> data.setVault(event.getInventory().getContents()));
		}
	}
}
