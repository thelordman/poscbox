package me.lord.poscbox.item.data;

import org.bukkit.inventory.ItemStack;

import java.io.Serial;

public class PickaxeItemData extends EquipmentItemData {
	@Serial
	private static final long serialVersionUID = 3638815038397925583L;

	public PickaxeItemData(ItemStack item) {
		super(item);
	}

	@Override
	public String[] getPossibleEnchantKeys() {
		return new String[]{"efficiency"};
	}
}
