package me.lord.poscbox.data;

import org.bukkit.Bukkit;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * The superclass of all persistent data storing classes.
 */
public interface Data extends Serializable {
	default void serialize(String path) {
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(path)))) {
			outputStream.writeObject(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static Data deserialize(String path) {
		try (ObjectInputStream inputStream = new ObjectInputStream(new GZIPInputStream(new FileInputStream(path)))) {
			Data data = (Data) inputStream.readObject();
			if (data instanceof PlayerData playerData) {
				if (Bukkit.getPlayer(playerData.getUUID()) != null) {
					playerData.initPermissions();
					playerData.setAddress(Bukkit.getPlayer(playerData.getUUID()).getAddress().getAddress().getHostAddress());
				}
				return playerData;
			}
			return data;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
