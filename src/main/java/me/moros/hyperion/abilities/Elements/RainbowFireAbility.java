package me.moros.hyperion.abilities.Elements;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ability.Ability;
import com.projectkorra.projectkorra.ability.SubAbility;
import me.moros.hyperion.Elements;
import me.moros.hyperion.Hyperion;
import me.moros.hyperion.util.RainbowParticleEffect;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public abstract class RainbowFireAbility extends FireAbility implements SubAbility {

    public RainbowFireAbility(Player player) {
        super(player);
    }

    public static void playRainbowFireParticles(Location loc, Player player) {
        // You can make radius/height configurable in the future if needed
        int amount = 100; // Number of particles to spawn

        if (loc != null && player != null && player.isOnline()) {
            RainbowParticleEffect.spawnRainbowParticleEffect(loc, amount);
        }
    }

    @Override
    public Class<? extends Ability> getParentAbility() {
        return FireAbility.class;
    }

    @Override
    public Element getElement() {
        return Elements.RAINBOWFIRE;
    }

    public static double getDamageFactor() {
        FileConfiguration config = Hyperion.getPlugin().getConfig();
        return config.getDouble("Properties.Fire.RainbowFire.DamageFactor", 1.0); // default fallback
    }

    public static double getCooldownFactor() {
        FileConfiguration config = Hyperion.getPlugin().getConfig();
        return config.getDouble("Properties.Fire.RainbowFire.CooldownFactor", 1.0);
    }

    public static double getRangeFactor() {
        FileConfiguration config = Hyperion.getPlugin().getConfig();
        return config.getDouble("Properties.Fire.RainbowFire.RangeFactor", 1.0);
    }
}
