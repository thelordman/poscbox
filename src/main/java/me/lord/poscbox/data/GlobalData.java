package me.lord.poscbox.data;

import org.bukkit.Bukkit;

import java.io.Serial;

/**
 * Stores data that is global (not attached to an entity, item or a block).
 */
public final class GlobalData implements Data {
	@Serial
	private static final long serialVersionUID = 4596697478918441344L;

	private int highID = 0;

	private transient int totalUsers = Bukkit.getOfflinePlayers().length;
	private transient Integer consoleSelectedNPC = null;

	public int getTotalUsers() {
		return totalUsers;
	}

	public void incrementTotalUsers() {
		totalUsers++;
	}

	public void setTotalUsers(int totalUsers) {
		this.totalUsers = totalUsers;
	}

	public Integer getConsoleSelectedNPC() {
		return consoleSelectedNPC;
	}

	public void setConsoleSelectedNPC(Integer selectedNPC) {
		consoleSelectedNPC = selectedNPC;
	}

	public int getHighID() {
		return highID;
	}

	public void setHighID(int highID) {
		this.highID = highID;
	}

	public void incrementHighID() {
		highID++;
	}
}
