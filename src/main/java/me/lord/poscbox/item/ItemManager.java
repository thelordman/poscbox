package me.lord.poscbox.item;

import me.lord.poscbox.PoscBox;
import me.lord.poscbox.item.data.*;
import me.lord.poscbox.utilities.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public final class ItemManager {
	public static ItemData getData(ItemStack item) {
		return item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(PoscBox.get(), "item_data"), new ItemDataTagType());
	}

	public static void setData(ItemStack item, ItemData data) {
		item.getItemMeta().getPersistentDataContainer().set(new NamespacedKey(PoscBox.get(), "item_data"), new ItemDataTagType(), data);
	}

	public static void modifyData(ItemStack item, Consumer<ItemData> consumer) {
		ItemData data = getData(item);
		consumer.accept(data);
		setData(item, data);
	}

	public static ItemStack item(Material material, int amount, String name, String... lore) {
		ItemStack item = new ItemStack(material, amount);
		ItemMeta meta = item.getItemMeta();
		if (name != null) {
			meta.displayName(TextUtil.c(name));
		}
		meta.lore(Arrays.stream(lore).map(TextUtil::c).toList());
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack item(Material material, String... lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.lore(Arrays.stream(lore).map(TextUtil::c).toList());
		item.setItemMeta(meta);
		return item;
	}

	public static void addLore(ItemStack item, String... strings) {
		List<Component> lore = item.lore() == null ? new ArrayList<>() : item.lore();
		for (String string : strings) {
			lore.add(TextUtil.c(string));
		}
		item.lore(lore);
	}

	public static void joinKit(Player player) {
		ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
		ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
		ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
		ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);

		ItemStack sword = new ItemStack(Material.STONE_SWORD);

		ItemStack bread = new ItemStack(Material.BREAD, 64);

		setData(helmet, new HelmetItemData(helmet));
		setData(chestplate, new ChestplateItemData(chestplate));
		setData(leggings, new LeggingsItemData(leggings));
		setData(boots, new BootsItemData(boots));

		setData(sword, new SwordItemData(sword));

		player.getInventory().setHelmet(helmet);
		player.getInventory().setChestplate(chestplate);
		player.getInventory().setLeggings(leggings);
		player.getInventory().setBoots(boots);

		player.getInventory().setItem(0, sword);

		player.getInventory().setItem(1, bread);
	}
}
