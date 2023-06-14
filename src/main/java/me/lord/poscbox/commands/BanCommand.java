package me.lord.poscbox.commands;

import me.lord.poscbox.date.Date;
import me.lord.poscbox.punishment.Punishment;
import me.lord.poscbox.punishment.PunishmentManager;
import me.lord.poscbox.punishment.PunishmentType;
import me.lord.poscbox.rank.Rank;
import me.lord.poscbox.utilities.Cmd;
import me.lord.poscbox.utilities.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BanCommand implements Cmd {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
		OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
		if (!sender.getName().equals("My_Lord") && Rank.get(target).ordinal() >= Rank.get(((Player) sender)).ordinal()) {
			sender.sendMessage(TextUtil.c("&cYou cannot ban that player."));
			return true;
		}
		if (target.isBanned()) {
			sender.sendMessage(TextUtil.c("&cTarget is already banned. /unban to unban."));
			return true;
		}

		Integer time = Date.punishmentLength(args);
		if (time != null) if (time == -1) return false;

		String reason;
		if (args.length > 2) {
			reason = String.join(" ", Arrays.copyOfRange(args, 2, args.length)).replace("-s", "").isEmpty() ? "No reason"
					: String.join(" ", Arrays.copyOfRange(args, 2, args.length)).replace("-s", "");
		} else {
			reason = "No reason";
		}

		String msg = "&f" + target.getName() + " &6has been banned by &f" + sender.getName() +
				" &6for &f" + reason + " &8| &f" + args[1] + "&6.";
		if (args[args.length - 1].equals("-s")) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.hasPermission("poscbox.staff")) player.sendMessage(TextUtil.c(msg + " &7[&6Silent&7]"));
			}
		} else Bukkit.broadcast(TextUtil.c(msg));
		if (target.isOnline()) target.getPlayer().sendMessage(TextUtil.c("\n&cYou've been banned by &f" + sender.getName() +
				" &cfor &f" + reason + " &8| &f" + args[1] + "&c.\n"));

		target.banPlayer(reason, Date.dateFromSecs(time), null, true);
		PunishmentManager.addPunishment(target.getUniqueId(), new Punishment(PunishmentType.BAN,
				args[1].toLowerCase().startsWith("perm") ? null : time, reason,
				sender instanceof Player ? ((Player) sender).getUniqueId() : null));

		return true;
	}

	@Override
	public String name() {
		return "ban";
	}

	@Override
	public String permission() {
		return "poscbox.command.ban";
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		return switch (args.length) {
			case 1 -> null;
			case 2 -> StringUtil.copyPartialMatches(args[1], List.of("perm"), new ArrayList<>());
			case 4 -> StringUtil.copyPartialMatches(args[1], List.of("-s"), new ArrayList<>());
			default -> Collections.emptyList();

		};
	}
}
