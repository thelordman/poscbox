package me.lord.poscbox.item.data;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EquipmentItemData extends ItemData {
	public EquipmentItemData(ItemStack item) {
		super(item);
		addTag(Tag.ENCHANTABLE);
		addTag(Tag.UPGRADEABLE);
		addTag(Tag.LOSABLE_10_LEVELS_LOWER);
	}

	@Override
	public void onRightClick(Player player) {

	}
}
