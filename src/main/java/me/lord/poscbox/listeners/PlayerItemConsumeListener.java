package me.lord.poscbox.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.meta.PotionMeta;

public class PlayerItemConsumeListener implements Listener {

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() == Material.POTION) {
            PotionMeta potionMeta = (PotionMeta) event.getItem().getItemMeta();
            if (potionMeta.getBasePotionData().getType().name().equalsIgnoreCase("WATER")) {
                event.getPlayer().setFireTicks(0);
            }
        }
    }
}
