package me.moros.hyperion.util;

import com.destroystokyo.paper.ParticleBuilder;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RainbowParticleEffect {

    private static final Random random = new Random();

    // Red and blue handled with these flame particles
    private static final List<Particle> SPECIAL_PARTICLES = Arrays.asList(
            Particle.FLAME,           // Red
            Particle.SOUL_FIRE_FLAME  // Blue
    );

    // Rainbow colors except red and blue — all using REDSTONE with DustOptions
    private static final List<Color> RAINBOW_COLORS = Arrays.asList(
            Color.fromRGB(255, 165, 0),   // Orange
            Color.fromRGB(255, 255, 0),   // Yellow
            Color.fromRGB(0, 255, 0),     // Green
            Color.fromRGB(75, 0, 130),    // Indigo
            Color.fromRGB(138, 43, 226)   // Violet
    );

    public static void spawnRainbowParticleEffect(Location center, int amount) {
        for (int i = 0; i < amount; i++) {

            Particle particle;
            Object data = null;

            if (random.nextDouble() < 0.4) {
                // 40% chance to use flame-style particles (red/blue)
                particle = SPECIAL_PARTICLES.get(random.nextInt(SPECIAL_PARTICLES.size()));
            } else {
                // 60% chance to use REDSTONE color particles
                particle = Particle.REDSTONE;
                Color color = RAINBOW_COLORS.get(random.nextInt(RAINBOW_COLORS.size()));
                data = new DustOptions(color, 1.5f);
            }

            // Random offset around center
            double offsetX = (random.nextDouble() - 0.5) * 2.0;
            double offsetY = random.nextDouble() * 1.5;
            double offsetZ = (random.nextDouble() - 0.5) * 2.0;
            Location loc = center.clone().add(offsetX, offsetY, offsetZ);

            // Velocity
            Vector velocity = new Vector(
                    (random.nextDouble() - 0.5) * 0.2,
                    random.nextDouble() * 0.2,
                    (random.nextDouble() - 0.5) * 0.2
            );

            new ParticleBuilder(particle)
                    .location(loc)
                    .count(0)
                    .offset((float) velocity.getX(), (float) velocity.getY(), (float) velocity.getZ())
                    .extra(0)
                    .data(data)
                    .spawn();
        }
    }
}
