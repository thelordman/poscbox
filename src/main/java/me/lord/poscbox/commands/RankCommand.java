package me.lord.poscbox.commands;

import me.lord.poscbox.data.DataManager;
import me.lord.poscbox.rank.Rank;
import me.lord.poscbox.utilities.Cmd;
import me.lord.poscbox.utilities.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RankCommand implements Cmd {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (args.length < 2 | args.length > 3) return false;
		OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
		if (args[1].equals("set")) {
			if (args[2].equals("default")) {
				DataManager.modifyPlayerData(target.getUniqueId(), data -> data.setRank(null));
				sender.sendMessage(TextUtil.c("&f" + target.getName() + "'s &6rank was set to &fdefault&6."));
				if (target.isOnline()) {
					target.getPlayer().sendMessage(TextUtil.c("&f" + sender.getName() + " &6has set your rank to &fdefault&6."));
				}
			}
			if (Rank.isValid(args[2].toUpperCase())) {
				DataManager.modifyPlayerData(target.getUniqueId(), data -> data.setRank(Rank.valueOf(args[2].toUpperCase())));
				sender.sendMessage(TextUtil.c("&f" + target.getName() + "'s &6rank was set to &f" + DataManager.getPlayerDataCopy(target.getUniqueId()).getRank().getDisplay() + "&6."));
				if (target.isOnline()) {
					target.getPlayer().sendMessage(TextUtil.c("&f" + sender.getName() + " &6has set your rank to &f" + DataManager.getPlayerData(target.getUniqueId()).getRank().getDisplay() + "&6."));
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public String name() {
		return "rank";
	}

	@Override
	public String permission() {
		return "poscbox.command.rank";
	}

	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		return switch (args.length) {
			case 1 -> null;
			case 2 -> List.of("set");
			case 3 -> StringUtil.copyPartialMatches(args[2],
					List.of("default", "knight", "emperor",
					"elder", "hero", "legend",
					"jr_developer", "jr_mod", "mod",
					"sr_mod", "builder", "admin",
					"developer", "head_developer", "owner"), new ArrayList<>());
			default -> Collections.emptyList();
		};
	}
}
