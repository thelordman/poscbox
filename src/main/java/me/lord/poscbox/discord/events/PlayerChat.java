package me.lord.poscbox.discord.events;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.lord.poscbox.utilities.TextUtil;
import me.lord.poscbox.discord.Discord;

public class PlayerChat {
	public static void exe(AsyncChatEvent event) {
		Discord.MINECRAFT_CHAT.sendMessage("**" + event.getPlayer().getDisplayName() + ":** " + TextUtil.stripColorCodes(TextUtil.componentToString(event.message()))).queue();
	}
}
