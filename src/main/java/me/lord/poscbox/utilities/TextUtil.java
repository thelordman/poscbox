package me.lord.poscbox.utilities;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;

import java.text.DecimalFormat;
import java.util.Arrays;

public final class TextUtil {
	public static Component c(String string) {
		return LegacyComponentSerializer.legacyAmpersand().deserialize("&r" + string);
	}
	public static String cs(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}

	public static Component nl() {
		return c("&r\n");
	}

	public static Component empty() {
		return c("&r");
	}

	public static String componentToString(Component component) {
		return LegacyComponentSerializer.legacyAmpersand().serialize(component);
	}

	public static String stripColorCodes(String string) {
		return string.replaceAll("§[4c6e2ab319d5f780klmnor]", "");
	}

	public static String stripColorCodes(String string, char c) {
		return string.replaceAll(c + "[4c6e2ab319d5f780klmnor]", "");
	}

	public static String formatMoney(double number) {
		return new DecimalFormat("$#,###.##").format(number);
	}

	public static String format(int number) {
		return new DecimalFormat("#,###").format(number);
	}

	public static String format(double number) {
		return new DecimalFormat("#,###.##").format(number);
	}

	public static String ordinal(int number) {
		String string = format(number);
		if (number >= 111) number = Integer.parseInt(Character.toString(string.charAt(string.length() - 2) + string.charAt(string.length() - 1)));
		if (number >= 11 && number <= 13) return string + "th";
		return switch (string.charAt(string.length() - 1)) {
			case '1' -> string + "st";
			case '2' -> string + "nd";
			case '3' -> string + "rd";
			default -> string + "th";
		};
	}

	public static String sexyLocation(Location location) {
		return Math.round(location.x()) + ", " + Math.round(location.y()) + ", " + Math.round(location.z());
	}

	public static String sexyString(String string) {
		return Character.toUpperCase(string.charAt(0)) + string.substring(1);
	}

	public static boolean isNumber(String string) {
		try {
			Integer.parseInt(string);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	public static String joinArray(String[] strings, int from) {
		return String.join(" ", Arrays.copyOfRange(strings, from, strings.length));
	}

	public static String toRoman(int number) {
		StringBuilder builder = new StringBuilder();
		int repeat;
		String[] romans = new String[]{"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};
		int[] ints = new int[]{1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
		for (int i = ints.length - 1; i >= 0; i--) {
			repeat = number / ints[i];
			number %= ints[i];
			while (repeat > 0) {
				builder.append(romans[i]);
				repeat--;
			}
		}
		return builder.toString();
	}

	public static boolean isAny(String string, String... strings) {
		for (String str : strings) {
			if (str.equals(string)) {
				return true;
			}
		}
		return false;
	}

	public static String enchantmentName(String key) {
		if (key.equals("sweeping")) {
			return "Sweeping Edge";
		}

		char[] chars = key.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == '_' || i == 0) {
				if (chars[i] == '_') {
					chars[i] = ' ';
				}
				chars[i == 0 ? 0 : i + 1] = Character.toUpperCase(chars[i == 0 ? 0 : i + 1]);
			}
		}
		return String.valueOf(chars);
	}
}
