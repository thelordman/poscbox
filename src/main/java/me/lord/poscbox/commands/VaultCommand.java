package me.lord.poscbox.commands;

import me.lord.poscbox.data.DataManager;
import me.lord.poscbox.utilities.Cmd;
import me.lord.poscbox.utilities.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class VaultCommand implements Cmd {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (sender instanceof ConsoleCommandSender) sender.sendMessage(TextUtil.c("&cThis command can only be used by players."));

		Player player = (Player) sender;
		if (args.length == 0 || Bukkit.getPlayer(args[0]) == player) {
			sender.sendMessage(TextUtil.c("&6Opening your vault."));
			Inventory inventory = Bukkit.createInventory(player, 54, "Vault");
			inventory.setContents(DataManager.getPlayerData(player.getUniqueId()).getVault());
			player.openInventory(inventory);
		} else if (sender.hasPermission("poscbox.command.invsee")) {
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
			sender.sendMessage(TextUtil.c("&6Opening &f" + target.getName() + "'s &6vault."));
			Inventory inventory = Bukkit.createInventory(player, 54, target.getName() + "'s Vault");
			if (target.isOnline()) {
				inventory.setContents(DataManager.getPlayerData(target.getUniqueId()).getVault());
			} else {
				inventory.setContents(DataManager.getPlayerDataCopy(target.getUniqueId()).getVault());
			}
			player.openInventory(inventory);
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
		return "vault";
	}

	@Override
	public String permission() {
		return "poscbox.command.vault";
	}
}
