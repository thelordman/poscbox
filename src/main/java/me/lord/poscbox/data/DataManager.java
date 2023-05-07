package me.lord.poscbox.data;

import me.lord.poscbox.PoscBox;
import me.lord.poscbox.scoreboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;

public class DataManager {
	private static final HashMap<UUID, PlayerData> playerDataMap = new HashMap<>();

	public static final File PLAYER_DATA_FOLDER = new File(PoscBox.get().getDataFolder() + File.separator + "playerdata");
	public static final File GLOBAL_DATA_FILE = new File(PoscBox.get().getDataFolder() + File.separator + "globaldata.dat");

	private static GlobalData globalData = null;

	public static void loadPlayerData(UUID uuid) {
		PlayerData data = getPlayerDataFromFile(uuid);
		playerDataMap.put(uuid, data == null ? new PlayerData(uuid) : data);
	}

	public static void loadPlayerData(Player player) {
		loadPlayerData(player.getUniqueId());
	}

	public static void savePlayerData(UUID uuid) {
		playerDataMap.get(uuid).serialize(PLAYER_DATA_FOLDER.getPath() + File.separator + uuid + ".dat");
	}

	public static void savePlayerData(Player player) {
		savePlayerData(player.getUniqueId());
	}

	public static GlobalData getGlobal() {
		return globalData;
	}


	public static void loadAll() {
		if (!PoscBox.get().getDataFolder().exists()) PoscBox.get().getDataFolder().mkdir();

		if (GLOBAL_DATA_FILE.exists()) {
			globalData = (GlobalData) Data.deserialize(GLOBAL_DATA_FILE.getPath());
			globalData.setTotalUsers(Bukkit.getOfflinePlayers().length);
		} else if (globalData == null) {
			globalData = new GlobalData();
		}

		if (!PLAYER_DATA_FOLDER.exists()) PLAYER_DATA_FOLDER.mkdir();
		for (Player player : Bukkit.getOnlinePlayers()) {
			loadPlayerData(player);
			getPlayerData(player).getScoreboard().updateAll();
		}
	}

	public static void saveAll() {
		if (!PoscBox.get().getDataFolder().exists()) PoscBox.get().getDataFolder().mkdir();

		if (globalData != null) {
			globalData.serialize(GLOBAL_DATA_FILE.getPath());
		}

		if (!PLAYER_DATA_FOLDER.exists()) PLAYER_DATA_FOLDER.mkdir();

		for (Player player : Bukkit.getOnlinePlayers()) {
			savePlayerData(player);
		}
	}


	private static PlayerData getPlayerDataFromFile(UUID uuid) {
		File file = new File(PLAYER_DATA_FOLDER.getPath() + File.separator + uuid.toString() + ".dat");
		if (!file.exists()) return null;
		PlayerData data = (PlayerData) Data.deserialize(file.getPath());
		if (data != null) {
			if (data.getUUID() == null) data.setUUID(uuid);
			if (data.getScoreboard() == null && Bukkit.getOfflinePlayer(uuid).isOnline()) data.setScoreboard(new FastBoard(uuid));
		}
		return data;
	}

	public static PlayerData getPlayerData(Player player) {
		return getPlayerData(player.getUniqueId());
	}

	public static PlayerData getPlayerData(UUID uuid) {
		return playerDataMap.get(uuid);
	}

	/**
	 * Use this method to modify the PlayerData of offline players.
	 */
	public static void modifyPlayerData(UUID uuid, Consumer<PlayerData> consumer) {
		PlayerData playerData = playerDataMap.containsKey(uuid) ? getPlayerData(uuid) : getPlayerDataFromFile(uuid);
		if (playerData != null) {
			consumer.accept(playerData);
			playerData.serialize(PLAYER_DATA_FOLDER.getPath() + File.separator + uuid + ".dat");
		}
	}
}
