package me.lord.poscbox.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.lord.poscbox.PoscBox;
import me.lord.poscbox.utilities.TextUtil;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Note;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collection;
import java.util.UUID;

public class NPC extends ServerPlayer {
	private final int index;

	private final UUID interactionId;

	protected NPC(int index, @NotNull String name, @NotNull Location location) {
		super(((CraftServer) Bukkit.getServer()).getServer(),
				((CraftWorld) PoscBox.mainWorld).getHandle(),
				new GameProfile(UUID.randomUUID(), name));

		isRealPlayer = false;

		this.index = index;

		setPos(location.getX(), location.getY(), location.getZ());
		setYRot(location.getYaw());
		setXRot(location.getPitch());

		Interaction interaction = (Interaction) PoscBox.mainWorld.spawnEntity(location, EntityType.INTERACTION);
		interaction.setInteractionHeight(2.0f);
		interactionId = interaction.getUniqueId();
	}

	public int getIndex() {
		return index;
	}

	public UUID getInteractionId() {
		return interactionId;
	}

	// TODO: If possible, find a way to update the name without having to create an entirely new object
	public void setName(String name) {
		sendRemovePacket();
		PoscBox.mainWorld.getEntity(getInteractionId()).remove();
		NPC npc = new NPC(getIndex(), name, getLocation());
		npc.getGameProfile().getProperties().putAll(getGameProfile().getProperties());
		npc.sendInitPacket();
		NPCManager.getNPCMap().put(index, npc);
	}

	public String getNameString() {
		return getGameProfile().getName();
	}

	public Location getLocation() {
		return new Location(getBukkitEntity().getWorld(), getX(), getY(), getZ(), getYRot(), getXRot());
	}

	public Property getSkinProperty() {
		Collection<Property> properties = getGameProfile().getProperties().get("textures");
		return properties.stream()
				.filter(p -> p.getName().equals("textures"))
				.findFirst()
				.orElse(null);
	}

	public void setSkin(Property property) {
		getGameProfile().getProperties().removeAll("textures");
		getGameProfile().getProperties().put("textures", property);
	}

	public boolean setSkin(String username) {
		try {
			HttpsURLConnection connection = (HttpsURLConnection) new URL("https://api.ashcon.app/mojang/v2/user/" + username).openConnection();
			if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String reply = String.join(" ", reader.lines().toList());
				int indexOfValue = reply.indexOf("\"value\": \"");
				int indexOfSignature = reply.indexOf("\"signature\": \"");
				String skin = reply.substring(indexOfValue + 10, reply.indexOf("\"", indexOfValue + 10));
				String signature = reply.substring(indexOfSignature + 14, reply.indexOf("\"", indexOfSignature + 14));

				setSkin(new Property("textures", skin, signature));

				return true;
			} else {
				PoscBox.logger.warning("Couldn't open connection to https://api.ashcon.app/mojang/v2/user/" + username + " (Response code " + connection.getResponseCode() + ": " + connection.getResponseMessage() + ")");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public void teleport(Location location) {
		setPos(location.getX(), location.getY(), location.getZ());
		setYRot(location.getYaw());
		setXRot(location.getPitch());
		sendTeleportPacket();
	}

	public void message(Player player, String message, long delay) {
		Bukkit.getScheduler().runTaskLater(PoscBox.get(),
				() -> {
					player.sendMessage(TextUtil.c("\n&e&lNPC &6" + getNameString() + "&f: " + message + "\n"));
					player.playNote(player.getLocation(), Instrument.PLING, Note.sharp(0, Note.Tone.C));
				},
				delay);
	}

	public void sendInitPacket(Player player) {
		ServerGamePacketListenerImpl connection = ((CraftPlayer) player).getHandle().connection;
		connection.send(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, this));
		connection.send(new ClientboundAddPlayerPacket(this));
		connection.send(new ClientboundRotateHeadPacket(this, (byte) (getYRot() * 256 / 360)));
		SynchedEntityData data = getEntityData();
		data.set(new EntityDataAccessor<>(17, EntityDataSerializers.BYTE), (byte) 126);
		connection.send(new ClientboundSetEntityDataPacket(getId(), data.getNonDefaultValues()));
	}

	public void sendInitPacket() {
		Bukkit.getOnlinePlayers().forEach(this::sendInitPacket);
	}

	public void sendRemovePacket(Player player) {
		((CraftPlayer) player).getHandle().connection.send(new ClientboundRemoveEntitiesPacket(getId()));
	}

	public void sendRemovePacket() {
		Bukkit.getOnlinePlayers().forEach(this::sendRemovePacket);
	}

	public void sendTeleportPacket(Player player) {
		((CraftPlayer) player).getHandle().connection.send(new ClientboundTeleportEntityPacket(this));
	}

	public void sendTeleportPacket() {
		Bukkit.getOnlinePlayers().forEach(this::sendTeleportPacket);
	}
}