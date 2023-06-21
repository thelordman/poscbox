package me.lord.poscbox.listeners;

import me.lord.poscbox.PoscBox;
import me.lord.poscbox.data.DataManager;
import me.lord.poscbox.mine.MineManager;
import me.lord.poscbox.utilities.ArrayUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Set;

public class BlockBreakListener implements Listener {
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		Location location = block.getLocation();
		Material material = block.getType();

		if (player.getGameMode() != GameMode.CREATIVE) {
			Material[] allowed = {
					Material.COBBLESTONE, Material.STONE, Material.COAL_ORE,
					Material.DEEPSLATE_COAL_ORE, Material.IRON_ORE, Material.DEEPSLATE_IRON_ORE,
					Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE, Material.GOLD_ORE,
					Material.DEEPSLATE_GOLD_ORE, Material.REDSTONE_ORE, Material.DEEPSLATE_REDSTONE_ORE,
					Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE, Material.EMERALD_ORE,
					Material.DEEPSLATE_EMERALD_ORE, Material.ANCIENT_DEBRIS
			};
			if (!Set.of(allowed).contains(material)) {
				event.setCancelled(true);
				return;
			}

			event.setDropItems(false);
			event.setExpToDrop(0);

			MineManager.mine(player, material);

			regenerateBlock(block, location, material);
		}
	}

	private void regenerateBlock(final Block block, final Location location, Material material) {
		final Material nextMaterial = nextMaterial(material);
		Bukkit.getScheduler().runTaskLater(PoscBox.get(), () -> block.setType(nextMaterial), 1L);

		if (DataManager.getGlobal().getOriginalMaterials().containsKey(location)) {
			material = DataManager.getGlobal().getOriginalMaterials().get(location);
		} else {
			DataManager.getGlobal().getOriginalMaterials().put(location, material);
		}

		final Material finalMaterial = material;
		Bukkit.getScheduler().runTaskLater(PoscBox.get(), () -> {
			if (block.getType() == nextMaterial) {
				block.setType(finalMaterial);
				DataManager.getGlobal().getOriginalMaterials().remove(location);
			}
		}, delay(finalMaterial));
	}

	private Material nextMaterial(Material material) {
		return switch (material) {
			case COBBLESTONE, ANCIENT_DEBRIS -> Material.BEDROCK;
			case STONE -> Material.COBBLESTONE;
			case COAL_ORE, DEEPSLATE_COAL_ORE, IRON_ORE, DEEPSLATE_IRON_ORE,
					LAPIS_ORE, DEEPSLATE_LAPIS_ORE, GOLD_ORE, DEEPSLATE_GOLD_ORE,
					REDSTONE_ORE, DEEPSLATE_REDSTONE_ORE, DIAMOND_ORE, DEEPSLATE_DIAMOND_ORE,
					EMERALD_ORE, DEEPSLATE_EMERALD_ORE -> Material.STONE;
			default -> Material.AIR;
		};
	}

	private long delay(Material material) {
		return switch (material) {
			case COBBLESTONE -> 60L;
			case STONE -> 100L;
			case COAL_ORE -> 200L;
			case IRON_ORE -> 300L;
			case LAPIS_ORE -> 400L;
			case GOLD_ORE -> 500L;
			case REDSTONE -> 600L;
			case DIAMOND_ORE -> 900L;
			case EMERALD_ORE -> 1200L;
			case ANCIENT_DEBRIS -> 1800L;
			default -> 0L;
		};
	}
}
