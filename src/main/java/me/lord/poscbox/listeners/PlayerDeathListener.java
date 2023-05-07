package me.lord.poscbox.listeners;

import me.lord.poscbox.data.DataManager;
import me.lord.poscbox.data.PlayerData;
import me.lord.poscbox.discord.events.PlayerDeath;
import me.lord.poscbox.utilities.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player victim = event.getPlayer();
		Player attacker = victim.getKiller();

		// GameRule SHOW_DEATH_MESSAGES must be set to false, or it will duplicate
		Bukkit.broadcast(TextUtil.c("&cDeath &7| &f").append(event.deathMessage()));

		PlayerData victimData = DataManager.getPlayerData(victim);

		victimData.resetKillstreak();

		victimData.getScoreboard().updateDeaths();
		victimData.getScoreboard().updateKDR();
		victimData.getScoreboard().updateKillstreak();
		if (attacker != null) {
			PlayerData attackerData = DataManager.getPlayerData(attacker);

			attackerData.incrementKillstreak();

			attackerData.getScoreboard().updateKills();
			attackerData.getScoreboard().updateKDR();
			attackerData.getScoreboard().updateKillstreak();
		}

		PlayerDeath.exe(event);
	}
}
