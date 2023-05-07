package me.lord.poscbox.discord.listeners;

import com.sun.management.OperatingSystemMXBean;
import me.lord.poscbox.PoscBox;
import me.lord.poscbox.discord.Discord;
import me.lord.poscbox.utilities.PoscBoxEmbedBuilder;
import me.lord.poscbox.utilities.TextUtil;
import me.lord.poscbox.utilities.tuples.Pair;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.lang.management.ManagementFactory;
import java.time.Instant;

public class SlashCommandInteractionListener extends ListenerAdapter {
	@Override
	public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
		Member member = event.getMember();

		switch (event.getName()) {
			case "ping" -> {
				long time = Instant.now().toEpochMilli();
				event.replyEmbeds(new PoscBoxEmbedBuilder()
						.setAuthor("Pong!")
						.setDescription("**Ping:** Calculating...")
						.setColor(Color.BLUE)
						.build()
				).flatMap(v ->
						event.getHook().editOriginalEmbeds(
								new PoscBoxEmbedBuilder()
										.setAuthor("Pong!")
										.setDescription("**Ping:** " + TextUtil.format(Instant.now().toEpochMilli() - time) + " ms")
										.setColor(Color.BLUE)
										.build()
						)
				).queue();
			}
			case "server" -> {
				// TODO: Sort players by rank
				Pair<String, Double> stats = getPlayers();
				String players = stats.getA();
				double totalPing = stats.getB();

				String statistics = "**Online Players:** " + PoscBox.onlinePlayers
						+ "\n**TPS:**"
						+ "\n1 m: " + TextUtil.format(Bukkit.getTPS()[0])
						+ "\n5 m: " + TextUtil.format(Bukkit.getTPS()[1])
						+ "\n15 m: " + TextUtil.format(Bukkit.getTPS()[2])
						+ "\n**Average Ping:** " + TextUtil.format(totalPing / PoscBox.onlinePlayers) + " ms"
						+ "\n**Memory:** " + TextUtil.format((int) ((Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory()) / 1000000)) + " MB/" + TextUtil.format((int) (Runtime.getRuntime().maxMemory() / 1000000)) + " MB"
						+ "\n**CPU:** " + TextUtil.format(ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class).getCpuLoad() * 100) + "%";

				event.replyEmbeds(new PoscBoxEmbedBuilder()
						.setAuthor("Online Players")
						.setDescription(players)
						.addField("Statistics", statistics, false)
						.setColor(Color.BLUE)
						.build()).queue();
			}
			case "online" -> {
				Pair<String, Double> stats = getPlayers();
				String players = stats.getA();

				event.replyEmbeds(new PoscBoxEmbedBuilder()
						.setAuthor("Online Players")
						.setDescription(players + "\n**Total: ** " + PoscBox.onlinePlayers)
						.setColor(Color.BLUE)
						.build()).queue();
			}
			case "command" -> {
				if (!member.getRoles().contains(Discord.jda.getRoleById("921439677574160499"))) {
					event.reply("You aren't allowed to execute this command.").setEphemeral(true).queue();
					return;
				}
				String command = event.getOption("command", OptionMapping::getAsString);
				if (command == null) {
					event.reply("You must state a command to execute.").setEphemeral(true).queue();
					return;
				}
				Bukkit.getScheduler().callSyncMethod(PoscBox.get(), () ->
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command));
				event.replyEmbeds(new PoscBoxEmbedBuilder()
						.setAuthor("Command")
						.setDescription("Successfully executed the console command `/" + command + "`")
						.setColor(Color.BLUE)
						.build()).queue();
			}
		}
	}

	private Pair<String, Double> getPlayers() {
		double totalPing = 0d;

		StringBuilder builder = new StringBuilder();

		for (Player player : Bukkit.getOnlinePlayers()) {
			totalPing += player.getPing();
			builder.append(TextUtil.stripColorCodes(TextUtil.componentToString(player.displayName()))).append("\n");
		}

		return new Pair<>(builder.toString(), totalPing);
	}
}
