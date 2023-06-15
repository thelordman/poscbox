package me.lord.poscbox.commands;

import me.lord.poscbox.punishment.PunishmentManager;
import me.lord.poscbox.rank.Rank;
import me.lord.poscbox.utilities.Cmd;
import me.lord.poscbox.utilities.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UnmuteCommand implements Cmd {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
		if (args.length == 0) return false;

		OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
		if (!sender.getName().equals("My_Lord") && Rank.get(target).ordinal() >= Rank.get(((Player) sender)).ordinal()) {
			sender.sendMessage(TextUtil.c("&cYou cannot unmute that player."));
			return true;
		}
		if (!PunishmentManager.isMuted(target.getUniqueId())) {
			sender.sendMessage(TextUtil.c("&cTarget isn't muted. /mute to mute."));
			return true;
		}

		String msg = "&f" + target.getName() + " &6has been unmuted by &f" + sender.getName();
		if (args[args.length - 1].equals("-s")) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.hasPermission("poscbox.staff")) player.sendMessage(TextUtil.c(msg + " &7[&6Silent&7]"));
			}
		} else Bukkit.broadcast(TextUtil.c(msg));

		if (target.isOnline()) target.getPlayer().sendMessage(TextUtil.c("\n&cYou've been unmuted by &f" + sender.getName() + "&c.\n"));
		PunishmentManager.setMuted(target.getUniqueId(), 0);

		return true;
	}

	@Override
	public String name() {
		return "unmute";
	}

	@Override
	public String permission() {
		return "poscbox.command.unmute";
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		return null;
	}
}
