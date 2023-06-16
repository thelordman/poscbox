package me.lord.poscbox.commands;

import me.lord.poscbox.utilities.Cmd;
import me.lord.poscbox.utilities.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class EnderChestCommand implements Cmd {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (sender instanceof ConsoleCommandSender) sender.sendMessage(TextUtil.c("&cThis command can only be used by players."));

		Player player = (Player) sender;
		if (args.length == 0 || Bukkit.getPlayer(args[0]) == player) {
			sender.sendMessage(TextUtil.c("&6Opening your enderchest."));
			player.openInventory(player.getEnderChest());
		} else if (sender.hasPermission("poscbox.command.invsee")) {
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) return false;
			sender.sendMessage(TextUtil.c("&6Opening &f" + target.getDisplayName() + "'s &6enderchest."));
			player.openInventory(target.getEnderChest());
		} else {
			return false;
		}

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		return sender.hasPermission("poscbox.command.invsee") ? null : Collections.emptyList();
	}

	@Override
	public String name() {
		return "enderchest";
	}

	@Override
	public String permission() {
		return "poscbox.command.enderchest";
	}
}
