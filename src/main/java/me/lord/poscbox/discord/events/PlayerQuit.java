package me.lord.poscbox.discord.events;

import me.lord.poscbox.utilities.PoscBoxEmbedBuilder;
import me.lord.poscbox.discord.Discord;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;

import java.awt.*;

public class PlayerQuit {
	public static void exe(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		Discord.MINECRAFT_CHAT.sendMessageEmbeds(new PoscBoxEmbedBuilder()
				.setAuthor(player.getName() + " Left", null, "https://crafatar.com/avatars/" + player.getUniqueId())
				.setColor(Color.RED)
				.build()).queue();
	}
}
