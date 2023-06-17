package me.lord.poscbox.commands;

import me.lord.poscbox.utilities.Cmd;
import me.lord.poscbox.utilities.TextUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class HatCommand implements Cmd {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		Player player = (Player) sender;
		PlayerInventory inventory = player.getInventory();
		ItemStack hand = inventory.getItemInMainHand();
		ItemStack helmet = inventory.getHelmet();

		if (hand.getType() == Material.AIR) {
			player.sendMessage(TextUtil.c("&cHold an item to set it as your hat"));
			return true;
		}
		if (hand.getAmount() > 1) {
			player.sendMessage(TextUtil.c("&cHold only one of this item"));
			return true;
		}

		inventory.setHelmet(hand);
		inventory.setItemInMainHand(helmet);

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		return Collections.emptyList();
	}

	@Override
	public String name() {
		return "hat";
	}

	@Override
	public String permission() {
		return "poscbox.command.hat";
	}
}
