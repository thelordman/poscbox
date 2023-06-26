package me.lord.poscbox.commands;

import me.lord.poscbox.utilities.Cmd;
import me.lord.poscbox.utilities.CombatLog;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpawnCommand implements Cmd {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (!CombatLog.inCombat(player)) {
                player.teleport(new Location(Bukkit.getWorld("poscbox"), -163.5, 27, -209.5));
            } else {
                player.sendMessage("You are currently in combat!");
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }

    @Override
    public String name() {
        return "spawn";
    }
}
