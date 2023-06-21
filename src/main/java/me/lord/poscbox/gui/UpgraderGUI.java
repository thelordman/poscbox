package me.lord.poscbox.gui;

import me.lord.poscbox.data.DataManager;
import me.lord.poscbox.item.ItemManager;
import me.lord.poscbox.item.data.ItemData;
import me.lord.poscbox.item.enchant.Enchant;
import me.lord.poscbox.utilities.TextUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class UpgraderGUI extends ChestGUI {
	public UpgraderGUI() {
		for (int i = 0; i < 27; i++) {
			items[i] = ItemManager.item(Material.WHITE_STAINED_GLASS_PANE, 1, "&r");
		}
	}

	@Override
	public int getRows() {
		return 3;
	}

	@Override
	public Component getTitle() {
		return TextUtil.c("Upgrader");
	}

	@Override
	public void open(Player player) {
		ItemStack item = player.getInventory().getItem(EquipmentSlot.HAND);
		ItemData itemData = ItemManager.getData(item);

		if (itemData.isMax()) {
			ItemStack newItem = item.clone();
			newItem.editMeta(meta -> meta.displayName(TextUtil.c("&6&lMAX LEVEL").decoration(TextDecoration.ITALIC, false)));
			items[13] = newItem;
			items[11] = ItemManager.item(Material.WHITE_STAINED_GLASS_PANE, 1, "&r");
			items[15] = ItemManager.item(Material.WHITE_STAINED_GLASS_PANE, 1, "&r");
		} else {
			items[11] = item;

			ItemStack newItem = item.clone();
			newItem.setType(itemData.getNextType());
			items[15] = newItem;

			ItemStack mid = new ItemStack(Material.ANVIL);
			double cost = itemData.getNextTypeCost();
			String oldName = TextUtil.componentToString(item.displayName()).replaceAll("[\\[\\]]", "");
			String newName = TextUtil.componentToString(newItem.displayName()).replaceAll("[\\[\\]]", "");

			ItemMeta meta = mid.getItemMeta();
			meta.displayName(TextUtil.c("&7" + oldName + " &e→ &6" + newName).decoration(TextDecoration.ITALIC, false));
			String[] lore = {
					"",
					"&7Upgrade material.",
					"",
					"&f" + TextUtil.formatMoney(cost),
					"",
					"&7Left click to buy"
			};
			meta.lore(Arrays.stream(lore).map(s -> TextUtil.c(s).decoration(TextDecoration.ITALIC, false)).toList());
			mid.setItemMeta(meta);

			items[13] = mid;
		}

		super.open(player);
	}

	@Override
	protected void onClick(Player player, int slot, ClickType type) {
		if (player.getInventory().getItem(EquipmentSlot.HAND).getItemMeta() == null) {
			return;
		}

		ItemData itemData = ItemManager.getData(player.getInventory().getItem(EquipmentSlot.HAND));

		if (itemData == null || slot != 13 || itemData.isMax() || !type.isLeftClick()) {
			return;
		}

		double cost = itemData.getNextTypeCost();
		Material material = items[15].getType();

		if (cost > DataManager.getPlayerData(player).getBalance()) {
			player.sendMessage(TextUtil.c("&cYou don't have enough money for that"));
			return;
		}

		DataManager.getPlayerData(player).removeBalance(cost);
		player.sendMessage(TextUtil.c("&eBought &6" + TextUtil.componentToString(items[15].displayName()).replaceAll("[\\[\\]]", "") + " &efor &f" + TextUtil.formatMoney(cost)));

		player.getInventory().getItem(EquipmentSlot.HAND).setType(material);
		for (Enchantment enchantment : Enchantment.values()) {
			player.getInventory().getItem(EquipmentSlot.HAND).removeEnchantment(enchantment);
			itemData.getRef().removeEnchantment(enchantment);
		}
		itemData.getRef().setType(material);
		itemData.setEnchants(new Enchant[itemData.getPossibleEnchantKeys().length]);
		ItemManager.setData(player.getInventory().getItem(EquipmentSlot.HAND), itemData);

		open(player);
	}
}
