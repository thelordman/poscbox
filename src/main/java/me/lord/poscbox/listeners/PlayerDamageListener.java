package me.lord.poscbox.listeners;

import me.lord.poscbox.utilities.CombatLog;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player attacker && event.getEntity() instanceof Player victim) {
            if (event.getEntity().getLocation().getY() < -25 && !CombatLog.inCombat(victim)) {
                event.setCancelled(true);
                return;
            }
            CombatLog.setCombat(attacker);
            CombatLog.setCombat(victim);
        }
    }
}
