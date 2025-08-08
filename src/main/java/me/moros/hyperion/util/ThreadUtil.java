package me.moros.hyperion.util;

import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
import me.moros.hyperion.Hyperion;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;



public class ThreadUtil {

    private static final Plugin PLUGIN = Hyperion.plugin;
    private static final boolean THREAD_UTIL_AVAILABLE;
    private static final Class<?> THREAD_UTIL_CLASS;

    static {
        Class<?> threadUtilClass = null;
        try {
            threadUtilClass = Class.forName("com.projectkorra.projectkorra.util.ThreadUtil");
        } catch (ClassNotFoundException ignored) {}
        THREAD_UTIL_CLASS = threadUtilClass;
        THREAD_UTIL_AVAILABLE = THREAD_UTIL_CLASS != null;
    }

    public static void ensureLocation(Location location, Runnable runnable) {
        if (Hyperion.isFolia() || Hyperion.isLuminol()) {
            if (Bukkit.isOwnedByCurrentRegion(location) || Bukkit.isStopping()) {
                runnable.run();
                return;
            }
            RegionScheduler scheduler = Bukkit.getRegionScheduler();
            scheduler.execute(PLUGIN, location, runnable);
        } else {
            if (Bukkit.isPrimaryThread()) {
                runnable.run();
            } else {
                Bukkit.getScheduler().runTask(PLUGIN, runnable);
            }
        }
    }

    public static void runGlobalLater(Runnable runnable, long delayTicks) {
        if (Hyperion.isFolia() || Hyperion.isLuminol()) {
            Bukkit.getGlobalRegionScheduler().runDelayed(PLUGIN, task -> runnable.run(), delayTicks);
        } else {
            Bukkit.getScheduler().runTaskLater(PLUGIN, runnable, delayTicks);
        }
    }

    public static void runLocationLater(Location location, Runnable runnable, long delayTicks) {
        if (Hyperion.isFolia() || Hyperion.isLuminol()) {
            Bukkit.getRegionScheduler().runDelayed(PLUGIN, location, task -> runnable.run(), delayTicks);
        } else {
            Bukkit.getScheduler().runTaskLater(PLUGIN, runnable, delayTicks);
        }
    }

    public static void runEntityLater(Location loc, Runnable runnable, long delayTicks) {
        if (Hyperion.isFolia() || Hyperion.isLuminol()) {
            Bukkit.getRegionScheduler().runDelayed(PLUGIN, loc, task -> runnable.run(), delayTicks);
        } else {
            Bukkit.getScheduler().runTaskLater(PLUGIN, runnable, delayTicks);
        }
    }

    public static BukkitTask runGlobalTimer(Runnable runnable, long delayTicks, long periodTicks) {
        if (Hyperion.isFolia() || Hyperion.isLuminol()) {
            throw new UnsupportedOperationException("Global repeating tasks must be managed differently in Folia.");
        }
        return Bukkit.getScheduler().runTaskTimer(PLUGIN, runnable, delayTicks, periodTicks);
    }

    public static BukkitTask runLocationTimer(Location location, Runnable runnable, long delayTicks, long periodTicks) {
        if (Hyperion.isFolia() || Hyperion.isLuminol()) {
            throw new UnsupportedOperationException("Region-based repeating tasks must use Folia task management.");
        }
        return Bukkit.getScheduler().runTaskTimer(PLUGIN, runnable, delayTicks, periodTicks);
    }

    public static BukkitTask runEntityTimer(Entity entity, Runnable runnable, long delayTicks, long periodTicks) {
        if (Hyperion.isFolia() || Hyperion.isLuminol()) {
            throw new UnsupportedOperationException("Entity-based repeating tasks must use Folia task management.");
        }
        return Bukkit.getScheduler().runTaskTimer(PLUGIN, runnable, delayTicks, periodTicks);
    }

    public static void runAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(PLUGIN, runnable);
    }

    public static BukkitTask runAsyncTimer(Runnable runnable, long delayTicks, long periodTicks) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(PLUGIN, runnable, delayTicks, periodTicks);
    }

    public static void cancelTimerTask(Object task) {
        if (task instanceof BukkitTask bukkitTask) {
            bukkitTask.cancel();
        }
    }

    public static void ensureEntity(Entity entity, Runnable runnable) {
        if (Hyperion.isFolia() || Hyperion.isLuminol()) {
            if (entity.getLocation() == null) {
                runnable.run();
                return;
            }
            ensureLocation(entity.getLocation(), runnable);
        } else {
            runnable.run();
        }
    }

    public static boolean isThreadUtilAvailable() {
        return THREAD_UTIL_AVAILABLE;
    }
}
