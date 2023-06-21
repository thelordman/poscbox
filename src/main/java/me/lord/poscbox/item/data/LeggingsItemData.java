package me.lord.poscbox.item.data;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.Serial;

public class LeggingsItemData extends ArmorItemData {
	@Serial
	private static final long serialVersionUID = 5164413776691739052L;

	public LeggingsItemData(ItemStack item) {
		super(item);
	}

	@Override
	public Material getMaxType() {
		return Material.NETHERITE_LEGGINGS;
	}

	@Override
	public Material getNextType() {
		return switch (getRef().getType()) {
			case CHAINMAIL_LEGGINGS -> Material.IRON_LEGGINGS;
			case IRON_LEGGINGS -> Material.DIAMOND_LEGGINGS;
			default -> Material.NETHERITE_LEGGINGS;
		};
	}

	@Override
	public double getNextTypeCost() {
		return switch (getNextType()) {
			case IRON_LEGGINGS -> 7500d;
			case DIAMOND_LEGGINGS -> 25000d;
			case NETHERITE_LEGGINGS -> 75000d;
			default -> 0d;
		};
	}
}
