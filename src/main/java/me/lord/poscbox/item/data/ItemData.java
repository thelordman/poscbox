package me.lord.poscbox.item.data;

import me.lord.poscbox.item.ItemManager;
import me.lord.poscbox.item.ItemStackReference;
import me.lord.poscbox.item.enchant.Enchant;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.EnumSet;

public abstract class ItemData extends ItemStackReference implements Serializable {
	protected EnumSet<Tag> tags;

	protected Enchant[] enchants = new Enchant[getPossibleEnchantKeys().length];

	public void openEnchanter(Player player) {

	}

	public void openUpgrader(Player player) {

	}

	public String[] getPossibleEnchantKeys() {
		return new String[0];
	}

	public Enchant[] getEnchants() {
		return enchants;
	}

	public void setEnchants(Enchant[] enchants) {
		this.enchants = enchants;
	}

	public void setEnchant(Enchant enchant, int index) {
		enchants[index] = enchant;
	}

	public Material getMaxType() {
		return getRef().getType();
	}

	public boolean isMax() {
		return getRef().getType() == getMaxType();
	}

	public Material getNextType() {
		return getRef().getType();
	}

	public double getNextTypeCost() {
		return 0d;
	}

	public ItemData(ItemStack item) {
		super(item);
		tags = EnumSet.noneOf(Tag.class);
	}

	public EnumSet<Tag> getTags() {
		return tags;
	}

	public boolean hasTag(Tag tag) {
		return tags.contains(tag);
	}

	public void addTag(Tag tag) {
		String addendum = "&7• ";
		switch (tag) {
			case ENCHANTABLE -> addendum += "Enchantable";
			case UPGRADEABLE -> {
				if (getRef().getType() != getMaxType()) {
					addendum += "Upgradeable";
				}
			}
			case UNLOSABLE -> addendum += "Doesn't drop on death";
			case LOSABLE_10_LEVELS_LOWER -> addendum += "Drops on death to players that are 10 levels lower";
			case UNDROPPABLE -> addendum += "Cannot be dropped";
		}
		if (!addendum.equals("&7• ")) {
			ItemManager.addLore(getRef(), addendum);
			tags.add(tag);
		}
	}

	public enum Tag {
		ENCHANTABLE,
		UPGRADEABLE,
		UNLOSABLE,
		LOSABLE_10_LEVELS_LOWER,
		UNDROPPABLE,
		DROPPABLE_WITH_CROUCH
	}
}
