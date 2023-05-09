package me.lord.poscbox.item.data;

import org.bukkit.inventory.ItemStack;

import java.io.Serial;

public class SwordItemData extends EquipmentItemData {
	@Serial
	private static final long serialVersionUID = 5286930572355333210L;

	public SwordItemData(ItemStack item) {
		super(item);
	}

	@Override
	public String[] getPossibleEnchantKeys() {
		return new String[]{"sharpness", "knockback", "sweeping",
							"fire_aspect"};
	}
}
