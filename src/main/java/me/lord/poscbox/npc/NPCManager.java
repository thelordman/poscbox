package me.lord.poscbox.npc;

import com.mojang.authlib.properties.Property;
import me.lord.poscbox.PoscBox;
import me.lord.poscbox.data.DataManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;

public class NPCManager {
	private static final HashMap<Integer, NPC> npcMap = new HashMap<>();

	public static int createNPC(@Nullable String name, @NotNull Location location, @Nullable Property skinProperty, @NotNull Integer index, boolean shouldLookClose) {
		name = name == null ? "Unnamed" : name;
		NPC npc = new NPC(index, name, location);
		if (skinProperty != null) npc.setSkin(skinProperty);
		npc.sendInitPacket();
		npcMap.put(index, npc);
		return npc.getIndex();
	}

	public static int createNPC(@Nullable String name, @NotNull Location location, @Nullable Property skinProperty, @NotNull Integer index) {
		name = name == null ? "Unnamed" : name;
		NPC npc = new NPC(index, name, location);
		if (skinProperty != null) npc.setSkin(skinProperty);
		npc.sendInitPacket();
		npcMap.put(index, npc);
		return npc.getIndex();
	}

	public static int createNPC(@Nullable String name, @NotNull Location location, @Nullable String skinUsername, @NotNull Integer index) {
		name = name == null ? "Unnamed" : name;
		NPC npc = new NPC(index, name, location);
		npc.setSkin(skinUsername == null ? name : skinUsername);
		npc.sendInitPacket();
		npcMap.put(index, npc);
		return npc.getIndex();
	}

	public static int createNPC(@Nullable String name, @NotNull Location location, @Nullable String skinUsername) {
		return createNPC(name, location, skinUsername, npcMap.keySet().stream().mapToInt(t -> t).max().orElse(-1) + 1);
	}

	public static HashMap<Integer, NPC> getNPCMap() {
		return npcMap;
	}

	public static NPC getTargetNPC(Player player) {
		ServerPlayer nmsPlayer = ((CraftPlayer) player).getHandle();

		Vec3 start = nmsPlayer.getEyePosition(1.0f);
		Vec3 direction = nmsPlayer.getLookAngle();
		Vec3 end = start.add(direction.x * 10d, direction.y * 10d, direction.z * 10d);

		nmsPlayer.getBoundingBox().expandTowards(direction.x * 10d, direction.y * 10d, direction.z * 10d).inflate(1.0d, 1.0d, 1.0d);

		double distance = 0.0d;
		NPC result = null;

		for (NPC npc : NPCManager.npcMap.values()) {
			AABB aabb = npc.getBoundingBox().inflate(0.0f, 0.0f, 0.0f);
			Optional<Vec3> rayTraceResult = aabb.clip(start, end);

			if (rayTraceResult.isPresent()) {
				Vec3 rayTrace = rayTraceResult.get();
				double distanceTo = start.distanceToSqr(rayTrace);
				if (distanceTo < distance || distance == 0.0d) {
					result = npc;
					distance = distanceTo;
				}
			}
		}

		return result;
	}

	public static boolean isNPC(Entity entity) {
		if (entity == null) return false;
		net.minecraft.world.entity.Entity nmsEntity = ((CraftEntity) entity).getHandle();
		return nmsEntity instanceof NPC;
	}

	public static NPC getNPC(Entity entity) {
		if (!isNPC(entity)) return null;
		return (NPC) ((CraftEntity) entity).getHandle();
	}

	public static NPC[] getNPC(String name) {
		return npcMap.values().stream()
				.filter(npc -> npc.getNameString().equalsIgnoreCase(name))
				.sorted(Comparator.comparingInt(NPC::getIndex))
				.toArray(NPC[]::new);
	}

	public static NPC getNPC(int index) {
		return npcMap.get(index);
	}

	public static void removeNPC(int index) {
		NPC npc = npcMap.get(index);
		if (npc != null) {
			PoscBox.mainWorld.getEntity(npc.getInteractionId()).remove();
			npc.sendRemovePacket();
		}
		npcMap.remove(index);
		File npcFile = new File(DataManager.NPC_DATA_FOLDER.getPath() + File.separator + index + ".dat");
		if (npcFile.exists()) npcFile.delete();
	}

	public static void sendInitPacketAll(Player player) {
		npcMap.values().forEach(npc -> npc.sendInitPacket(player));
	}

	public static void sendInitPacketAll() {
		Bukkit.getOnlinePlayers().forEach(NPCManager::sendInitPacketAll);
	}

	public static void sendRemovePacketAll(Player player) {
		npcMap.values().forEach(npc -> npc.sendRemovePacket(player));
	}

	public static void sendRemovePacketAll() {
		Bukkit.getOnlinePlayers().forEach(NPCManager::sendRemovePacketAll);
	}
}
