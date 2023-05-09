package me.lord.poscbox.item.enchant;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

public interface Enchant extends Serializable {
	static Enchant of(ItemStack item, String key) {
		Enchant enchant;

		Enchantment vanilla = Enchantment.getByKey(NamespacedKey.minecraft(key));
		/* if (vanilla == null) {
			enchant = switch (key) {
				default -> null;
			};
		}
		*/
		enchant = VanillaEnchant.isValid(key) ? new VanillaEnchant(item, vanilla) : null;

		return enchant;
	}

	String getName();

	default int getLevel() {
		return 1;
	}

	default Material getIcon() {
		return Material.ENCHANTED_BOOK;
	}

	void setLevel(int level);

	default void incrementLevel() {
		setLevel(getLevel() + 1);
	}

	default int getMaxLevel() {
		return 1;
	}

	String getKey();

	double getCost(int level);
}
