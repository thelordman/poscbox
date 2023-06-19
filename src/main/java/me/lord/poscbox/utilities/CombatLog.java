package me.lord.poscbox.utilities;

import me.lord.poscbox.PoscBox;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class CombatLog {

    private static HashMap<Player, Integer> combat = new HashMap<>();

    public static void setCombat(Player player) {
        if (combat.containsKey(player)) {
            combat.replace(player, 5);
            return;
        }
        player.sendActionBar(TextUtil.c("&cYou are now in combat!"));
        combat.put(player, 5);
        new BukkitRunnable() {
            @Override
            public void run() {
                combat.replace(player, combat.get(player) - 1);
                if (combat.get(player) == 0) {
                    player.sendActionBar(TextUtil.c("&aYou are no longer in combat!"));
                    cancel();
                }
            }
        }.runTaskTimer(PoscBox.get(), 0, 20);
    }

    public static boolean inCombat(Player player) {
        return combat.containsKey(player);
    }
}
