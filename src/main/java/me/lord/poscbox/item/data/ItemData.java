package me.lord.poscbox.item.data;

import io.netty.util.IllegalReferenceCountException;
import me.lord.poscbox.item.ItemManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.Serial;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.EnumSet;

public abstract class ItemData implements Serializable {
	@Serial
	private static final long serialVersionUID = 3868880100266558073L;

	private final WeakReference<ItemStack> itemRef;

	protected EnumSet<Tag> tags;

	public void onRightClick(Player player) {

	}

	public ItemStack getRef() {
		if (itemRef.refersTo(null)) {
			throw new IllegalStateException("Tried to access item reference which was cleared");
		} else {
			return itemRef.get();
		}
	}

	public Material getMaxType() {
		return getRef().getType();
	}

	public boolean isMax() {
		return getRef().getType() == getMaxType();
	}

	public ItemData(ItemStack item) {
		itemRef = new WeakReference<>(item);
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
			case ENCHANTABLE -> addendum += "Right click to enchant";
			case UPGRADEABLE -> {
				if (getRef().getType() != getMaxType()) {
					addendum += "Material upgradeable on Smithing Table";
				}
			}
			case UNLOSABLE -> addendum += "Doesn't drop on death";
			case LOSABLE_10_LEVELS_LOWER -> addendum += "Drops on death to players that are 10 levels lower";
			case UNDROPPABLE -> addendum += "Cannot be dropped";
		}
		ItemManager.addLore(getRef(), "", addendum);
		tags.add(tag);
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
