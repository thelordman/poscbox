package me.lord.poscbox.item;

import org.bukkit.inventory.ItemStack;

import java.lang.ref.WeakReference;

public abstract class ItemStackReference {
	private final WeakReference<ItemStack> itemRef;

	protected ItemStackReference(ItemStack item) {
		itemRef = new WeakReference<>(item);
	}

	public ItemStack getRef() {
		if (itemRef.refersTo(null)) {
			throw new IllegalStateException("Tried to access item reference which was cleared");
		} else {
			return itemRef.get();
		}
	}
}
