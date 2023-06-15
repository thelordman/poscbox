package me.lord.poscbox.commands;

import me.lord.poscbox.data.DataManager;
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

public class UnIpBanCommand implements Cmd {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
		if (args.length == 0) return false;

		OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
		if (!sender.getName().equals("My_Lord") && Rank.get(target).ordinal() >= Rank.get(((Player) sender)).ordinal()) {
			sender.sendMessage(TextUtil.c("&cYou cannot unban that IP."));
			return true;
		}
		if (DataManager.getPlayerDataCopy(target.getUniqueId()).getAddress() == null) {
			sender.sendMessage(TextUtil.c("&cTarget hasn't logged on the server yet."));
			return true;
		}
		if (!Bukkit.getBanList(BanList.Type.IP).isBanned(DataManager.getPlayerDataCopy(target.getUniqueId()).getAddress())) {
			sender.sendMessage(TextUtil.c("&cTarget's IP isn't banned. /ipban to ban."));
			return true;
		}

		String msg = "&f" + target.getName() + "'s IP &6has been unbanned by &f" + sender.getName();
		if (args[args.length - 1].equals("-s")) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.hasPermission("poscbox.staff")) player.sendMessage(TextUtil.c(msg + " &7[&6Silent&7]"));
			}
		} else Bukkit.broadcast(TextUtil.c(msg));

		Bukkit.getBanList(BanList.Type.IP).pardon(DataManager.getPlayerDataCopy(target.getUniqueId()).getAddress());

		return true;
	}

	@Override
	public String name() {
		return "unipban";
	}

	@Override
	public String permission() {
		return "poscbox.command.unipban";
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		return Collections.emptyList();
	}
}
