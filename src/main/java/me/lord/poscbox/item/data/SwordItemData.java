package me.lord.poscbox.item.data;

import me.lord.poscbox.PoscBox;
import org.bukkit.Material;
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

	@Override
	public Material getMaxType() {
		return Material.NETHERITE_SWORD;
	}

	@Override
	public Material getNextType() {
		return switch (getRef().getType()) {
			case STONE_SWORD -> Material.IRON_SWORD;
			case IRON_SWORD -> Material.DIAMOND_SWORD;
			default -> Material.NETHERITE_SWORD;
		};
	}

	@Override
	public double getNextTypeCost() {
		return switch (getNextType()) {
			case IRON_SWORD -> 10000d;
			case DIAMOND_SWORD -> 50000d;
			case NETHERITE_SWORD -> 250000d;
			default -> 0d;
		};
	}
}
