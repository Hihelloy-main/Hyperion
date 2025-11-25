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

package me.moros.hyperion.commands;

import com.projectkorra.projectkorra.command.PKCommand;
import me.moros.hyperion.Hyperion;
import me.moros.hyperion.configuration.ConfigManager;
import me.moros.hyperion.util.HexColor;
import me.moros.hyperion.util.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static me.moros.hyperion.Hyperion.isFolia;
import static me.moros.hyperion.Hyperion.scheduler;

public class HyperionCommand extends PKCommand {
	public HyperionCommand() {
		super("hyperion", "/bending hyperion <reload>", "Show information about Hyperion and optionally reload its config.", new String[]{"hyperion"});
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender) || !correctLength(sender, args.size(), 0, 1)) return;

		if (args.size() == 0) {
			sender.sendMessage(ChatColor.GREEN + "Hyperion Version: " + ChatColor.RED + Hyperion.getVersion());
			sender.sendMessage(ChatColor.GREEN + "Developed by: " + ChatColor.RED + Hyperion.getAuthor());
		} else if (args.size() == 1) {
			String sub = args.get(0).toLowerCase();

			if (sub.equals("reload") && hasPermission(sender, "reload")) {
				if (isFolia) {
					scheduler.global().run(Hyperion::reload);
				} else {
					Bukkit.getScheduler().runTask(Hyperion.plugin, Hyperion::reload1);
				}
				sender.sendMessage(ChatColor.GREEN + "Hyperion config has been reloaded.");
			}


			else if (sub.equals("checkupdate") && hasPermission(sender, "checkupdate")) {
				UpdateChecker checker = Hyperion.getUpdateChecker();

				if (checker == null) {
					sender.sendMessage(ChatColor.RED + "Update checker not initialized.");
					return;
				}

                if (!checker.hasChecked()) {
					sender.sendMessage(ChatColor.GRAY + "Still checking for updates, please try again shortly.");
					return;
				}

				if (checker.isUpdateAvailable()) {
					String current = checker.getCurrentVersion() != null ? checker.getCurrentVersion() : "unknown";
					String latest = checker.getLatestVersion() != null ? checker.getLatestVersion() : "unknown";

					sender.sendMessage(HexColor.ORANGE + "[Hyperion] " + "A new version is available!");
					sender.sendMessage(ChatColor.GRAY + "You're running: " + ChatColor.RED + current);
					sender.sendMessage(ChatColor.GRAY + "Latest version: " + ChatColor.GREEN + latest);
					sender.sendMessage(ChatColor.GRAY + "Download: " + ChatColor.UNDERLINE + ChatColor.BLUE + "https://github.com/Hihelloy-main/Hyperion");
				} else {
					sender.sendMessage(ChatColor.GREEN + "You're running the latest version of Hyperion.");
				}
			}
		}
	}

}
