package me.lord.poscbox.utilities;

import net.dv8tion.jda.api.EmbedBuilder;

import java.time.Instant;

/**
 * An EmbedBuilder class for convenience, use this when creating embeds
 * instead of {@link EmbedBuilder}, unless you want to specify the footer
 * and/or the timestamp of the embed.
 */
public class PoscBoxEmbedBuilder extends EmbedBuilder {
	public PoscBoxEmbedBuilder() {
		setFooter("poscbox.minehut.gg");
		setTimestamp(Instant.now());
	}
}
