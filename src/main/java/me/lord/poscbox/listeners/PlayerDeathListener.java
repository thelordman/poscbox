package me.lord.poscbox.listeners;

import me.lord.poscbox.PoscBox;
import me.lord.poscbox.data.DataManager;
import me.lord.poscbox.data.PlayerData;
import me.lord.poscbox.discord.events.PlayerDeath;
import me.lord.poscbox.item.ItemManager;
import me.lord.poscbox.item.data.ItemData;
import me.lord.poscbox.mine.MineManager;
import me.lord.poscbox.utilities.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDeathListener implements Listener {
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player victim = event.getPlayer();
		Player attacker = victim.getKiller();

		// GameRule SHOW_DEATH_MESSAGES must be set to false, or it will duplicate
		Bukkit.broadcast(TextUtil.c("&6&lDeath &7| &e").append(event.deathMessage()));

		PlayerData victimData = DataManager.getPlayerData(victim);

		victimData.resetKillstreak();

		victimData.getScoreboard().updateDeaths();
		victimData.getScoreboard().updateKDR();
		victimData.getScoreboard().updateKillstreak();
		if (attacker != null) {
			for (ItemStack item : victim.getInventory().getContents()) {
				if (item == null) {
					continue;
				}

				ItemData itemData = ItemManager.getData(item);

				if (itemData != null) {
					if (itemData.hasTag(ItemData.Tag.UNLOSABLE)) {
						continue;
					}
					if (itemData.hasTag(ItemData.Tag.LOSABLE_10_LEVELS_LOWER)) {
						if (DataManager.getPlayerData(victim).getLevel() - DataManager.getPlayerData(attacker).getLevel() >= 10) {
							PoscBox.mainWorld.dropItemNaturally(victim.getLocation(), item);
							victimData.setDroppedEquipment(true);
						}
					}
				}
			}

			PlayerData attackerData = DataManager.getPlayerData(attacker);

			double reward = 100d;
			reward += Math.max((victimData.getLevel() - attackerData.getLevel()) * 100d, 0d);
			reward += victimData.getBalance() * 0.05d;
			if (reward < 0d) {
				reward = 0d;
			}

			victimData.removeBalance(reward);
			if (victimData.getBalance() < 0d) {
				victimData.setBalance(0d);
			}

			reward *= 1 + ((double) attackerData.getKillstreak()) / 10d;

			attackerData.addBalance(reward * (MineManager.donatorMulti(attacker) + 1));
			attackerData.addXp(reward * MineManager.donatorMulti(attacker));
			attacker.sendActionBar(TextUtil.c("&f+" + TextUtil.formatMoney(reward * (MineManager.donatorMulti(attacker) + 1)) + " &7(" + TextUtil.format(MineManager.donatorMulti(attacker) + 1) + "x) &8| &f+" + TextUtil.format(reward * (MineManager.donatorMulti(attacker) + 1)) + "xp &7(" + TextUtil.format(MineManager.donatorMulti(attacker) + 1) + "x)"));

			attacker.setHealth(Math.min(attacker.getHealth() + 6d, 20d));

			attackerData.incrementKillstreak();

			attackerData.getScoreboard().updateKills();
			attackerData.getScoreboard().updateKDR();
			attackerData.getScoreboard().updateKillstreak();
		}

		PlayerDeath.exe(event);
	}
}
