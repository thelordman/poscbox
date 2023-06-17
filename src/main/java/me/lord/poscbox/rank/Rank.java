package me.lord.poscbox.rank;

import me.lord.poscbox.data.DataManager;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.Arrays;
import java.util.UUID;

public enum Rank {
	OWNER("&4Owner", "§a§r", NamedTextColor.DARK_RED, "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.*", "poscbox.command.economy", "poscbox.command.joinkit", "poscbox.command.rank"),
	HEAD_DEVELOPER("&5Head Developer", "§b§r", NamedTextColor.DARK_PURPLE, "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.*", "poscbox.command.economy", "poscbox.command.joinkit", "poscbox.command.rank"),
	DEVELOPER("&dDeveloper", "§c§r", NamedTextColor.LIGHT_PURPLE, "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.*", "poscbox.command.economy", "poscbox.command.joinkit", "poscbox.command.rank"),
	ADMIN("&cAdmin", "§d§r", NamedTextColor.RED, "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.*", "poscbox.command.economy", "poscbox.command.joinkit", "poscbox.command.rank"),
	BUILDER("&9Builder", "§e§r", NamedTextColor.BLUE, "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.creative", "poscbox.command.gamemode.survival", "poscbox.command.gamemode.spectator", "poscbox.command.joinkit"),
	SR_MOD("&6Sr. Mod", "§f§r", NamedTextColor.GOLD, "poscbox.staff", "poscbox.command.gamemode.*"),
	MOD("&eMod", "§k§r", NamedTextColor.YELLOW, "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.spectator", "poscbox.command.gamemode.survival"),
	JR_MOD("&aJr. Mod", "§l§r", NamedTextColor.GREEN, "poscbox.donator", "poscbox.staff"),
	JR_DEVELOPER("&dJr. Developer", "§m§r", NamedTextColor.LIGHT_PURPLE, "poscbox.donator", "poscbox.staff"),

	LEGEND("&6Legend", "§n§r", NamedTextColor.GOLD, "poscbox.donator"),
	HERO("&bHero", "§o§r", NamedTextColor.AQUA, "poscbox.donator"),
	ELDER("&3Elder", "§r§r", NamedTextColor.DARK_AQUA, "poscbox.donator"),
	EMPEROR("&eEmperor", "§r§r§r", NamedTextColor.YELLOW, "poscbox.donator"),
	KNIGHT("&2Knight", "§r§r§r§r", NamedTextColor.DARK_GREEN, "poscbox.donator");

	private final String[] permissions;
	private final NamedTextColor teamColor;
	private final String tabPlacement;
	private final String display;

	Rank(String display, String tabPlacement, NamedTextColor teamColor, String... permissions) {
		this.permissions = permissions;
		this.teamColor = teamColor;
		this.tabPlacement = tabPlacement;
		this.display = display;
	}

	public String getDisplay() {
		return display;
	}

	public NamedTextColor getTeamColor() {
		return teamColor;
	}

	public String getTabPlacement() {
		return tabPlacement;
	}

	public Permission[] getPermissions() {
		return Arrays.stream(permissions)
				.map(Permission::new)
				.toArray(Permission[]::new);
	}

	public static boolean isValid(String name) {
		for (Rank rank : values()) {
			if (name.equals(rank.name())) {
				return true;
			}
		}
		return false;
	}

	public static Rank get(OfflinePlayer player) {
		return get(player.getUniqueId());
	}

	public static Rank get(Player player) {
		return DataManager.getPlayerData(player).getRank();
	}

	public static Rank get(UUID uuid) {
		if (Bukkit.getOfflinePlayer(uuid).isOnline()) {
			return get(Bukkit.getOfflinePlayer(uuid).getPlayer());
		} else {
			return DataManager.getPlayerDataCopy(uuid).getRank();
		}
	}
}
