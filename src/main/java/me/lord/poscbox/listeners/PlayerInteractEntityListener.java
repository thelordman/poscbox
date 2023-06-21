package me.lord.poscbox.listeners;

import me.lord.poscbox.data.DataManager;
import me.lord.poscbox.npc.NPC;
import me.lord.poscbox.npc.NPCManager;
import me.lord.poscbox.npc.interaction.NPCInteraction;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntityListener implements Listener {
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		Entity entity = event.getRightClicked();

		if (entity instanceof Interaction) {

			NPC npc = NPCManager.getNPCMap().values().stream()
					.filter(n -> n.getInteractionId() == entity.getUniqueId())
					.findFirst()
					.orElse(null);

			if (npc != null) {
				NPCInteraction npcInteraction = NPCInteraction.create(npc);
				if (npcInteraction != null) {
					NPCInteraction currentInteraction = DataManager.getPlayerData(player).getCurrentInteraction();
					NPC currentInteractionNPC = null;
					if (currentInteraction != null) {
						currentInteractionNPC = currentInteraction.getNPC();
					}
					if (currentInteractionNPC != npc) {
						npcInteraction.setPlayer(player);
						npcInteraction.setNpc(npc);
						npcInteraction.callEvent();
					}
				}
			}
		}
	}
}
