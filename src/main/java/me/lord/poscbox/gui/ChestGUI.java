package me.lord.poscbox.gui;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

// TODO: Add abstract class MultiPageGUI
public abstract class ChestGUI extends GUI {
	public ChestGUI() {
		items = new ItemStack[getRows() * 9];
	}

	@Override
	public InventoryType getType() {
		return InventoryType.CHEST;
	}

	public abstract int getRows();
}
