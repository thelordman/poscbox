package me.lord.poscbox.item.data;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.Serial;

public class ChestplateItemData extends ArmorItemData {
	@Serial
	private static final long serialVersionUID = 1110258518053644368L;

	public ChestplateItemData(ItemStack item) {
		super(item);
	}

	@Override
	public Material getMaxType() {
		return Material.NETHERITE_CHESTPLATE;
	}

	@Override
	public Material getNextType() {
		return switch (getRef().getType()) {
			case CHAINMAIL_CHESTPLATE -> Material.IRON_CHESTPLATE;
			case IRON_CHESTPLATE -> Material.DIAMOND_CHESTPLATE;
			default -> Material.NETHERITE_CHESTPLATE;
		};
	}

	@Override
	public double getNextTypeCost() {
		return switch (getNextType()) {
			case IRON_CHESTPLATE -> 7500d;
			case DIAMOND_CHESTPLATE -> 25000d;
			case NETHERITE_CHESTPLATE -> 75000d;
			default -> 0d;
		};
	}
}
