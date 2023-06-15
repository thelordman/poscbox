package me.lord.poscbox.commands;

import me.lord.poscbox.date.Date;
import me.lord.poscbox.date.DateType;
import me.lord.poscbox.punishment.Punishment;
import me.lord.poscbox.punishment.PunishmentManager;
import me.lord.poscbox.punishment.PunishmentType;
import me.lord.poscbox.rank.Rank;
import me.lord.poscbox.utilities.Cmd;
import me.lord.poscbox.utilities.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PunishmentsCommand implements Cmd {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
		Player player = (Player) sender;
		OfflinePlayer target = args.length == 0 ? player : Bukkit.getOfflinePlayer(args[0]);
		if (args.length > 1 && !sender.getName().equals("My_Lord") && Rank.get(target).ordinal() >= Rank.get(((Player) sender)).ordinal()) {
			sender.sendMessage(TextUtil.c("&cYou cannot modify the punishments of that player."));
			return true;
		}

		if (args.length > 1) {
			if (args.length < 3 | !args[1].equals("remove")) return false;

			int i;
			try {
				i = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				return false;
			}
			if (PunishmentManager.removePunishment(target.getUniqueId(), i)) {
				sender.sendMessage(TextUtil.c("&6Successfully removed the punishment of the ID &f" + i + "&6."));
			} else sender.sendMessage(TextUtil.c("&cPunishment of that ID not found."));
		}

		sender.sendMessage(TextUtil.c("\n&r    &6&l" + target.getName() + "'s Punishments\n&r\n" +
				" &6Warnings &7(&f" + PunishmentManager.getPunishmentAmount(target.getUniqueId(), PunishmentType.WARNING) + "&7):\n" +
				punishmentEntries(PunishmentManager.getPunishments(target.getUniqueId(), PunishmentType.WARNING)) +
				" &6Jails &7(&f" + PunishmentManager.getPunishmentAmount(target.getUniqueId(), PunishmentType.JAIL) + "&7):\n" +
				punishmentEntries(PunishmentManager.getPunishments(target.getUniqueId(), PunishmentType.JAIL)) +
				" &6Kicks &7(&f" + PunishmentManager.getPunishmentAmount(target.getUniqueId(), PunishmentType.KICK) + "&7):\n" +
				punishmentEntries(PunishmentManager.getPunishments(target.getUniqueId(), PunishmentType.KICK)) +
				" &6Mutes &7(&f" + PunishmentManager.getPunishmentAmount(target.getUniqueId(), PunishmentType.MUTE) + "&7):\n" +
				punishmentEntries(PunishmentManager.getPunishments(target.getUniqueId(), PunishmentType.MUTE)) +
				" &6Bans &7(&f" + PunishmentManager.getPunishmentAmount(target.getUniqueId(), PunishmentType.BAN) + "&7):\n" +
				punishmentEntries(PunishmentManager.getPunishments(target.getUniqueId(), PunishmentType.BAN)) +
				" &6IP-Bans &7(&f" + PunishmentManager.getPunishmentAmount(target.getUniqueId(), PunishmentType.IP_BAN) + "&7):\n" +
				punishmentEntries(PunishmentManager.getPunishments(target.getUniqueId(), PunishmentType.IP_BAN)) + "&r"));

		return true;
	}

	private String punishmentEntries(ArrayList<Punishment> punishments) {
		if (punishments == null) return "  &6No punishments found.\n";
		StringBuilder builder = new StringBuilder();
		for (Punishment punishment : punishments) {
			String expirationDate = punishment.getExpiration() == null ? "&cNever" : Date.dateTimeFormat(punishment.getExpiration(), DateType.FULL);
			String punisher = punishment.getPunisher() == null ? "Console" : Bukkit.getOfflinePlayer(punishment.getPunisher()).getName();
			builder.append("  &6ID&7: &f").append(punishment.getID()).append("\n")
					.append("   &6Given&7: &f").append(Date.dateTimeFormat(punishment.getCreated(), DateType.FULL)).append("\n");
			if (punishment.getPunismentType() != PunishmentType.JAIL) builder.append("   &6Expiration&7: &f").append(expirationDate).append("\n");
			if (punishment.getReason() != null) builder.append("   &6Reason&7: &f").append(punishment.getReason()).append("\n");
			builder.append("   &6Punisher&7: &f").append(punisher).append("\n");
		}
		return builder.toString();
	}

	@Override
	public String name() {
		return "punishments";
	}

	@Override
	public String permission() {
		return "poscbox.commands.punishments";
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		return switch (args.length) {
			case 1 -> null;
			case 2 -> StringUtil.copyPartialMatches(args[1], List.of("remove"), new ArrayList<>());
			default -> Collections.emptyList();
		};
	}
}
