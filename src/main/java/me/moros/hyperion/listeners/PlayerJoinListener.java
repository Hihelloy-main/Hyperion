package me.moros.hyperion.listeners;

import me.moros.hyperion.Hyperion;
import me.moros.hyperion.util.HexColor;
import me.moros.hyperion.util.UpdateChecker;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.isOp()) return;

        UpdateChecker checker = Hyperion.getUpdateChecker();
        if (checker == null) {
            player.sendMessage(HexColor.ORANGE + "[Hyperion] " + ChatColor.RED + "Update checker not initialized.");
            return;
        }

        if (!checker.hasChecked()) {
            player.sendMessage(HexColor.ORANGE + "[Hyperion] " + ChatColor.GRAY + "Checking for updates...");
            return;
        }

        if (checker.isUpdateAvailable()) {
            String current = checker.getCurrentVersion() != null ? checker.getCurrentVersion() : "unknown";
            String latest = checker.getLatestVersion() != null ? checker.getLatestVersion() : "unknown";

            player.sendMessage(HexColor.ORANGE + "[Hyperion] " + ChatColor.RESET + HexColor.YELLOW + ChatColor.UNDERLINE + "A new version is available!");
            player.sendMessage(ChatColor.GRAY + "You're running: " + ChatColor.RED + current);
            player.sendMessage(ChatColor.GRAY + "Latest version: " + ChatColor.GREEN + latest);
            player.sendMessage(ChatColor.GRAY + "Download: " + ChatColor.UNDERLINE + ChatColor.BLUE + "https://github.com/Hihelloy-main/Hyperion");

        } else {
            String latest = checker.getLatestVersion() != null ? checker.getLatestVersion() : "unknown";

            player.sendMessage(HexColor.ORANGE + "[Hyperion] " + ChatColor.RESET + ChatColor.GREEN + "No updates available. You're on the latest version.");
            player.sendMessage(ChatColor.GRAY + "Latest GitHub version: " + ChatColor.GREEN + latest);
        }

    }
}
