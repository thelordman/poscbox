package me.lord.poscbox.item.data;

import me.lord.poscbox.utilities.ArrayUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BootsItemData extends ArmorItemData {
	public BootsItemData(ItemStack item) {
		super(item);
	}

	@Override
	public String[] getPossibleEnchantKeys() {
		return ArrayUtil.copyOf(super.getPossibleEnchantKeys(), super.getPossibleEnchantKeys().length + 1, "swift_sneak");
	}

	@Override
	public Material getMaxType() {
		return super.getMaxType();
	}
}
