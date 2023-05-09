package me.lord.poscbox.item.enchant;

import me.lord.poscbox.item.ItemStackReference;
import me.lord.poscbox.utilities.TextUtil;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.io.Serial;

public class VanillaEnchant extends ItemStackReference implements Enchant {
	@Serial
	private static final long serialVersionUID = 7228328996987764185L;

	private int level;
	private final String key;

	public static boolean isValid(String key) {
		if (TextUtil.isAny(key, "sharpness", "knockback", "sweeping",
				"fire_aspect", "protection", "fire_protection",
				"projectile_protection", "blast_protection", "thorns",
				"swift_sneak")) {
			return true;
		}
		return false;
	}

	public VanillaEnchant(ItemStack item, Enchantment enchantment) {
		super(item);
		key = enchantment.getKey().getKey();
		level = enchantment.getStartLevel();
	}

	@Override
	public String getName() {
		return TextUtil.enchantmentName(key);
	}

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public void setLevel(int level) {
		this.level = level;
		getRef().addUnsafeEnchantment(getVanilla(), level);
	}

	@Override
	public int getMaxLevel() {
		return getVanilla().getMaxLevel() + 1;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public double getCost(int level) {
		return switch (key) {
			case "sharpness" ->
					switch (level) {
						case 1 -> 100d;
						case 2 -> 500d;
						case 3 -> 1000d;
						case 4 -> 5000d;
						case 5 -> 10000d;
						case 6 -> 25000d;
						default -> 0d;
					};
			case "knockback" ->
					switch (level) {
						case 1 -> 200d;
						case 2 -> 1000d;
						case 3 -> 5000d;
						default -> 0d;
					};
			case "sweeping" ->
					switch (level) {
						case 1 -> 1000d;
						case 2 -> 5000d;
						case 3 -> 10000d;
						case 4 -> 50000d;
						default -> 0d;
					};
			case "fire_aspect" ->
					switch (level) {
						case 1 -> 10000d;
						case 2 -> 50000d;
						case 3 -> 100000d;
						default -> 0d;
					};
			case "protection" ->
					switch (level) {
						case 1 -> 500d;
						case 2 -> 1000d;
						case 3 -> 5000d;
						case 4 -> 10000d;
						case 5 -> 25000d;
						default -> 0d;
					};
			case "fire_protection" ->
					switch (level) {
						case 1 -> 1500d;
						case 2 -> 3500d;
						case 3 -> 7500d;
						case 4 -> 15000d;
						case 5 -> 35000d;
						default -> 0d;
					};
			case "projectile_protection" ->
					switch (level) {
						case 1 -> 3000d;
						case 2 -> 6000d;
						case 3 -> 12500d;
						case 4 -> 20000d;
						case 5 -> 45000d;
						default -> 0d;
					};
			case "blast_protection" ->
					switch (level) {
						case 1 -> 5000d;
						case 2 -> 7500d;
						case 3 -> 15000d;
						case 4 -> 25000d;
						case 5 -> 50000d;
						default -> 0d;
					};
			case "thorns" ->
					switch (level) {
						case 1 -> 10000d;
						case 2 -> 25000d;
						case 3 -> 50000d;
						case 4 -> 100000d;
						default -> 0d;
					};
			case "swift_sneak" ->
					switch (level) {
						case 1 -> 15000d;
						case 2 -> 35000d;
						case 3 -> 75000d;
						case 4 -> 125000d;
						default -> 0d;
					};
			default -> throw new RuntimeException("Illegal enchant key");
		};
	}

	public Enchantment getVanilla() {
		return Enchantment.getByKey(NamespacedKey.minecraft(key));
	}
}
