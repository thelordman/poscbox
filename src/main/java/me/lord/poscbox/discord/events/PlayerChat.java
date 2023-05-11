package me.lord.poscbox.discord.events;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.lord.poscbox.utilities.TextUtil;
import me.lord.poscbox.discord.Discord;

public class PlayerChat {
	public static void exe(AsyncChatEvent event) {
		Discord.MINECRAFT_CHAT.sendMessage(TextUtil.stripColorCodes("**" + TextUtil.componentToString(event.getPlayer().displayName()) + ":** " + TextUtil.componentToString(event.message()))).queue();
	}
}
