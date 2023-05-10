package me.lord.poscbox.commands;

import me.lord.poscbox.data.DataManager;
import me.lord.poscbox.data.PlayerData;
import me.lord.poscbox.utilities.Cmd;
import me.lord.poscbox.utilities.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EconomyCommand implements Cmd {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (args.length < 2 | args.length > 3) return false;
		OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
		String executor = sender instanceof Player ? ((Player) sender).getDisplayName() : "Console";

		if (args.length < 3) {
			if (args[1].equals("reset")) {
				DataManager.modifyPlayerData(target.getUniqueId(), data -> data.setBalance(0d));
				if (target.isOnline()) {
					target.getPlayer().sendMessage(TextUtil.c("&6Your balance was reset by " + executor + "&6."));
					DataManager.getPlayerData(target.getUniqueId()).getScoreboard().updateBalance();
				}
				if (!(target == sender)) sender.sendMessage(TextUtil.c(("&6You reset " + target.getName() + "&6's balance.")));
				return true;
			}
			if (args[1].equals("get")) {
				DataManager.modifyPlayerData(target.getUniqueId(), data -> sender.sendMessage(TextUtil.formatMoney(data.getBalance())));
				if (target.isOnline()) DataManager.getPlayerData(target.getUniqueId()).getScoreboard().updateBalance();
				return true;
			}
			return false;
		}

		String targetMsg;
		String senderMsg;
		Double bal = DataManager.getPlayerDataCopy(target.getUniqueId()).getBalance();
		Double amount;
		double math;
		DecimalFormat df = new DecimalFormat("###,###.##");
		try {
			amount = df.parse(args[2]).doubleValue();
		} catch (ParseException e) {
			return false;
		}
		switch (args[1]) {
			case "set" -> {
				math = amount;
				targetMsg = "&6Your balance was set to &f$" + df.format(amount) + " &6by " + executor + "&6.";
				senderMsg = "&6You set &f" + target.getName() + "&6's balance to &f$" + df.format(amount) + "&6.";
			}
			case "add", "put" -> {
				math = bal + amount;
				targetMsg = "&6" + executor + " &6has added &f$" + df.format(amount) + " &6to your balance.";
				senderMsg = "&6You added &f$" + df.format(amount) + " &6to " + target.getName() + "&6's balance.";
			}
			case "take", "remove" -> {
				math = bal - amount;
				targetMsg = "&6" + executor + " &6has removed &f$" + df.format(amount) + " &6from your balance.";
				senderMsg = "&6You removed &f$" + df.format(amount) + " &6from " + target.getName() + "&6's balance.";
			}
			case "multiply", "times" -> {
				math = bal * amount;
				targetMsg = "&6" + executor + " &6has multiplied your balance by &f" + df.format(amount) + "&6.";
				senderMsg = "&6You multiplied" + target.getName() + "&6's balance by &f" + df.format(amount) + "&6.";
			}
			case "divide", "fraction" -> {
				math = bal / amount;
				targetMsg = "&6" + executor + " &6has divided your balance by &f" + df.format(amount) + "&6.";
				senderMsg = "&6You divided" + target.getName() + "&6's balance by &f" + df.format(amount) + "&6.";
			}
			case "power", "pow" -> {
				math = Math.pow(bal, amount);
				targetMsg = "&6" + executor + " &6has set your balance to the power of &f" + df.format(amount) + "&6.";
				senderMsg = "&6You set" + target.getName() + "&6's balance to the power of &f" + df.format(amount) + "&6.";
			}
			case "squareroot", "sqrt" -> {
				math = Math.sqrt(amount);
				targetMsg = "&6" + executor + " &6has set your balance to the squareroot of &f" + df.format(amount) + "&6.";
				senderMsg = "&6You set" + target.getName() + "&6's balance to the squareroot of &f" + df.format(amount) + "&6.";
			}
			default -> {
				return false;
			}
		}
		DataManager.modifyPlayerData(target.getUniqueId(), playerData -> playerData.setBalance(math));
		if (target.isOnline()) {
			target.getPlayer().sendMessage(TextUtil.c(targetMsg));
			DataManager.getPlayerData(target.getUniqueId()).getScoreboard().updateBalance();
		}
		if (!(target == sender)) sender.sendMessage(TextUtil.c(senderMsg));

		return true;
	}

	@Override
	public String name() {
		return "economy";
	}

	@Override
	public String permission() {
		return "posc.command.economy";
	}

	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		return switch (args.length) {
			case 1 -> null;
			case 2 -> StringUtil.copyPartialMatches(args[1], List.of("get", "reset", "set", "add", "take", "multiply", "divide", "power", "squareroot"), new ArrayList<>());
			default -> Collections.emptyList();
		};
	}
}
