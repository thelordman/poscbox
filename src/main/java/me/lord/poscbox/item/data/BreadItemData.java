package me.lord.poscbox.item.data;

import org.bukkit.inventory.ItemStack;

public class BreadItemData extends ItemData {
	public BreadItemData(ItemStack item) {
		super(item);
		addTag(Tag.UNLOSABLE);
	}
}
