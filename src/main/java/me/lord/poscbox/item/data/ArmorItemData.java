package me.lord.poscbox.item.data;

import org.bukkit.inventory.ItemStack;

public abstract class ArmorItemData extends EquipmentItemData {
	public ArmorItemData(ItemStack item) {
		super(item);
	}

	@Override
	public String[] getPossibleEnchantKeys() {
		return new String[]{"protection", "fire_protection", "projectile_protection",
							"blast_protection", "thorns"};
	}
}
