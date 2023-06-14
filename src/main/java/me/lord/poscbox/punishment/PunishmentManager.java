package me.lord.poscbox.punishment;

import me.lord.poscbox.data.DataManager;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.UUID;

public class PunishmentManager {
	private static ArrayList<Punishment> getPunishments(UUID uuid) {
		return Bukkit.getOfflinePlayer(uuid).isOnline() ? DataManager.getPlayerData(uuid).getPunishments() : DataManager.getPlayerDataCopy(uuid).getPunishments();
	}

	public static ArrayList<Punishment> getPunishments(UUID uuid, PunishmentType punishmentType) {
		if (getPunishments(uuid) == null) return null;
		if (getPunishments(uuid).isEmpty()) return null;
		ArrayList<Punishment> list = new ArrayList<>();
		getPunishments(uuid).forEach((p) -> {
			if (p.getPunismentType() == punishmentType) list.add(p);
		});
		return list;
	}

	public static int getPunishmentAmount(UUID uuid, PunishmentType punishmentType) {
		return getPunishments(uuid, punishmentType) == null ? 0 : getPunishments(uuid, punishmentType).size();
	}

	public static void addPunishment(UUID uuid, Punishment punishment) {
		DataManager.modifyPlayerData(uuid, data -> data.addPunishment(punishment));
	}

	public static boolean removePunishment(UUID uuid, int ID) {
		var ref = new Object() {
			boolean bool;
		};
		DataManager.modifyPlayerData(uuid, data -> ref.bool = data.removePunishment(ID));
		return ref.bool;
	}

	public static boolean isMuted(UUID uuid) {
		return Bukkit.getOfflinePlayer(uuid).isOnline() ? DataManager.getPlayerData(uuid).isMuted() : DataManager.getPlayerDataCopy(uuid).isMuted();
	}

	public static void setMuted(UUID uuid, Integer time) {
		DataManager.modifyPlayerData(uuid, data -> data.setMuted(time));
	}
}