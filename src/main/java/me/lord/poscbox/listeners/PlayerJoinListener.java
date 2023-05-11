package me.lord.poscbox.listeners;

import me.lord.poscbox.PoscBox;
import me.lord.poscbox.data.DataManager;
import me.lord.poscbox.discord.events.PlayerJoin;
import me.lord.poscbox.item.ItemManager;
import me.lord.poscbox.scoreboard.FastBoard;
import me.lord.poscbox.utilities.PacketListener;
import me.lord.poscbox.utilities.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		PoscBox.onlinePlayers++;

		PacketListener.injectPlayer(event.getPlayer());
		if (!event.getPlayer().hasPlayedBefore()) DataManager.getGlobal().incrementTotalUsers();
		if (!event.getPlayer().hasPlayedBefore()) {
			DataManager.getGlobal().incrementTotalUsers();

			ItemManager.joinKit(event.getPlayer());
		}

		DataManager.loadPlayerData(event.getPlayer());
		DataManager.getPlayerData(event.getPlayer()).getScoreboard().updateAll();

		for (Player player : Bukkit.getOnlinePlayers()) {
			DataManager.getPlayerData(player).getScoreboard().updateTitle();
		}

		if (DataManager.getPlayerData(event.getPlayer()).getRank() == null) {
			event.getPlayer().displayName(null);
		} else {
			event.getPlayer().displayName(TextUtil.c(DataManager.getPlayerData(event.getPlayer()).getRank().getDisplay() + " &8| &f" + event.getPlayer().getName()));
		}

		event.joinMessage(TextUtil.c("&7[&a+&7] &f" + event.getPlayer().getDisplayName() + (event.getPlayer().hasPlayedBefore() ? "" : " &8| &6" + TextUtil.ordinal(DataManager.getGlobal().getTotalUsers()) + " join")));

		PlayerJoin.exe(event);
	}
}
