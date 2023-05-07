package me.lord.poscbox.discord.events;

import me.lord.poscbox.utilities.PoscBoxEmbedBuilder;
import me.lord.poscbox.utilities.TextUtil;
import me.lord.poscbox.discord.Discord;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.awt.*;

public class PlayerDeath {
	public static void exe(PlayerDeathEvent event) {
		Player player = event.getPlayer();
		Discord.MINECRAFT_CHAT.sendMessageEmbeds(new PoscBoxEmbedBuilder()
				.setAuthor(player.getName() + " Died", null, "https://crafatar.com/avatars/" + player.getUniqueId())
				.setDescription(TextUtil.stripColorCodes(TextUtil.componentToString(event.deathMessage())))
				.setColor(Color.RED)
				.build()).queue();
	}
}
