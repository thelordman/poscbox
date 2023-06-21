package me.lord.poscbox.listeners;

import com.destroystokyo.paper.profile.PlayerProfile;
import me.lord.poscbox.PoscBox;
import me.lord.poscbox.data.DataManager;
import me.lord.poscbox.discord.events.PlayerJoin;
import me.lord.poscbox.item.ItemManager;
import me.lord.poscbox.npc.NPCManager;
import me.lord.poscbox.rank.Rank;
import me.lord.poscbox.utilities.PacketListener;
import me.lord.poscbox.utilities.TeamUtil;
import me.lord.poscbox.utilities.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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

		if (event.getPlayer().getLevel() == 0) {
			event.getPlayer().setLevel(1);
		}

		DataManager.loadPlayerData(event.getPlayer());
		DataManager.getPlayerData(event.getPlayer()).getScoreboard().updateAll();

		NPCManager.sendInitPacketAll(event.getPlayer());

		for (Player player : Bukkit.getOnlinePlayers()) {
			DataManager.getPlayerData(player).getScoreboard().updateTitle();
		}

		if (TeamUtil.board.getPlayerTeam(event.getPlayer()) != null)
			TeamUtil.board.getPlayerTeam(event.getPlayer()).removePlayer(event.getPlayer());

		if (DataManager.getPlayerData(event.getPlayer()).getRank() == null) {
			event.getPlayer().displayName(null);
			if (TeamUtil.board.getTeam("§r§r§r§r§r§r§r§r§r§r§r") != null && !TeamUtil.board.getTeam("§r§r§r§r§r§r§r§r§r§r§r").hasPlayer(event.getPlayer()))
				TeamUtil.board.getTeam("§r§r§r§r§r§r§r§r§r§r§r").addPlayer(event.getPlayer());
		} else {
			event.getPlayer().displayName(TextUtil.c(DataManager.getPlayerData(event.getPlayer()).getRank().getDisplay() + " &8| &f" + event.getPlayer().getName()));
			if (TeamUtil.board.getTeam(String.valueOf(DataManager.getPlayerData(event.getPlayer()).getRank().getTabPlacement())) != null)
				TeamUtil.board.getTeam(String.valueOf(DataManager.getPlayerData(event.getPlayer()).getRank().getTabPlacement())).addPlayer(event.getPlayer());
		}

		event.joinMessage(TextUtil.c("&7[&a+&7] &f" + event.getPlayer().getDisplayName() + (event.getPlayer().hasPlayedBefore() ? "" : " &8| &6" + TextUtil.ordinal(DataManager.getGlobal().getTotalUsers()) + " join")));

		event.getPlayer().teleport(new Location(Bukkit.getWorld("poscbox"), -163.5, 27, -209.5));

		PlayerJoin.exe(event);
	}
}
