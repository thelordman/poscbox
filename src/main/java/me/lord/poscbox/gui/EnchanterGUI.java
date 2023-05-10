package me.lord.poscbox.gui;

import me.lord.poscbox.PoscBox;
import me.lord.poscbox.data.DataManager;
import me.lord.poscbox.item.ItemManager;
import me.lord.poscbox.item.data.EnchantItemData;
import me.lord.poscbox.item.data.ItemData;
import me.lord.poscbox.item.enchant.Enchant;
import me.lord.poscbox.item.enchant.VanillaEnchant;
import me.lord.poscbox.utilities.NumberUtil;
import me.lord.poscbox.utilities.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class EnchanterGUI extends ChestGUI {
	public EnchanterGUI() {
		for (int i = 0; i < 21; i++) {
			if (NumberUtil.equalsRange(i, 3, 9) || NumberUtil.equalsRange(i, 12, 18)) {
				continue;
			}
			items[i] = ItemManager.item(Material.WHITE_STAINED_GLASS_PANE, 1, "&r");
		}
	}

	@Override
	public void open(Player player) {
		ItemStack item = player.getInventory().getItem(EquipmentSlot.HAND);
		ItemData itemData = ItemManager.getData(item);

		items[10] = item;

		for (int i = 3, e = 0; i < 26; i++) {
			if (NumberUtil.equalsRange(i, 9, 11) || NumberUtil.equalsRange(i, 18, 20)) {
				continue;
			}

			if (e == itemData.getPossibleEnchantKeys().length) {
				break;
			}

			Enchant enchant = itemData.getEnchants()[e];
			int level = enchant == null ? 0 : enchant.getLevel();
			int newLevel = level + 1;
			boolean max = enchant != null && level == enchant.getMaxLevel();
			String key = itemData.getPossibleEnchantKeys()[e];
			String name = enchant == null ? TextUtil.enchantmentName(key) : enchant.getName();
			double oldCost = enchant == null ? Enchant.of(null, key).getCost(level) : enchant.getCost(level);
			double cost = enchant == null ? Enchant.of(null, key).getCost(newLevel) : enchant.getCost(newLevel);
			Material icon = enchant == null ? Enchant.of(null, key).getIcon() : enchant.getIcon();

			String[] lore = new String[max || level == 0 ? 6 : 7];

			lore[0] = "";
			lore[1] = "&7" + switch (key) {
				case "sharpness" -> "Increases attack damage.";
				case "knockback" -> "Increases knockback.";
				case "sweeping" -> "Increases sweep damage.";
				case "fire_aspect" -> "Sets your opponents on fire.";
				case "protection" -> "Decreases damage dealt to you.";
				case "fire_protection" -> "Decreases fire damage dealt to you.";
				case "projectile_protection" -> "Decreases damage from projectiles dealt to you.";
				case "blast_protection" -> "Decreases damage from explosions dealt to you.";
				case "thorns" -> "Sends damage dealt to you back to the attacker.";
				case "swift_sneak" -> "Increases sneaking speed.";
				default -> throw new RuntimeException("Illegal enchant key");
			};
			lore[2] = "";
			lore[3] = max ? "&6&lMAX LEVEL" : "&f" + TextUtil.formatMoney(cost);
			lore[4] = "";
			if (!max) {
				lore[5] = "&7Left click to buy";
			}
			if (level != 0) {
				lore[max ? 5 : 6] = "&7Right click to sell for " + TextUtil.formatMoney(oldCost / 2);
			}

			ItemStack enchantItem = item(icon, max || level == 0 ? "&6" + name + " " + TextUtil.toRoman(level) :
					"&e" + name + " &7" + TextUtil.toRoman(level) + " &e→ &6" + TextUtil.toRoman(newLevel), lore);

			ItemManager.setData(enchantItem, new EnchantItemData(enchantItem, key));

			items[i] = enchantItem;

			e++;
		}
		
		super.open(player);
	}

	@Override
	public int getRows() {
		return 3;
	}

	@Override
	protected void onClick(Player player, int slot, ClickType type) {
		ItemData enchantData = ItemManager.getData(items[slot]);
		ItemData data = ItemManager.getData(player.getInventory().getItemInMainHand());

		if (enchantData == null) {
			return;
		}
		if (!(enchantData instanceof EnchantItemData eData)) {
			return;
		}

		String eKey = eData.getKey();

		for (int i = 0; i < data.getEnchants().length; i++) {
			String key = data.getPossibleEnchantKeys()[i];
			if (key.equals(eKey)) {
				Enchant enchant = data.getEnchants()[i];
				int level = enchant == null ? 0 : enchant.getLevel();
				int newLevelBuy = level + 1;
				int newLevelSell = level - 1;
				boolean max = enchant != null && level == enchant.getMaxLevel();
				if ((max && !type.isRightClick()) || (newLevelSell == -1 && type.isRightClick())) {
					return;
				}
				double oldCost = enchant == null ? Enchant.of(null, key).getCost(level) : enchant.getCost(level);
				double cost = enchant == null ? Enchant.of(null, key).getCost(newLevelBuy) : enchant.getCost(newLevelBuy);

				Enchant newEnchant;
				if (type.isRightClick()) {
					if (newLevelSell == 0) {
						newEnchant = null;
						if (enchant instanceof VanillaEnchant vanilla) {
							player.getInventory().getItemInMainHand().removeEnchantment(vanilla.getVanilla());
						}
					} else {
						newEnchant = enchant;
						newEnchant.setLevel(player.getInventory().getItemInMainHand(), newLevelSell);
					}
					DataManager.getPlayerData(player).addBalance(oldCost / 2);
					player.sendMessage(TextUtil.c("&eSold &6" + TextUtil.enchantmentName(key) + " &6" + TextUtil.toRoman(level) + " &efor &f" + TextUtil.formatMoney(oldCost / 2)));
				} else {
					if (cost > DataManager.getPlayerData(player).getBalance()) {
						player.sendMessage(TextUtil.c("&cYou don't have enough money for that"));
						return;
					}

					if (enchant == null) {
						newEnchant = Enchant.of(player.getInventory().getItemInMainHand(), key);
					} else {
						newEnchant = enchant;
						newEnchant.setLevel(player.getInventory().getItemInMainHand(), newLevelBuy);
					}
					DataManager.getPlayerData(player).removeBalance(cost);
					player.sendMessage(TextUtil.c("&eBought &6" + TextUtil.enchantmentName(key) + " &6" + TextUtil.toRoman(newLevelBuy) + " &efor &f" + TextUtil.formatMoney(cost)));
				}
				data.setEnchant(newEnchant, i);

				ItemManager.setData(player.getInventory().getItemInMainHand(), data);

				player.closeInventory();
				new EnchanterGUI().open(player);

				return;
			}
		}
	}

	@Override
	public Component getTitle() {
		return TextUtil.c("Enchanter");
	}
}
