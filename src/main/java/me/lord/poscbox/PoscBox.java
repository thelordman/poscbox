package me.lord.poscbox;

import com.sun.management.OperatingSystemMXBean;
import me.lord.poscbox.data.DataManager;
import me.lord.poscbox.data.PlayerData;
import me.lord.poscbox.discord.Discord;
import me.lord.poscbox.utilities.Cmd;
import me.lord.poscbox.utilities.ReflectionUtil;
import me.lord.poscbox.utilities.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.util.logging.Logger;

public final class PoscBox extends JavaPlugin {
	private static PoscBox instance;

	public static Logger logger;
	public static World mainWorld;

	public static int onlinePlayers;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		instance = this;
		logger = getLogger();

		mainWorld = Bukkit.getWorld("poscbox");

		onlinePlayers = Bukkit.getOnlinePlayers().size();

		DataManager.loadAll();

		configureServer();

		logger.info("Registering listeners and commands");
		Instant time = Instant.now();
		registerListeners();
		registerCommands();
		logger.info("Registration completed in " + Instant.now().minusMillis(time.toEpochMilli()) + " ms");

		Discord.enable();

		Bukkit.getScheduler().runTaskTimer(get(), () -> {
			for (Player player : Bukkit.getOnlinePlayers()) {
				PlayerData data = DataManager.getPlayerData(player);
				if (data != null) {
					data.getScoreboard().updateConstant();
				}
				updateTab(player);
			}
		}, 0L, 20L);
	}

	@Override
	public void onDisable() {
		DataManager.saveAll();
		Discord.disable();
	}

	public static PoscBox get() {
		return instance;
	}

	public static ClassLoader getInstanceClassLoader() {
		return get().getClassLoader();
	}

	/**
	 * A method to automate the registration of listeners.
	 */
	private static void registerListeners() {
		try {
			for (Class<? extends Listener> eventClass : ReflectionUtil.getSubclasses(Listener.class, "me.lord.poscbox.listeners")) {
				Bukkit.getPluginManager().registerEvents(eventClass.getDeclaredConstructor().newInstance(), get());
			}
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * A method to automate the registration of commands.
	 * Also add the command to plugin.yml.
	 */
	private static void registerCommands() throws NullPointerException {
		try {
			for (Class<? extends Cmd> cmdClass : ReflectionUtil.getSubclasses(Cmd.class, "me.lord.poscbox.commands")) {
				Cmd cmd = cmdClass.getDeclaredConstructor().newInstance();
				if (cmd.name() == null && cmd.names() == null) {
					throw new NullPointerException("getName() and getNames() cannot both return null; " + cmd.getClass().getSimpleName());
				}
				if (cmd.names() == null) {
					get().getCommand(cmd.name()).setExecutor(cmd);
					get().getCommand(cmd.name()).permissionMessage(TextUtil.c("&cYou need the permission \"" + cmd.permission() + "\" to use this command."));
					if (cmd.permission() != null) {
						get().getCommand(cmd.name()).setPermission(cmd.permission());
					}
				} else {
					for (String name : cmd.names()) {
						get().getCommand(name).setExecutor(cmd);
						get().getCommand(name).permissionMessage(TextUtil.c("&cYou need the permission \"" + cmd.permission() + "\" to use this command."));
						if (cmd.permissions(name) != null) {
							get().getCommand(name).setPermission(cmd.permissions(name));
						}
					}
				}
			}
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configures server related settings on startup to make sure
	 * nothing unexpected happens.
	 * This makes modifications to these GameRules made with the
	 * /gamerule command reset on server reload and restart.
	 */
	private static void configureServer() {
		mainWorld.setDifficulty(Difficulty.HARD);
		mainWorld.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
		mainWorld.setGameRule(GameRule.SPAWN_RADIUS, 0);
		mainWorld.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
		mainWorld.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
		mainWorld.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
		mainWorld.setGameRule(GameRule.FALL_DAMAGE, false);
		mainWorld.setGameRule(GameRule.DO_MOB_SPAWNING, false);
		mainWorld.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
		mainWorld.setGameRule(GameRule.KEEP_INVENTORY, true);
	}

	// TODO: Sort TAB list according to rank
	public static void updateTab(Player player) {
		player.sendPlayerListHeader(TextUtil.c("\n&6&lPoscBox\n"));
		player.sendPlayerListFooter(TextUtil.c("\n   &6Online&7: &f" + TextUtil.format(onlinePlayers) + " &8| " +
				"&6Ping&7: &f" + TextUtil.format(player.getPing()) + " &8| " +
				"&6TPS&7: &f" + TextUtil.format(Bukkit.getTPS()[0]) + " &8| " +
				"&6Memory&7: &f" + TextUtil.format((int) ((Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory()) / 1000000)) + " MB/" + TextUtil.format((int) (Runtime.getRuntime().maxMemory() / 1000000)) + " MB" + " &8| " +
				"&6CPU&7: &f" + TextUtil.format(ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class).getCpuLoad() * 100) + "%   \n                                                                                                  "));
	}
}
