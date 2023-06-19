package me.lord.poscbox.utilities;

import me.lord.poscbox.PoscBox;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LootboxUtil {

    public static List<Location> lootboxLocations = List.of(
            new Location(Bukkit.getWorld("poscbox"), -191.5, -10, -240.5),
            new Location(Bukkit.getWorld("poscbox"), -190.5, -14, -220.5),
            new Location(Bukkit.getWorld("poscbox"), -130.5, -2, -217.5)
    );

    public static Location currentLocation;

    public static void loadBoxes() {
        new BukkitRunnable() {
            @Override
            public void run() {
                currentLocation = lootboxLocations.get(new Random().nextInt(lootboxLocations.size()));
                currentLocation.getBlock().setType(Material.CHEST);
                Bukkit.broadcast(TextUtil.c("&a&lLOOTBOX &fA loot box has spawned! Buy a rank to claim it!"));
            }
        }.runTaskLater(PoscBox.get(), 100);
    }
}
