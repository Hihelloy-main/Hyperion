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

import com.projectkorra.projectkorra.util.TempFallingBlock;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import me.moros.hyperion.abilities.Elements.FireAbility;
import me.moros.hyperion.commands.HyperionCommand;
import me.moros.hyperion.configuration.ConfigManager;
import me.moros.hyperion.listeners.AbilityListener;
import me.moros.hyperion.listeners.CoreListener;
import me.moros.hyperion.methods.CoreMethods;
import me.moros.hyperion.util.*;
import org.bstats.bukkit.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Logger;

public class Hyperion extends JavaPlugin {
	public static Hyperion plugin;
	private static String author;
	private static String version;
	private static Logger log;
	private static PersistentDataLayer layer;
	public static boolean isFolia;
	public static boolean paper;
	public static boolean luminol;
	private PotionEffectAdapter potionEffectAdapter;

	@Override
	public void onEnable() {
		plugin = this;
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

        if (!isLuminol()) {
            getLogger().info("[Hyperion] Hyperion is running on Paper/Folia");
        } else {
            getLogger().info("[Hyperion] Hyperion is running on Luminol");
        }

        new Metrics(this, 8212);
		new ConfigManager();
		new HyperionCommand();
		new Elements();
		layer = new PersistentDataLayer();
		checkMaintainer();
		CoreMethods.loadAbilities();

		getServer().getPluginManager().registerEvents(new AbilityListener(), this);
		getServer().getPluginManager().registerEvents(new CoreListener(), this);

		// Use appropriate scheduler depending on platform
		if (isFolia || luminol) {
			getServer().getGlobalRegionScheduler().runAtFixedRate(this, task -> TempFallingBlock.manage(), 1L, 5L);
			getServer().getGlobalRegionScheduler().runAtFixedRate(this, task -> TempArmorStand.manage(), 1L, 1L);
			getServer().getGlobalRegionScheduler().runAtFixedRate(this, task -> BendingFallingBlock.manage(), 1L, 5L);
			getServer().getGlobalRegionScheduler().runAtFixedRate(this, task -> FireAbility.getAbilities(), 1L, 5L);
		} else {
			new BukkitRunnable() {
				@Override
				public void run() {
					TempFallingBlock.manage();
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

		// Avoid Bukkit cancelTasks on Folia/Luminol (unsupported)
		if (!isFolia && !luminol) {
			getServer().getScheduler().cancelTasks(this);
		}
		// Use a Folia/Luminol compatible version of cancelTasks
		if (isFolia || luminol ) {
            getServer().getGlobalRegionScheduler().cancelTasks(this);
        }
	}

	public static void reload1() {
		Hyperion.getPlugin().reloadConfig();
		ConfigManager.modifiersConfig.reloadConfig();
		BendingFallingBlock.removeAll();
		TempArmorStand.removeAll();
		CoreMethods.loadAbilities();
		getLog().info("Hyperion BUKKIT Reloaded.");
	}

	public static void reload(ScheduledTask scheduledTask) {
		Hyperion.getPlugin().reloadConfig();
		ConfigManager.modifiersConfig.reloadConfig();
		BendingFallingBlock.removeAll();
		TempArmorStand.removeAll();
		CoreMethods.loadAbilities();
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

}
