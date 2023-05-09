package me.lord.poscbox.gui;

import me.lord.poscbox.item.ItemManager;
import me.lord.poscbox.item.data.ItemData;
import me.lord.poscbox.item.enchant.Enchant;
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
		for (int i = 0; i < 20; i++) {
			if (NumberUtil.equalsRange(i, 3, 8) || NumberUtil.equalsRange(i, 12, 17)) {
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

		for (int i = 3; i < 26; i++) {
			if (NumberUtil.equalsRange(i, 9, 11) || NumberUtil.equalsRange(i, 18, 20)) {
				continue;
			}

			Enchant enchant = itemData.getEnchants()[i - 4];
			int level = enchant == null ? 0 : enchant.getLevel();
			int newLevel = level + 1;
			boolean max = enchant != null && level == enchant.getMaxLevel();
			String key = itemData.getPossibleEnchantKeys()[i - 4];
			String name = enchant == null ? TextUtil.sexySnakeString(key) : enchant.getName();
			double cost = enchant == null ? Enchant.of(null, key).getCost(level) : enchant.getCost(level);
			Material icon = enchant == null ? Enchant.of(null, key).getIcon() : enchant.getIcon();

			String[] lore = new String[max ? 5 : 6];

			lore[0] = "";
			lore[1] = "&7" + switch (enchant.getKey()) {
				case "sharpness" -> "Increases attack damage.";
				case "knockback" -> "Increases knockback.";
				case "sweeping_edge" -> "Increases sweep damage.";
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
			if (!max) lore[4] = "&7Left click to buy";
			lore[max ? 4 : 5] = "&7Right click to sell for " + TextUtil.formatMoney(cost / 2);

			items[i] = item(icon, max ? "&6" + name + " " + TextUtil.toRoman(level) :
					"&e" + name + " &7" + TextUtil.toRoman(level) + " &e→ &6" + TextUtil.toRoman(newLevel), lore);

			ItemManager.addLore(items[i], lore);
		}
		
		super.open(player);
	}

	@Override
	public int getRows() {
		return 3;
	}

	@Override
	protected void onClick(Player player, int slot, ClickType type) {

	}

	@Override
	public Component getTitle() {
		return TextUtil.c("Enchanter");
	}
}
