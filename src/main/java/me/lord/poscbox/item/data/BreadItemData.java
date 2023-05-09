package me.lord.poscbox.item.data;

import me.lord.poscbox.item.ItemManager;
import org.bukkit.inventory.ItemStack;

public class BreadItemData extends ItemData {
	public BreadItemData(ItemStack item) {
		super(item);
		ItemManager.addLore(getRef(), "");
		addTag(Tag.UNLOSABLE);
	}
}
