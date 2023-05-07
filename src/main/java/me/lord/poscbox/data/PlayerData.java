package me.lord.poscbox.data;

import me.lord.poscbox.scoreboard.FastBoard;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serial;
import java.util.UUID;

/**
 * A player's data which won't be wiped on a server reload or a server restart.
 */
public final class PlayerData implements Data {
	@Serial
	private static final long serialVersionUID = 3585230923101039049L;

	@Nullable
	private UUID uuid;
	private double balance = 0d;
	private int killstreak = 0;

	private transient Integer selectedNPC = null;
	private transient boolean godMode = false;
	private transient boolean hunger = true;
	private transient FastBoard scoreboard;

	public static double getBalance(UUID uuid) {
		return DataManager.getPlayerData(uuid).getBalance();
	}

	public static double getBalance(Player player) {
		return getBalance(player.getUniqueId());
	}

	public PlayerData(@NotNull UUID uuid) {
		this.uuid = uuid;
		scoreboard = new FastBoard(uuid);
	}

	@Nullable
	public UUID getUUID() {
		return uuid;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
		getScoreboard().updateBalance();
	}

	public void addBalance(double amount) {
		setBalance(getBalance() + amount);
	}

	public void removeBalance(double amount) {
		setBalance(getBalance() - amount);
	}

	public boolean godMode() {
		return godMode;
	}

	public void setGodMode(boolean godMode) {
		this.godMode = godMode;
	}

	public boolean hunger() {
		return hunger;
	}

	public void setHunger(boolean hunger) {
		this.hunger = hunger;
	}

	public FastBoard getScoreboard() {
		if (scoreboard == null) {
			scoreboard = new FastBoard(uuid);
		}
		return scoreboard;
	}

	public void setScoreboard(FastBoard scoreboard) {
		this.scoreboard = scoreboard;
	}

	public int getKillstreak() {
		return killstreak;
	}

	public void incrementKillstreak() {
		killstreak++;
	}

	public void resetKillstreak() {
		killstreak = 0;
	}
}
