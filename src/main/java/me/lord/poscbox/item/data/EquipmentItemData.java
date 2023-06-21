package me.lord.poscbox.item.data;

import me.lord.poscbox.gui.EnchanterGUI;
import me.lord.poscbox.gui.UpgraderGUI;
import me.lord.poscbox.item.ItemManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class EquipmentItemData extends ItemData {

	public EquipmentItemData(ItemStack item) {
		super(item);
		ItemManager.addLore(getRef(), "");
		addTag(Tag.ENCHANTABLE);
		addTag(Tag.UPGRADEABLE);
		addTag(Tag.LOSABLE_10_LEVELS_LOWER);
	}

	@Override
	public void openEnchanter(Player player) {
		new EnchanterGUI().open(player);
	}

	@Override
	public void openUpgrader(Player player) {
		new UpgraderGUI().open(player);
	}
}
