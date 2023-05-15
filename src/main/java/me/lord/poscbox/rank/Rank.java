package me.lord.poscbox.rank;

import org.bukkit.permissions.Permission;

import java.util.Arrays;

public enum Rank {
	KNIGHT("&2Knight", "poscbox.donator"),
	EMPEROR("&eEmperor", "poscbox.donator"),
	ELDER("&3Elder", "poscbox.donator"),
	HERO("&bHero", "poscbox.donator"),
	LEGEND("&6Legend", "poscbox.donator"),

	JR_DEVELOPER("&dJr. Developer", "poscbox.donator", "poscbox.staff"),
	JR_MOD("&aJr. Mod", "poscbox.donator", "poscbox.staff"),
	MOD("&eMod", "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.spectator", "poscbox.command.gamemode.survival"),
	SR_MOD("&6Sr. Mod", "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.*"),
	BUILDER("&9Builder", "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.creative", "poscbox.command.gamemode.survival", "poscbox.command.gamemode.spectator", "poscbox.command.joinkit"),
	ADMIN("&cAdmin", "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.*", "poscbox.command.economy", "poscbox.command.joinkit", "poscbox.command.rank"),
	DEVELOPER("&dDeveloper", "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.*", "poscbox.command.economy", "poscbox.command.joinkit", "poscbox.command.rank"),
	HEAD_DEVELOPER("&5Head Developer", "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.*", "poscbox.command.economy", "poscbox.command.joinkit", "poscbox.command.rank"),
	OWNER("&4Owner", "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.*", "poscbox.command.economy", "poscbox.command.joinkit", "poscbox.command.rank");

	private final String[] permissions;
	private final String display;

	Rank(String display, String... permissions) {
		this.permissions = permissions;
		this.display = display;
	}

	public String getDisplay() {
		return display;
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
}
