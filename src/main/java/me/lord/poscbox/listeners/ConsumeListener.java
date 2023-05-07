package me.lord.poscbox.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.meta.PotionMeta;

public class ConsumeListener implements Listener {

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() == Material.POTION) {
            PotionMeta potionMeta = (PotionMeta) event.getItem().getItemMeta();
            if (potionMeta.getCustomEffects().get(0) == null)
                event.getPlayer().setFireTicks(0);
        }
    }
}
