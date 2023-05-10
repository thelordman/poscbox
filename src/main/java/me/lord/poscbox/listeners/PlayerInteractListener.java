package me.lord.poscbox.listeners;

import me.lord.poscbox.PoscBox;
import me.lord.poscbox.item.ItemManager;
import me.lord.poscbox.item.data.ItemData;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction().isRightClick()) {
			ItemStack item = event.getItem();
			if (item != null) {
				ItemData data = ItemManager.getData(item);
				if (data != null) {
					if (item.getType().name().contains("BOOTS") | item.getType().name().contains("LEGGINGS") | item.getType().name().contains("CHESTPLATE") | item.getType().name().contains("HELMET")) {
						event.setCancelled(true);
					}

					if (item.getType() == Material.BOW || item.getType() == Material.CROSSBOW) {
						if (event.getPlayer().isSneaking()) {
							data.onRightClick(event.getPlayer());
						}
					} else {
						data.onRightClick(event.getPlayer());
					}
				}
			}
		}
	}
}
