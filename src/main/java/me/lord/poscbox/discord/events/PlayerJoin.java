package me.lord.poscbox.discord.events;

import me.lord.poscbox.utilities.PoscBoxEmbedBuilder;
import me.lord.poscbox.discord.Discord;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.awt.*;

public class PlayerJoin {
	public static void exe(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Discord.MINECRAFT_CHAT.sendMessageEmbeds(new PoscBoxEmbedBuilder()
				.setAuthor(ChatColor.stripColor(player.getName() + " Joined"), null, "https://crafatar.com/avatars/" + player.getUniqueId())
				.setColor(Color.GREEN)
				.build()).queue();
	}
}
