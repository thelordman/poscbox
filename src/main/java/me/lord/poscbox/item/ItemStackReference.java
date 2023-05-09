package me.lord.poscbox.item;

import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.lang.ref.WeakReference;

public abstract class ItemStackReference implements Serializable {
	private transient WeakReference<ItemStack> itemRef;

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

	@Serial
	private void writeObject(java.io.ObjectOutputStream stream) throws IOException {
		stream.defaultWriteObject();
		stream.writeObject(getRef().serializeAsBytes());
	}

	@Serial
	private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
		stream.defaultReadObject();
		itemRef = new WeakReference<>(ItemStack.deserializeBytes((byte[]) stream.readObject()));
	}
}
