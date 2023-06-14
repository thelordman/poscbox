package me.lord.poscbox.commands;

import me.lord.poscbox.rank.Rank;
import me.lord.poscbox.utilities.Cmd;
import me.lord.poscbox.utilities.TextUtil;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class UnbanCommand implements Cmd {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
		if (args.length == 0) return false;

		OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
		if (!sender.getName().equals("My_Lord") && Rank.get(target).ordinal() >= Rank.get(((Player) sender)).ordinal()) {
			sender.sendMessage(TextUtil.c("&cYou cannot unban that player."));
			return true;
		}
		if (!target.isBanned()) {
			sender.sendMessage(TextUtil.c("&cTarget isn't banned. /ban to ban."));
			return true;
		}

		String msg = "&f" + target.getName() + " &6has been unbanned by &f" + sender.getName();
		if (args[args.length - 1].equals("-s")) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.hasPermission("poscbox.staff")) player.sendMessage(TextUtil.c(msg + " &7[&6Silent&7]"));
			}
		} else Bukkit.broadcastMessage(msg);

		if (target.isOnline()) target.getPlayer().sendMessage(TextUtil.c("\n&cYou've been unbanned by &f" + sender.getName() + "&c.\n"));
		Bukkit.getBanList(BanList.Type.NAME).pardon(target.getName());

		return true;
	}

	@Override
	public String name() {
		return "unban";
	}

	@Override
	public String permission() {
		return "poscbox.command.unban";
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (args.length == 1) {
			return Bukkit.getBanList(BanList.Type.NAME).getBanEntries().stream()
					.map(BanEntry::getTarget)
					.toList();
		}
		return Collections.emptyList();
	}
}
