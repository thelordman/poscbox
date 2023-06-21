package me.lord.poscbox.item.data;

import me.lord.poscbox.item.enchant.Enchant;
import me.lord.poscbox.utilities.ArrayUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.Serial;

public class BootsItemData extends ArmorItemData {
	@Serial
	private static final long serialVersionUID = 6184501224419028043L;

	public BootsItemData(ItemStack item) {
		super(item);
		enchants = new Enchant[6];
	}

	@Override
	public String[] getPossibleEnchantKeys() {
		return ArrayUtil.copyOf(super.getPossibleEnchantKeys(), super.getPossibleEnchantKeys().length + 1, "swift_sneak");
	}

	@Override
	public Material getMaxType() {
		return Material.NETHERITE_BOOTS;
	}

	@Override
	public Material getNextType() {
		return switch (getRef().getType()) {
			case CHAINMAIL_BOOTS -> Material.IRON_BOOTS;
			case IRON_BOOTS -> Material.DIAMOND_BOOTS;
			default -> Material.NETHERITE_BOOTS;
		};
	}

	@Override
	public double getNextTypeCost() {
		return switch (getNextType()) {
			case IRON_BOOTS -> 5000d;
			case DIAMOND_BOOTS -> 15000d;
			case NETHERITE_BOOTS -> 50000d;
			default -> 0d;
		};
	}
}
