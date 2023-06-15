package me.lord.poscbox.commands;

import me.lord.poscbox.punishment.Punishment;
import me.lord.poscbox.punishment.PunishmentManager;
import me.lord.poscbox.punishment.PunishmentType;
import me.lord.poscbox.rank.Rank;
import me.lord.poscbox.utilities.Cmd;
import me.lord.poscbox.utilities.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class KickCommand implements Cmd {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (args.length == 0) return false;

		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) return false;

		if (!sender.getName().equals("My_Lord") && Rank.get(target).ordinal() >= Rank.get(((Player) sender)).ordinal()) {
			sender.sendMessage(TextUtil.c("&cYou cannot kick that player."));
			return true;
		}

		String reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length)).replace("-s", "").isEmpty() ? "No reason"
				: String.join(" ", Arrays.copyOfRange(args, 1, args.length)).replace("-s", "");

		String msg = "&f" + target.getName() + " &6has been kicked by &f" + sender.getName() + " &6for &f" + reason + "&6.";
		if (args[args.length - 1].equals("-s")) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.hasPermission("poscbox.staff")) player.sendMessage(TextUtil.c(msg + " &7[&6Silent&7]"));
			}
		} else Bukkit.broadcast(TextUtil.c(msg));

		target.kickPlayer(reason);

		PunishmentManager.addPunishment(target.getUniqueId(), new Punishment(PunishmentType.KICK,
				null, reason, sender instanceof Player ? ((Player) sender).getUniqueId() : null));

		return true;
	}

	@Override
	public String name() {
		return "kick";
	}

	@Override
	public String permission() {
		return "poscbox.command.kick";
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (args.length == 1) return null;
		else return Collections.emptyList();
	}
}
