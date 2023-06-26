package me.lord.poscbox.utilities;

import me.lord.poscbox.rank.Rank;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;

public class TeamUtil {

    public static Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();

    public static void loadTeams() {
        String name;
        for (Rank rank : Rank.values()) {
            name = String.valueOf(rank.getTabPlacement());
            if (board.getTeam(name) == null) {
                board.registerNewTeam(name);
                board.getTeam(name).prefix(TextUtil.c(name));
                board.getTeam(name).color(rank.getTeamColor());
            }
        }
        if (board.getTeam("§r§r§r§r§r§r§r§r§r§r§r") == null) {
            board.registerNewTeam("§r§r§r§r§r§r§r§r§r§r§r");
            board.getTeam("§r§r§r§r§r§r§r§r§r§r§r").prefix(TextUtil.c("§r§r§r§r§r§r§r§r§r§r§r"));
            board.getTeam("§r§r§r§r§r§r§r§r§r§r§r").color(NamedTextColor.GRAY);
        }
    }
}
