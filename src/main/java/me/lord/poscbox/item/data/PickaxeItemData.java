package me.lord.poscbox.item.data;

import org.bukkit.Material;
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

	@Override
	public Material getMaxType() {
		return Material.NETHERITE_PICKAXE;
	}

	@Override
	public Material getNextType() {
		return switch (getRef().getType()) {
			case WOODEN_PICKAXE -> Material.STONE_PICKAXE;
			case STONE_PICKAXE -> Material.IRON_PICKAXE;
			case IRON_PICKAXE -> Material.DIAMOND_PICKAXE;
			default -> Material.NETHERITE_PICKAXE;
		};
	}

	@Override
	public double getNextTypeCost() {
		return switch (getNextType()) {
			case STONE_PICKAXE -> 2500d;
			case IRON_PICKAXE -> 10000d;
			case DIAMOND_PICKAXE -> 50000d;
			case NETHERITE_PICKAXE -> 250000d;
			default -> 0d;
		};
	}
}
