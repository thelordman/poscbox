package me.lord.poscbox.npc.interaction;

import me.lord.poscbox.data.DataManager;
import me.lord.poscbox.item.ItemManager;
import me.lord.poscbox.item.data.ItemData;
import org.bukkit.inventory.ItemStack;

@NPCIndex(id = 0)
public class EnchanterNPCInteraction extends NPCInteraction {
	@Override
	public void callEvent() {
		DataManager.getPlayerData(getPlayer()).setCurrentInteraction(this);

		ItemStack item = getPlayer().getInventory().getItemInMainHand();

		if (item.getItemMeta() != null) {
			ItemData data = ItemManager.getData(item);
			if (data != null) {
				data.openEnchanter(getPlayer());
			}
		}

		DataManager.getPlayerData(getPlayer()).setCurrentInteraction(null);
	}
}
