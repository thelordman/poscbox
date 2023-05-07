package me.lord.poscbox.listeners;

import me.lord.poscbox.PoscBox;
import me.lord.poscbox.data.DataManager;
import me.lord.poscbox.discord.events.PlayerJoin;
import me.lord.poscbox.utilities.TextUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		PoscBox.onlinePlayers++;
		if (!event.getPlayer().hasPlayedBefore()) DataManager.getGlobal().incrementTotalUsers();

		DataManager.loadPlayerData(event.getPlayer());
		DataManager.getPlayerData(event.getPlayer()).getScoreboard().updateAll();

		event.joinMessage(TextUtil.c("&7[&a+&7] &f" + event.getPlayer().getName() + (event.getPlayer().hasPlayedBefore() ? "" : " &8| &6" + TextUtil.ordinal(DataManager.getGlobal().getTotalUsers()) + " join")));

		PlayerJoin.exe(event);
	}
}
