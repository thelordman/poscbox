package me.lord.posc.discord.events;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.lord.posc.discord.Discord;
import me.lord.posc.utilities.TextUtil;

public class PlayerChat {
	public static void exe(AsyncChatEvent event) {
		Discord.MINECRAFT_CHAT.sendMessage("**" + event.getPlayer().getDisplayName() + ":** " + TextUtil.stripColorCodes(TextUtil.componentToString(event.message()))).queue();
	}
}
