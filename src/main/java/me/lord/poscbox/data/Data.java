package me.lord.poscbox.data;

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
			return (Data) inputStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}
}
