package me.lord.poscbox.utilities;

import java.util.Arrays;

public final class ArrayUtil {
	public static <T> T[] copyOf(T[] original, int newLength, T... objects) {
		if (objects.length + original.length > newLength) {
			throw new IndexOutOfBoundsException("Amount of objects (" + objects.length + ") exceeds new length (" + newLength + ")");
		}

		T[] array = Arrays.copyOf(original, newLength);

		int i = original.length;
		for (T t : objects) {
			array[i] = t;
			i++;
		}

		return array;
	}
}
