package me.lord.poscbox.item;

import ca.spottedleaf.dataconverter.types.nbt.NBTListType;
import me.lord.poscbox.PoscBox;
import me.lord.poscbox.item.data.*;
import me.lord.poscbox.utilities.TextUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
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
		ItemMeta meta = item.getItemMeta();
		meta.getPersistentDataContainer().set(new NamespacedKey(PoscBox.get(), "item_data"), new ItemDataTagType(), data);
		item.setItemMeta(meta);
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
			lore.add(TextUtil.c(string).decoration(TextDecoration.ITALIC, false));
		}
		item.lore(lore);
	}

	public static void joinKit(Player player) {
		ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
		helmet.editMeta(meta -> meta.setUnbreakable(true));
		ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
		chestplate.editMeta(meta -> meta.setUnbreakable(true));
		ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
		leggings.editMeta(meta -> meta.setUnbreakable(true));
		ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
		boots.editMeta(meta -> meta.setUnbreakable(true));

		ItemStack sword = new ItemStack(Material.STONE_SWORD);
		sword.editMeta(meta -> meta.setUnbreakable(true));

		ItemStack pickaxe = new ItemStack(Material.WOODEN_PICKAXE);
		net.minecraft.world.item.ItemStack nmsPickaxe = CraftItemStack.asNMSCopy(pickaxe);
		ListTag listTag = new ListTag();
		listTag.add(StringTag.valueOf("minecraft:cobblestone"));
		listTag.add(StringTag.valueOf("minecraft:stone"));
		listTag.add(StringTag.valueOf("minecraft:deepslate"));
		listTag.add(StringTag.valueOf("minecraft:coal_ore"));
		listTag.add(StringTag.valueOf("minecraft:deepslate_coal_ore"));

		listTag.add(StringTag.valueOf("minecraft:iron_ore"));
		listTag.add(StringTag.valueOf("minecraft:deepslate_iron_ore"));
		listTag.add(StringTag.valueOf("minecraft:lapis_ore"));
		listTag.add(StringTag.valueOf("minecraft:deepslate_lapis_ore"));

		listTag.add(StringTag.valueOf("minecraft:gold_ore"));
		listTag.add(StringTag.valueOf("minecraft:deepslate_gold_ore"));
		listTag.add(StringTag.valueOf("minecraft:redstone_ore"));
		listTag.add(StringTag.valueOf("minecraft:deepslate_redstone_ore"));
		listTag.add(StringTag.valueOf("minecraft:diamond_ore"));
		listTag.add(StringTag.valueOf("minecraft:deepslate_diamond_ore"));
		listTag.add(StringTag.valueOf("minecraft:emerald_ore"));
		
		listTag.add(StringTag.valueOf("minecraft:deepslate_emerald_ore"));
		listTag.add(StringTag.valueOf("minecraft:ancient_debris"));
		nmsPickaxe.getTag().put("CanDestroy", listTag);
		pickaxe = nmsPickaxe.asBukkitMirror();
		pickaxe.addItemFlags(ItemFlag.HIDE_DESTROYS);
		pickaxe.editMeta(meta -> meta.setUnbreakable(true));

		setData(helmet, new HelmetItemData(helmet));
		setData(chestplate, new ChestplateItemData(chestplate));
		setData(leggings, new LeggingsItemData(leggings));
		setData(boots, new BootsItemData(boots));

		setData(sword, new SwordItemData(sword));

		setData(pickaxe, new PickaxeItemData(pickaxe));

		player.getInventory().setHelmet(helmet);
		player.getInventory().setChestplate(chestplate);
		player.getInventory().setLeggings(leggings);
		player.getInventory().setBoots(boots);

		player.getInventory().setItem(0, sword);

		player.getInventory().setItem(1, pickaxe);
	}
}
