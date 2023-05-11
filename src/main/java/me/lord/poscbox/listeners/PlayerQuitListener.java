package me.lord.poscbox.listeners;

import me.lord.poscbox.PoscBox;
import me.lord.poscbox.data.DataManager;
import me.lord.poscbox.discord.events.PlayerQuit;
import me.lord.poscbox.utilities.PacketListener;
import me.lord.poscbox.utilities.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		PoscBox.onlinePlayers--;

		for (Player player : Bukkit.getOnlinePlayers()) {
			DataManager.getPlayerData(player).getScoreboard().updateTitle();
		}

		PacketListener.removePlayer(event.getPlayer());

		DataManager.savePlayerData(event.getPlayer());
		event.quitMessage(TextUtil.c("&7[&c-&7] &f" + event.getPlayer().getDisplayName()));

		PlayerQuit.exe(event);
	}
}
