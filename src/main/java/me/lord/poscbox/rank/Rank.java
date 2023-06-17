package me.lord.poscbox.rank;

import me.lord.poscbox.data.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.Arrays;
import java.util.UUID;

public enum Rank {
	OWNER("&4Owner", "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.*", "poscbox.command.economy", "poscbox.command.joinkit", "poscbox.command.rank"),
	HEAD_DEVELOPER("&5Head Developer", "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.*", "poscbox.command.economy", "poscbox.command.joinkit", "poscbox.command.rank"),
	DEVELOPER("&dDeveloper", "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.*", "poscbox.command.economy", "poscbox.command.joinkit", "poscbox.command.rank"),
	ADMIN("&cAdmin", "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.*", "poscbox.command.economy", "poscbox.command.joinkit", "poscbox.command.rank"),
	BUILDER("&9Builder", "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.creative", "poscbox.command.gamemode.survival", "poscbox.command.gamemode.spectator", "poscbox.command.joinkit"),
	SR_MOD("&6Sr. Mod", "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.*"),
	MOD("&eMod", "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.spectator", "poscbox.command.gamemode.survival"),
	JR_MOD("&aJr. Mod", "poscbox.donator", "poscbox.staff"),
	JR_DEVELOPER("&dJr. Developer", "poscbox.donator", "poscbox.staff"),

	LEGEND("&6Legend", "poscbox.donator", "poscbox.command.enderchest", "poscbox.command.glow", "poscbox.command.vault"),
	HERO("&bHero", "poscbox.donator", "poscbox.command.enderchest", "poscbox.command.glow", "poscbox.command.vault"),
	ELDER("&3Elder", "poscbox.donator", "poscbox.command.enderchest", "poscbox.command.glow", "poscbox.command.vault"),
	EMPEROR("&eEmperor", "poscbox.donator", "poscbox.command.enderchest", "poscbox.command.glow", "poscbox.command.vault"),
	KNIGHT("&2Knight", "poscbox.donator", "poscbox.command.enderchest");

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
