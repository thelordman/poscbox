package me.lord.poscbox.item;

import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

public abstract class ItemStackReference implements Serializable {
	private transient ItemStack itemRef;

	protected ItemStackReference(ItemStack item) {
		itemRef = item;
	}

	public ItemStack getRef() {
		return itemRef;
	}

	@Serial
	private void writeObject(java.io.ObjectOutputStream stream) throws IOException {
		stream.defaultWriteObject();
		stream.writeObject(getRef().serializeAsBytes());
	}

	@Serial
	private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
		stream.defaultReadObject();
		itemRef = ItemStack.deserializeBytes((byte[]) stream.readObject());
	}
}
