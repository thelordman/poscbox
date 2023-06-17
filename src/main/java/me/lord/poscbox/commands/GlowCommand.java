package me.lord.poscbox.commands;

import me.lord.poscbox.utilities.Cmd;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class GlowCommand implements Cmd {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		Player player = (Player) sender;

		if (player.hasPotionEffect(PotionEffectType.GLOWING)) player.removePotionEffect(PotionEffectType.GLOWING);
		else player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 0, false, false));

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		return Collections.emptyList();
	}

	@Override
	public String name() {
		return "glow";
	}

	@Override
	public String permission() {
		return "poscbox.command.glow";
	}
}
