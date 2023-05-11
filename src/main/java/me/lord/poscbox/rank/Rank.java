package me.lord.poscbox.rank;

import org.bukkit.permissions.Permission;

import java.util.Arrays;

public enum Rank {
	KNIGHT("&2Knight", "poscbox.donator"),
	EMPEROR("&eEmperor", "poscbox.donator"),
	ELDER("&3Elder", "poscbox.donator"),
	HERO("&bHero", "poscbox.donator"),
	LEGEND("&6Legend", "poscbox.donator"),

	JR_DEVELOPER("&dJr. Developer", "poscbox.donator", "posc.staff"),
	JR_MOD("&aJr. Mod", "poscbox.donator", "posc.staff"),
	MOD("&eMod", "poscbox.donator", "posc.staff", "posc.command.gamemode.spectator", "posc.command.gamemode.survival"),
	SR_MOD("&6Sr. Mod", "poscbox.donator", "posc.staff", "posc.command.gamemode.*"),
	BUILDER("&9Builder", "poscbox.donator", "posc.staff", "posc.command.gamemode.creative", "posc.command.gamemode.survival", "posc.command.gamemode.spectator", "posc.command.joinkit"),
	ADMIN("&cAdmin", "poscbox.donator", "posc.staff", "posc.command.gamemode.*", "posc.command.economy", "posc.command.joinkit", "posc.command.rank"),
	DEVELOPER("&dDeveloper", "poscbox.donator", "posc.staff", "posc.command.gamemode.*", "posc.command.economy", "posc.command.joinkit", "posc.command.rank"),
	HEAD_DEVELOPER("&5Head Developer", "poscbox.donator", "posc.staff", "posc.command.gamemode.*", "posc.command.economy", "posc.command.joinkit", "posc.command.rank"),
	OWNER("&4Owner", "poscbox.donator", "posc.staff", "posc.command.gamemode.*", "posc.command.economy", "posc.command.joinkit", "posc.command.rank");

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
