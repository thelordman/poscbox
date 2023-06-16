package me.lord.poscbox.rank;

import me.lord.poscbox.data.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.Arrays;
import java.util.UUID;

public enum Rank {
	OWNER("&4Owner", 'a', "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.*", "poscbox.command.economy", "poscbox.command.joinkit", "poscbox.command.rank"),
	HEAD_DEVELOPER("&5Head Developer", 'b', "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.*", "poscbox.command.economy", "poscbox.command.joinkit", "poscbox.command.rank"),
	DEVELOPER("&dDeveloper", 'c', "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.*", "poscbox.command.economy", "poscbox.command.joinkit", "poscbox.command.rank"),
	ADMIN("&cAdmin", 'd', "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.*", "poscbox.command.economy", "poscbox.command.joinkit", "poscbox.command.rank"),
	BUILDER("&9Builder", 'e', "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.creative", "poscbox.command.gamemode.survival", "poscbox.command.gamemode.spectator", "poscbox.command.joinkit"),
	SR_MOD("&6Sr. Mod", 'f', "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.*"),
	MOD("&eMod", 'g', "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.spectator", "poscbox.command.gamemode.survival"),
	JR_MOD("&aJr. Mod", 'h', "poscbox.donator", "poscbox.staff"),
	JR_DEVELOPER("&dJr. Developer", 'i', "poscbox.donator", "poscbox.staff"),

	LEGEND("&6Legend", 'j', "poscbox.donator"),
	HERO("&bHero", 'k', "poscbox.donator"),
	ELDER("&3Elder", 'l', "poscbox.donator"),
	EMPEROR("&eEmperor", 'm', "poscbox.donator"),
	KNIGHT("&2Knight", 'n', "poscbox.donator");

	private final String[] permissions;
	private final char tabPlacement;
	private final String display;

	Rank(String display, char tabPlacement, String... permissions) {
		this.permissions = permissions;
		this.tabPlacement = tabPlacement;
		this.display = display;
	}

	public String getDisplay() {
		return display;
	}

	public char getTabPlacement() {
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
