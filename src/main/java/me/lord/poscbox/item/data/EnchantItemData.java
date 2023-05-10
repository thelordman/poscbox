package me.lord.poscbox.item.data;

import org.bukkit.inventory.ItemStack;

public class EnchantItemData extends ItemData {
	private final String key;

	public EnchantItemData(ItemStack item, String key) {
		super(item);
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
