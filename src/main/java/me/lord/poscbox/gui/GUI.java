package me.lord.poscbox.gui;

import me.lord.poscbox.utilities.TextUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public abstract class GUI implements Listener {
	public static ItemStack item(Material material, String name, String... lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.displayName(TextUtil.c(name).decoration(TextDecoration.ITALIC, false));
		meta.lore(Arrays.stream(lore).map(s -> TextUtil.c(s).decoration(TextDecoration.ITALIC, false)).toList());
		item.setItemMeta(meta);
		return item;
	}

	protected ItemStack[] items;

	@EventHandler
	public void onInventoryDrag(InventoryDragEvent event) {
		if (event.getView().title() == getTitle()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player player && event.getView().title() == getTitle()) {
			event.setCancelled(true);
			onClick(player, event.getSlot(), event.getClick());
		}
	}

	protected void onClick(Player player, int slot, ClickType type) {

	}

	public ItemStack[] getItems() {
		return items;
	}

	public abstract InventoryType getType();

	public Inventory createInventory() {
		Inventory inventory;
		if (getType() == InventoryType.CHEST) {
			inventory = Bukkit.createInventory(null, getItems().length, getTitle());
		} else {
			inventory = Bukkit.createInventory(null, getType(), getTitle());
		}
		inventory.setContents(getItems());
		return inventory;
	}

	public abstract Component getTitle();

	public void open(Player player) {
		player.openInventory(createInventory());
	}
}
