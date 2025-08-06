package me.moros.hyperion.util;


import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
import me.moros.hyperion.Hyperion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class ThreadUtil {

    private static final boolean THREAD_UTIL_AVAILABLE;
    private static final Class<?> THREAD_UTIL_CLASS;

    static {
        Class<?> threadUtilClass = null;
        try {
            threadUtilClass = Class.forName("com.projectkorra.projectkorra.util.ThreadUtil");
        } catch (ClassNotFoundException e) {
            // ThreadUtil not available, will use Bukkit scheduler
        }
        THREAD_UTIL_CLASS = threadUtilClass;
        THREAD_UTIL_AVAILABLE = THREAD_UTIL_CLASS != null;
    }

    public static void ensureLocation(Location location, Runnable runnable) {
        if (Hyperion.isFolia()) {
            if (Bukkit.isOwnedByCurrentRegion(location) || Bukkit.isStopping()) {
                runnable.run();
                return;
            }

            RegionScheduler scheduler = Bukkit.getRegionScheduler();
            scheduler.execute(Hyperion.plugin, location, runnable);
        } else {
            if (Bukkit.isPrimaryThread()) {
                runnable.run();
                return;
            }

            Bukkit.getScheduler().runTask(Hyperion.plugin, runnable);
        }

    }

    public static void runGlobalLater(Runnable runnable, long delay) {
        if (THREAD_UTIL_AVAILABLE) {
            try {
                THREAD_UTIL_CLASS.getMethod("runGlobalLater", Runnable.class, long.class)
                        .invoke(null, runnable, delay);
            } catch (Exception e) {
                // Fallback to Bukkit scheduler
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        runnable.run();
                    }
                }.runTaskLater(Hyperion.plugin, delay);
            }
        } else {
            new BukkitRunnable() {
                @Override
                public void run() {
                    runnable.run();
                }
            }.runTaskLater(Hyperion.plugin, delay);
        }
    }

    public static void runLocationLater(Location location, Runnable runnable, long delay) {
        if (THREAD_UTIL_AVAILABLE) {
            try {
                THREAD_UTIL_CLASS.getMethod("ensureLocationLater", Location.class, Runnable.class, long.class)
                        .invoke(null, location, runnable, delay);
            } catch (Exception e) {
                // Fallback to Bukkit scheduler
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        runnable.run();
                    }
                }.runTaskLater(Hyperion.plugin, delay);
            }
        } else {
            new BukkitRunnable() {
                @Override
                public void run() {
                    runnable.run();
                }
            }.runTaskLater(Hyperion.plugin, delay);
        }
    }

    public static void runEntityLater(Entity entity, Runnable runnable, long delay) {
        if (THREAD_UTIL_AVAILABLE) {
            try {
                THREAD_UTIL_CLASS.getMethod("ensureEntityLater", Entity.class, Runnable.class, long.class)
                        .invoke(null, entity, runnable, delay);
            } catch (Exception e) {
                // Fallback to Bukkit scheduler
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        runnable.run();
                    }
                }.runTaskLater(Hyperion.plugin, delay);
            }
        } else {
            new BukkitRunnable() {
                @Override
                public void run() {
                    runnable.run();
                }
            }.runTaskLater(Hyperion.plugin, delay);
        }
    }

    public static Object runGlobalTimer(Runnable runnable, long delay, long period) {
        if (THREAD_UTIL_AVAILABLE) {
            try {
                return THREAD_UTIL_CLASS.getMethod("runGlobalTimer", Runnable.class, long.class, long.class)
                        .invoke(null, runnable, delay, period);
            } catch (Exception e) {
                // Fallback to Bukkit scheduler
                return new BukkitRunnable() {
                    @Override
                    public void run() {
                        runnable.run();
                    }
                }.runTaskTimer(Hyperion.plugin, delay, period);
            }
        } else {
            return new BukkitRunnable() {
                @Override
                public void run() {
                    runnable.run();
                }
            }.runTaskTimer(Hyperion.plugin, delay, period);
        }
    }

    public static Object runLocationTimer(Location location, Runnable runnable, long delay, long period) {
        if (THREAD_UTIL_AVAILABLE) {
            try {
                return THREAD_UTIL_CLASS.getMethod("ensureLocationTimer", Location.class, Runnable.class, long.class, long.class)
                        .invoke(null, location, runnable, delay, period);
            } catch (Exception e) {
                // Fallback to Bukkit scheduler
                return new BukkitRunnable() {
                    @Override
                    public void run() {
                        runnable.run();
                    }
                }.runTaskTimer(Hyperion.plugin, delay, period);
            }
        } else {
            return new BukkitRunnable() {
                @Override
                public void run() {
                    runnable.run();
                }
            }.runTaskTimer(Hyperion.plugin, delay, period);
        }
    }

    public static Object runEntityTimer(Entity entity, Runnable runnable, long delay, long period) {
        if (THREAD_UTIL_AVAILABLE) {
            try {
                return THREAD_UTIL_CLASS.getMethod("ensureEntityTimer", Entity.class, Runnable.class, long.class, long.class)
                        .invoke(null, entity, runnable, delay, period);
            } catch (Exception e) {
                // Fallback to Bukkit scheduler
                return new BukkitRunnable() {
                    @Override
                    public void run() {
                        runnable.run();
                    }
                }.runTaskTimer(Hyperion.plugin, delay, period);
            }
        } else {
            return new BukkitRunnable() {
                @Override
                public void run() {
                    runnable.run();
                }
            }.runTaskTimer(Hyperion.plugin, delay, period);
        }
    }

    public static void runAsync(Runnable runnable) {
        if (THREAD_UTIL_AVAILABLE) {
            try {
                THREAD_UTIL_CLASS.getMethod("runAsync", Runnable.class)
                        .invoke(null, runnable);
            } catch (Exception e) {
                // Fallback to Bukkit scheduler
                Bukkit.getScheduler().runTaskAsynchronously(Hyperion.plugin, runnable);
            }
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(Hyperion.plugin, runnable);
        }
    }

    public static Object runAsyncTimer(Runnable runnable, long delay, long period) {
        if (THREAD_UTIL_AVAILABLE) {
            try {
                return THREAD_UTIL_CLASS.getMethod("runAsyncTimer", Runnable.class, long.class, long.class)
                        .invoke(null, runnable, delay, period);
            } catch (Exception e) {
                // Fallback to Bukkit scheduler
                return Bukkit.getScheduler().runTaskTimerAsynchronously(Hyperion.plugin, runnable, delay, period);
            }
        } else {
            return Bukkit.getScheduler().runTaskTimerAsynchronously(Hyperion.plugin, runnable, delay, period);
        }
    }

    public static void cancelTimerTask(Object task) {
        if (THREAD_UTIL_AVAILABLE) {
            try {
                THREAD_UTIL_CLASS.getMethod("cancelTimerTask", Object.class)
                        .invoke(null, task);
            } catch (Exception e) {
                // Fallback to Bukkit task cancellation
                if (task instanceof BukkitTask) {
                    ((BukkitTask) task).cancel();
                }
            }
        } else {
            if (task instanceof BukkitTask) {
                ((BukkitTask) task).cancel();
            }
        }
    }

    public static boolean isThreadUtilAvailable() {
        return THREAD_UTIL_AVAILABLE;
    }

    public static void ensureEntity(Entity entity, Runnable runnable) {
        if (THREAD_UTIL_AVAILABLE) {
            try {
                THREAD_UTIL_CLASS.getMethod("ensureEntity", Entity.class, Runnable.class)
                        .invoke(null, entity, runnable);
            } catch (Exception e) {
                // Fallback to global execution
                runnable.run();
            }
        } else {
            // Fallback to global execution
            runnable.run();
        }
    }
}