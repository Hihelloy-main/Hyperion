/*
 * Copyright 2016-2024 Moros
 *
 * This file is part of Hyperion.
 *
 * Hyperion is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Hyperion is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Hyperion. If not, see <https://www.gnu.org/licenses/>.
 */

package me.moros.hyperion;


import com.cjcrafter.foliascheduler.FoliaCompatibility;
import com.cjcrafter.foliascheduler.ServerImplementation;
import com.cjcrafter.foliascheduler.TaskImplementation;
import com.cjcrafter.foliascheduler.util.ReflectionUtil;
import com.projectkorra.projectkorra.BendingPlayer;
import me.moros.hyperion.abilities.Elements.FireAbility;
import me.moros.hyperion.commands.HyperionCommand;
import me.moros.hyperion.configuration.ConfigManager;
import me.moros.hyperion.listeners.AbilityListener;
import me.moros.hyperion.listeners.CoreListener;
import me.moros.hyperion.listeners.PlayerJoinListener;
import me.moros.hyperion.methods.CoreMethods;
import me.moros.hyperion.util.*;
import org.bstats.bukkit.Metrics;;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.Consumer;
import java.util.logging.Logger;

import static com.projectkorra.projectkorra.util.TempFallingBlock.get;
import static com.projectkorra.projectkorra.util.TempFallingBlock.manage;

public class Hyperion extends JavaPlugin {
	public static Hyperion plugin;
	private static String author;
	private static String version;
	private static Logger log;
	private static PersistentDataLayer layer;
	public static boolean isFolia;
	public static boolean paper;
	public static boolean luminol;
	public static boolean spigot;
	private PotionEffectAdapter potionEffectAdapter;
	public static ServerImplementation scheduler;
	private static UpdateChecker updateChecker;


	@Override
	public void onEnable() {
		plugin = this;
		scheduler = new FoliaCompatibility(plugin).getServerImplementation();
		log = getLogger();
		version = getDescription().getVersion();
		author = getDescription().getAuthors().get(0);
		try {
			Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
			isFolia = true;
		} catch (ClassNotFoundException ignored) {}

		try {
			Class.forName("com.destroystokyo.paper.PaperConfig");
			paper = true;
		} catch (ClassNotFoundException ignored) {}

		try {
			Class.forName("me.earthme.luminol.api.ThreadedRegion");
			luminol = true;
		} catch (ClassNotFoundException ignored) {}

		if (!isLuminol() && isPaper()) {
			getLogger().info("Hyperion is running on Paper/Folia");
		}

		if (isLuminol() && !spigot) {
			getLogger().info("Hyperion is running on Luminol");
		}

		if (!isFolia && !paper && !luminol) {
			spigot = true;
			getLogger().info("Hyperion is running on Spigot");
		}

		new Metrics(this, 8212);
		new ConfigManager();
		new HyperionCommand();
		new Elements();
		updateChecker = new UpdateChecker(this, "Hihelloy-main/Hyperion");
		if (isFolia) {
			scheduler.global().execute(() -> updateChecker.checkForUpdate());
		} else {
			Bukkit.getScheduler().runTaskAsynchronously(this, () -> updateChecker.checkForUpdate());
		}
		getLogger().info("Initialized Hyperion Elements/Configs/Commands/Metrics/UpdateChecker");
		getLogger().info("Attempting to load PaperLib");
		new PaperLib();
		layer = new PersistentDataLayer();
		checkMaintainer();
		CoreMethods.loadAbilities();

		getServer().getPluginManager().registerEvents(new AbilityListener(), this);
		getServer().getPluginManager().registerEvents(new CoreListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);

		// Use appropriate scheduler depending on platform
		if (isFolia || luminol) {
			scheduler.global().runAtFixedRate(task -> {
				manage();
				return null;
			}, 1L, 5L);

			scheduler.global().runAtFixedRate(task -> {
				TempArmorStand.manage();
				return null;
			}, 1L, 1L);

			scheduler.global().runAtFixedRate(task -> {
				BendingFallingBlock.manage();
				return null;
			}, 1L, 5L);

			scheduler.global().runAtFixedRate(task -> {
				FireAbility.getAbilities();
				return null;
			}, 1L, 5L);
		} else {
			new BukkitRunnable() {
				@Override
				public void run() {
					manage();
				}
			}.runTaskTimer(this, 0L, 5L);

			new BukkitRunnable() {
				@Override
				public void run() {
					TempArmorStand.manage();
				}
			}.runTaskTimer(this, 0L, 1L);

			new BukkitRunnable() {
				@Override
				public void run() {
					BendingFallingBlock.manage();
				}
			}.runTaskTimer(this, 0L, 5L);
		}

		PotionEffectAdapterFactory potionEffectAdapterFactory = new PotionEffectAdapterFactory();
		potionEffectAdapter = potionEffectAdapterFactory.getAdapter();

		if (author.contains("Hihelloy (Maintainer)")) {
			getLogger().info("Hihelloy is the current maintainer of Hyperion");
		} else {
			getLogger().warning("Hihelloy is the current maintainer of Hyperion, but the plugin had trouble loading that D:");
		}
	}

	@Override
	public void onDisable() {
		BendingFallingBlock.removeAll();
		TempArmorStand.removeAll();

		if (!isFolia && !luminol) {
			getServer().getScheduler().cancelTasks(this);
		}

		if (isFolia || luminol) {
			scheduler.async().cancelTasks();
		}
	}

	public static void reload1() {
		Hyperion.getPlugin().reloadConfig();
		ConfigManager.modifiersConfig.reloadConfig();
		BendingFallingBlock.removeAll();
		TempArmorStand.removeAll();
		CoreMethods.loadAbilities();
		new HyperionCommand();
		getLog().info("Trying to initialize commands once more");
		getLog().info("Hyperion BUKKIT Reloaded.");
	}

	public static void reload() {
		Hyperion.getPlugin().reloadConfig();
		ConfigManager.modifiersConfig.reloadConfig();
		BendingFallingBlock.removeAll();
		TempArmorStand.removeAll();
		CoreMethods.loadAbilities();
		new HyperionCommand();
		getLog().info("Trying to initialize commands once more");
		getLog().info("Hyperion FOLIA Reloaded.");
	}

	public static void checkMaintainer() {
		if (!author.contains("Hihelloy (Maintainer)")) {
			author = author + ", Hihelloy (Maintainer)";
		}
	}

	public static Hyperion getPlugin() {
		return plugin;
	}

	public static String getAuthor() {
		return author;
	}

	public static String getVersion() {
		return version;
	}

	public static Logger getLog() {
		return log;
	}

	public static PersistentDataLayer getLayer() {
		return layer;
	}

	public PotionEffectAdapter getPotionEffectAdapter() {
		return this.potionEffectAdapter;
	}

	public static boolean isFolia() {
		return isFolia;
	}

	public static boolean isPaper() {
		return paper;
	}

	public static boolean isLuminol() {
		return luminol;
	}

	public static boolean isSpigot() {
		return spigot;
	}

	public static ServerImplementation getScheduler() {
		return scheduler;
	}

	public static UpdateChecker getUpdateChecker() {
		return getPlugin(Hyperion.class).updateChecker;
	}
}
