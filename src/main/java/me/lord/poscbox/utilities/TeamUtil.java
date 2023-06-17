package me.lord.poscbox.utilities;

import me.lord.poscbox.PoscBox;
import me.lord.poscbox.rank.Rank;
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
            }
        }
        if (board.getTeam("z") == null) {
            board.registerNewTeam("z");
            board.getTeam("z").prefix(TextUtil.c("z"));
        }
    }
}