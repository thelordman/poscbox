package me.lord.poscbox.item.data;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.Serial;

public class HelmetItemData extends ArmorItemData {
	@Serial
	private static final long serialVersionUID = 525242639198026872L;

	public HelmetItemData(ItemStack item) {
		super(item);
	}

	@Override
	public Material getMaxType() {
		return Material.NETHERITE_HELMET;
	}

	@Override
	public Material getNextType() {
		return switch (getRef().getType()) {
			case CHAINMAIL_HELMET -> Material.IRON_HELMET;
			case IRON_HELMET -> Material.DIAMOND_HELMET;
			default -> Material.NETHERITE_HELMET;
		};
	}

	@Override
	public double getNextTypeCost() {
		return switch (getNextType()) {
			case IRON_HELMET -> 5000d;
			case DIAMOND_HELMET -> 15000d;
			case NETHERITE_HELMET -> 50000d;
			default -> 0d;
		};
	}
}
