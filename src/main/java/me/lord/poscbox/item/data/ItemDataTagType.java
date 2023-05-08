package me.lord.poscbox.item.data;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class ItemDataTagType implements PersistentDataType<byte[], ItemData> {

	@Override
	public @NotNull Class<byte[]> getPrimitiveType() {
		return byte[].class;
	}

	@Override
	public @NotNull Class<ItemData> getComplexType() {
		return ItemData.class;
	}

	@Override
	public byte @NotNull [] toPrimitive(@NotNull ItemData complex, @NotNull PersistentDataAdapterContext context) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
			objectOutputStream.writeObject(complex);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byteArrayOutputStream.toByteArray();
	}

	@Override
	public @NotNull ItemData fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
		ByteArrayInputStream byteArrayOutputStream = new ByteArrayInputStream(primitive);
		try (ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayOutputStream)) {
			return (ItemData) objectInputStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("Something went horribly, horribly wrong...");
	}
}
