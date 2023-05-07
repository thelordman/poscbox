package me.lord.poscbox.utilities;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public final class PlayerUtil {
	public static double getKDR(Player player) {
		double kdr = player.getStatistic(Statistic.PLAYER_KILLS);
		if (player.getStatistic(Statistic.DEATHS) != 0) {
			kdr = ((double) player.getStatistic(Statistic.PLAYER_KILLS)) / ((double) player.getStatistic(Statistic.DEATHS));
		}
		return kdr;
	}
}
