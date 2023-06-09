package me.lord.poscbox.data;

import com.destroystokyo.paper.profile.PlayerProfile;
import me.lord.poscbox.PoscBox;
import me.lord.poscbox.gui.GUI;
import me.lord.poscbox.npc.interaction.NPCInteraction;
import me.lord.poscbox.punishment.Punishment;
import me.lord.poscbox.rank.Rank;
import me.lord.poscbox.scoreboard.FastBoard;
import me.lord.poscbox.utilities.TeamUtil;
import me.lord.poscbox.utilities.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serial;
import java.time.Instant;
import java.util.ArrayList;
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
	private int level = 1;
	private double xp = 0d;
	private int killstreak = 0;
	private Rank rank = null;
	private final ArrayList<Punishment> punishments = new ArrayList<>();
	private Integer muted = 0;
	private String address;
	private final byte[][] vault = new byte[54][];

	private transient Integer selectedNPC = null;
	private transient NPCInteraction currentInteraction = null;
	private transient PermissionAttachment permissionAttachment = null;
	private transient boolean godMode = false;
	private transient boolean hunger = true;
	private transient FastBoard scoreboard;
	private transient boolean droppedEquipment = false;
	private transient GUI currentGUI = null;
	private boolean trail = false;


	public static double getBalance(UUID uuid) {
		return DataManager.getPlayerData(uuid).getBalance();
	}

	public static double getBalance(Player player) {
		return getBalance(player.getUniqueId());
	}

	public PlayerData(@NotNull UUID uuid) {
		this.uuid = uuid;
		address = null;
		if (Bukkit.getPlayer(uuid) != null) {
			address = Bukkit.getPlayer(uuid).getAddress().getAddress().getHostAddress();
		}
		initPermissions();
	}

	public PlayerData(@NotNull PlayerData playerData) {
		uuid = playerData.uuid;
		balance = playerData.balance;
		killstreak = playerData.killstreak;
		rank = playerData.rank;
		muted = playerData.muted;
		address = playerData.address;

		selectedNPC = playerData.selectedNPC;
		godMode = playerData.godMode;
		hunger = playerData.hunger;
		scoreboard = playerData.scoreboard;
		droppedEquipment = playerData.droppedEquipment;
		currentGUI = playerData.currentGUI;
		trail = false;
	}

	public boolean hasTrail() {
		return trail;
	}

	public void setTrail(boolean trail) {
		this.trail = trail;
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

		Player player = Bukkit.getPlayer(uuid);
		if (!GlobalData.baltop.containsKey(player))
			GlobalData.baltop.put(player, getBalance());
		else
			GlobalData.leveltop.replace(player, getBalance());
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
		if (scoreboard == null && Bukkit.getPlayer(uuid) != null) {
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

	public boolean droppedEquipment() {
		return droppedEquipment;
	}

	public void setDroppedEquipment(boolean droppedEquipment) {
		this.droppedEquipment = droppedEquipment;
	}

	public GUI getCurrentGUI() {
		return currentGUI;
	}

	public void setCurrentGUI(GUI currentGUI) {
		this.currentGUI = currentGUI;
	}

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		Player player = Bukkit.getPlayer(uuid);
		if (TeamUtil.board.getPlayerTeam(player) != null)
			TeamUtil.board.getPlayerTeam(player).removePlayer(player);
		this.rank = rank;
		initPermissions();
		if (Bukkit.getPlayer(getUUID()) != null) {
			if (rank == null) {
				Bukkit.getPlayer(getUUID()).displayName(TextUtil.c(Bukkit.getPlayer(getUUID()).getName()));
			} else {
				Bukkit.getPlayer(getUUID()).displayName(TextUtil.c(rank.getDisplay() + " &8| &f" + Bukkit.getPlayer(getUUID()).getName()));
			}
		}
		if (rank != null)
			TeamUtil.board.getTeam(String.valueOf(rank.getTabPlacement())).addPlayer(player);
		else
			TeamUtil.board.getTeam("z").addPlayer(player);
	}

	public void initPermissions() {
		Player player = Bukkit.getPlayer(getUUID());
		if (player != null) {
			if (rank != null) {
				if (permissionAttachment == null) {
					permissionAttachment = player.addAttachment(PoscBox.get());
				}
				for (Permission permission : getRank().getPermissions()) {
					permissionAttachment.setPermission(permission, true);
				}
			} else if (permissionAttachment != null) {
				player.removeAttachment(permissionAttachment);
				permissionAttachment = null;
			}
		}
	}

	public ArrayList<Punishment> getPunishments() {
		return punishments;
	}

	public void addPunishment(Punishment punishment) {
		this.punishments.add(punishment);
	}

	public boolean removePunishment(int ID) {
		for (Punishment punishment : punishments) {
			if (punishment.getID() == ID) {
				punishments.remove(punishment);
				return true;
			}
		}
		return false;
	}

	public boolean isMuted() {
		if (muted == null) return true;
		return muted > Instant.now().getEpochSecond();
	}

	public void setMuted(Integer muted) {
		this.muted = muted;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ItemStack[] getVault() {
		ItemStack[] items = new ItemStack[54];

		for (int i = 0; i < 54; i++) {
			items[i] = vault[i] == null ? null : ItemStack.deserializeBytes(vault[i]);
		}

		return items;
	}

	public void setVault(ItemStack[] vault) {
		for (int i = 0; i < 54; i++) {
			this.vault[i] = vault[i] == null ? null : vault[i].serializeAsBytes();
		}
	}

	public Integer getSelectedNPC() {
		return selectedNPC;
	}

	public void setSelectedNPC(Integer selectedNPC) {
		this.selectedNPC = selectedNPC;
	}

	public NPCInteraction getCurrentInteraction() {
		return currentInteraction;
	}

	public void setCurrentInteraction(NPCInteraction currentInteraction) {
		this.currentInteraction = currentInteraction;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;

		Player player = Bukkit.getPlayer(uuid);
		if (player != null) {
			player.setLevel(level);
			player.setExp(0f);
		}
		if (!GlobalData.leveltop.containsKey(player))
			GlobalData.leveltop.put(player, (double) getLevel());
		else
			GlobalData.leveltop.replace(player, (double) getLevel());
	}

	public void addLevel(int level) {
		setLevel(this.level + level);
	}

	public void removeLevel(int level) {
		setLevel(this.level - level);
	}

	public double getXp() {
		return xp;
	}

	public void setXp(double xp) {
		this.xp = xp;
		if (this.xp >= xpCap()) {
			addLevel(1);
			this.xp = 0;

			Player player = Bukkit.getPlayer(uuid);
			if (player != null) {
				player.sendMessage(TextUtil.c("&eYou leveled up to level &6" + level));
			}
		} else {
			Player player = Bukkit.getPlayer(uuid);
			if (player != null) {
				player.setExp((float) (xp / xpCap()));
			}
		}
	}

	private double xpCap() {
		return level * 1000;
	}

	public void addXp(double xp) {
		setXp(this.xp + xp);
	}

	public void removeXp(double xp) {
		setXp(this.xp - xp);
	}
}
