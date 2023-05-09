package me.lord.poscbox.utilities;

public final class NumberUtil {
	public static boolean equalsRange(int i, int min, int max) {
		for (int j = min; j < max; j++) {
			if (i == j) {
				return true;
			}
		}
		return false;
	}
}
