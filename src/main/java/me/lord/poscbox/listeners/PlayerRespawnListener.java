package me.lord.poscbox.listeners;

import me.lord.poscbox.data.DataManager;
import me.lord.poscbox.item.ItemManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		if (event.getRespawnReason() == PlayerRespawnEvent.RespawnReason.DEATH) {
			if (DataManager.getPlayerData(player).droppedEquipment()) {
				ItemManager.joinKit(player);
				DataManager.getPlayerData(player).setDroppedEquipment(false);
			}
		}
	}
}
