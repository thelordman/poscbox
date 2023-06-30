package me.lord.poscbox.mine;

import me.lord.poscbox.data.DataManager;
import me.lord.poscbox.utilities.TextUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MineManager {
	public static void mine(Player player, Material material) {
		processBlock(player, material);
	}

	private static void processBlock(Player player, Material material) {
		double reward = reward(material);
		double multi = 1d;

		multi += donatorMulti(player);

		reward *= multi;

		DataManager.getPlayerData(player).addBalance(reward);
		DataManager.getPlayerData(player).addXp(reward);
		player.sendActionBar(TextUtil.c("&f+" + TextUtil.formatMoney(reward) + " &7(" + TextUtil.format(multi) + "x) &8| &f+" + TextUtil.format(reward) + "xp &7(" + TextUtil.format(multi) + "x)"));
	}

	private static double reward(Material material) {
		return switch (material) {
			case COBBLESTONE, STONE, DEEPSLATE -> 1d;
			case COAL_ORE, DEEPSLATE_COAL_ORE -> 5d;
			case IRON_ORE, DEEPSLATE_IRON_ORE -> 10d;
			case LAPIS_ORE, DEEPSLATE_LAPIS_ORE -> 15d;
			case GOLD_ORE, DEEPSLATE_GOLD_ORE -> 25d;
			case REDSTONE_ORE, DEEPSLATE_REDSTONE_ORE -> 35d;
			case DIAMOND_ORE, DEEPSLATE_DIAMOND_ORE -> 50d;
			case EMERALD_ORE, DEEPSLATE_EMERALD_ORE -> 100d;
			case ANCIENT_DEBRIS -> 500d;
			default -> 0d;
		};
	}

	public static double donatorMulti(Player player) {
		if (DataManager.getPlayerData(player).getRank() == null) {
			return 0d;
		}
		return switch (DataManager.getPlayerData(player).getRank()) {
			case KNIGHT -> 0.25d;
			case EMPEROR -> 0.5d;
			case ELDER -> 0.75d;
			case HERO -> 1d;
			case LEGEND, OWNER, HEAD_DEVELOPER, DEVELOPER, ADMIN, BUILDER, SR_MOD, MOD, JR_MOD, JR_DEVELOPER -> 1.25d;
			default -> 0d;
		};
	}
}
