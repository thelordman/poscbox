package me.lord.poscbox.listeners;

import me.lord.poscbox.PoscBox;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity().getType() == EntityType.PLAYER && event.getDamager().getType() == EntityType.PLAYER) {
            Player player = (Player) event.getEntity();
            Player attacker = (Player) event.getDamager();
            Material weapon = attacker.getInventory().getItemInMainHand().getType();
            if (player.isBlocking() && player.getCooldown(Material.SHIELD) == 0 && (weapon == Material.WOODEN_SWORD || weapon == Material.STONE_SWORD || weapon == Material.GOLDEN_SWORD || weapon == Material.IRON_SWORD || weapon == Material.DIAMOND_SWORD || weapon == Material.NETHERITE_SWORD)) {
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        player.setCooldown(Material.SHIELD, 40);
                        ItemStack itemStack = player.getInventory().getItemInOffHand();
                        player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                player.getInventory().setItemInOffHand(itemStack);
                            }
                        }.runTaskLater(PoscBox.get(), 1);
                    }
                }.runTaskLater(PoscBox.get(), 1);
            }
        }
    }
}
