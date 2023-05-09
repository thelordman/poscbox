package me.lord.poscbox.item.data;

import me.lord.poscbox.item.ItemManager;
import org.bukkit.inventory.ItemStack;

import java.io.Serial;

public class BreadItemData extends ItemData {
	@Serial
	private static final long serialVersionUID = 3926166907380157785L;

	public BreadItemData(ItemStack item) {
		super(item);
		ItemManager.addLore(getRef(), "");
		addTag(Tag.UNLOSABLE);
	}
}
