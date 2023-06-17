package me.lord.poscbox.commands;

import me.lord.poscbox.PoscBox;
import me.lord.poscbox.data.DataManager;
import me.lord.poscbox.utilities.Cmd;
import me.lord.poscbox.utilities.TextUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class TrailCommand implements Cmd {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (args.length == 0) return false;
		if (sender instanceof Player player) {

			if (isMember(args[0].toUpperCase())) {
				DataManager.getPlayerData(player).setTrail(false);
				player.sendMessage(TextUtil.c("Set your trail to " + args[0]));
				DataManager.getPlayerData(player).setTrail(true);
				final Location[] location = {player.getLocation()};
				while (DataManager.getPlayerData(player).hasTrail()) {
					new BukkitRunnable() {
						@Override
						public void run() {
							location[0] = player.getLocation();
							location[0].setX(location[0].getX() - 0.5);
							player.getWorld().spawnParticle(Particle.valueOf(args[0].toUpperCase()), location[0], 5);
						}
					}.runTaskLater(PoscBox.get(), 5);
				}
			} else {
				DataManager.getPlayerData(player).setTrail(false);
				player.sendMessage(TextUtil.c("Invalid particle!"));
			}
		}
		return true;
	}

	@Override
	public String name() {
		return "trail";
	}

	@Override
	public String permission() {
		return "poscbox.command.trail";
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		return switch (args.length) {
			case 1 -> List.of("flame", "soul_fire_flame");
			default -> Collections.emptyList();
		};
	}

	private boolean isMember(String aName) {
		Particle[] aFruits = Particle.values();
		for (Particle aFruit : aFruits)
			if (aFruit.name().equals(aName))
				return true;
		return false;
	}
}
