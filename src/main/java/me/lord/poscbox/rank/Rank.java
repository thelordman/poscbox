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
	OWNER("&4Owner", "§a§r", NamedTextColor.DARK_RED, "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.*", "poscbox.command.economy", "poscbox.command.joinkit", "poscbox.command.rank", "poscbox.command.trail", "poscbox.command.spawn"),
	HEAD_DEVELOPER("&5Head Developer", "§b§r", NamedTextColor.DARK_PURPLE, "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.*", "poscbox.command.economy", "poscbox.command.joinkit", "poscbox.command.rank", "poscbox.command.trail", "poscbox.command.spawn"),
	DEVELOPER("&dDeveloper", "§c§r", NamedTextColor.LIGHT_PURPLE, "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.*", "poscbox.command.economy", "poscbox.command.joinkit", "poscbox.command.rank", "poscbox.command.trail", "poscbox.command.spawn"),
	ADMIN("&cAdmin", "§d§r", NamedTextColor.RED, "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.*", "poscbox.command.economy", "poscbox.command.joinkit", "poscbox.command.rank", "poscbox.command.trail", "poscbox.command.spawn"),
	BUILDER("&9Builder", "§e§r", NamedTextColor.BLUE, "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.creative", "poscbox.command.gamemode.survival", "poscbox.command.gamemode.spectator", "poscbox.command.joinkit", "poscbox.command.trail", "poscbox.command.spawn"),
	SR_MOD("&6Sr Mod", "§f§r", NamedTextColor.GOLD, "poscbox.staff", "poscbox.command.gamemode.*"),
	MOD("&eMod", "§k§r", NamedTextColor.YELLOW, "poscbox.donator", "poscbox.staff", "poscbox.command.gamemode.spectator", "poscbox.command.gamemode.survival", "poscbox.command.trail", "poscbox.command.spawn"),
	JR_MOD("&aJr Mod", "§l§r", NamedTextColor.GREEN, "poscbox.donator", "poscbox.staff", "poscbox.command.spawn"),
	JR_DEVELOPER("&dJr Developer", "§m§r", NamedTextColor.LIGHT_PURPLE, "poscbox.donator", "poscbox.staff", "poscbox.command.trail", "poscbox.command.spawn"),

	LEGEND("&6Legend", "§n§r", NamedTextColor.GOLD, "poscbox.donator", "poscbox.command.enderchest", "poscbox.command.glow", "poscbox.command.vault", "poscbox.command.hat", "poscbox.command.trail", "poscbox.command.spawn"),
	HERO("&bHero", "§o§r", NamedTextColor.AQUA, "poscbox.donator", "poscbox.command.enderchest", "poscbox.command.glow", "poscbox.command.vault", "poscbox.command.hat", "poscbox.command.trail", "poscbox.command.spawn"),
	ELDER("&3Elder", "§r§r", NamedTextColor.DARK_AQUA, "poscbox.donator", "poscbox.command.enderchest", "poscbox.command.glow", "poscbox.command.vault", "poscbox.command.hat", "poscbox.command.spawn"),
	EMPEROR("&eEmperor", "§r§r§r", NamedTextColor.YELLOW, "poscbox.donator", "poscbox.command.enderchest", "poscbox.command.glow", "poscbox.command.vault", "poscbox.command.spawn"),
	KNIGHT("&2Knight", "§r§r§r§r", NamedTextColor.DARK_GREEN, "poscbox.donator", "poscbox.command.enderchest", "poscbox.command.spawn");

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
