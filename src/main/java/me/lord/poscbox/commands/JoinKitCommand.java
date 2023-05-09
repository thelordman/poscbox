package me.lord.poscbox.commands;

import me.lord.poscbox.item.ItemManager;
import me.lord.poscbox.utilities.Cmd;
import me.lord.poscbox.utilities.CommandUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class JoinKitCommand implements Cmd {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (!(sender instanceof Player) && args.length == 0) return CommandUtil.error(sender, CommandUtil.Error.PLAYER_ONLY);
		Player target = args.length == 0 ? (Player) sender : Bukkit.getPlayer(args[0]);
		if (target == null) return CommandUtil.error(sender, CommandUtil.Error.TARGET_NOT_ONLINE);

		ItemManager.joinKit(target);

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		return args.length == 1 ? null : Collections.emptyList();
	}

	@Override
	public String name() {
		return "joinkit";
	}

	@Override
	public String permission() {
		return "poscbox.command.joinkit";
	}
}
