//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.moros.hyperion.util;

import com.projectkorra.projectkorra.GeneralMethods;
import org.bukkit.Location;
import org.bukkit.Particle;


public enum ParticleEffect {
    ASH(Particle.ASH),
    BLOCK_CRACK(Particle.BLOCK_CRACK),
    BLOCK_DUST(Particle.BLOCK_DUST),
    BUBBLE_COLUMN_UP(Particle.BUBBLE_COLUMN_UP),
    BUBBLE_POP(Particle.BUBBLE_POP),
    CAMPFIRE_COSY_SMOKE(Particle.CAMPFIRE_COSY_SMOKE),
    CAMPFIRE_SIGNAL_SMOKE(Particle.CAMPFIRE_SIGNAL_SMOKE),
    CLOUD(Particle.CLOUD),
    COMPOSTER(Particle.COMPOSTER),
    CRIMSON_SPORE(Particle.CRIMSON_SPORE),
    CRIT(Particle.CRIT),
    CRIT_MAGIC(Particle.CRIT_MAGIC),
    MAGIC_CRIT(Particle.CRIT_MAGIC),
    CURRENT_DOWN(Particle.CURRENT_DOWN),
    DAMAGE_INDICATOR(Particle.DAMAGE_INDICATOR),
    DOLPHIN(Particle.DOLPHIN),
    DRAGON_BREATH(Particle.DRAGON_BREATH),
    DRIP_LAVA(Particle.DRIP_LAVA),
    DRIP_WATER(Particle.DRIP_WATER),
    DRIPPING_HONEY(Particle.DRIPPING_HONEY),
    DRIPPING_OBSIDIAN_TEAR(Particle.DRIPPING_OBSIDIAN_TEAR),
    ENCHANTMENT_TABLE(Particle.ENCHANTMENT_TABLE),
    END_ROD(Particle.END_ROD),
    EXPLOSION_HUGE(Particle.EXPLOSION_HUGE),
    HUGE_EXPLOSION(Particle.EXPLOSION_HUGE),
    EXPLOSION_LARGE(Particle.EXPLOSION_LARGE),
    LARGE_EXPLODE(Particle.EXPLOSION_LARGE),
    EXPLOSION_NORMAL(Particle.EXPLOSION_NORMAL),
    EXPLODE(Particle.EXPLOSION_NORMAL),
    FALLING_DUST(Particle.FALLING_DUST),
    FALLING_HONEY(Particle.FALLING_HONEY),
    FALLING_LAVA(Particle.FALLING_LAVA),
    FALLING_NECTAR(Particle.FALLING_NECTAR),
    FALLING_OBSIDIAN_TEAR(Particle.FALLING_OBSIDIAN_TEAR),
    FALLING_WATER(Particle.FALLING_WATER),
    FIREWORKS_SPARK(Particle.FIREWORKS_SPARK),
    FLAME(Particle.FLAME),
    FLASH(Particle.FLASH),
    HEART(Particle.HEART),
    ITEM_CRACK(Particle.ITEM_CRACK),
    LANDING_HONEY(Particle.LANDING_HONEY),
    LANDING_LAVA(Particle.LANDING_LAVA),
    LANDING_OBSIDIAN_TEAR(Particle.LANDING_OBSIDIAN_TEAR),
    LAVA(Particle.LAVA),
    MOB_APPEARANCE(Particle.MOB_APPEARANCE),
    NAUTILUS(Particle.NAUTILUS),
    NOTE(Particle.NOTE),
    PORTAL(Particle.PORTAL),
    REDSTONE(Particle.REDSTONE),
    RED_DUST(Particle.REDSTONE),
    REVERSE_PORTAL(Particle.REVERSE_PORTAL),
    SLIME(Particle.SLIME),
    SMOKE_NORMAL(Particle.SMOKE_NORMAL),
    SMOKE(Particle.SMOKE_NORMAL),
    SMOKE_LARGE(Particle.SMOKE_LARGE),
    LARGE_SMOKE(Particle.SMOKE_LARGE),
    SNEEZE(Particle.SNEEZE),
    SNOW_SHOVEL(Particle.SNOW_SHOVEL),
    SNOWBALL(Particle.SNOWBALL),
    SNOWBALL_PROOF(Particle.SNOWBALL),
    SOUL(Particle.SOUL),
    SOUL_FIRE_FLAME(Particle.SOUL_FIRE_FLAME),
    SPELL(Particle.SPELL),
    SPELL_INSTANT(Particle.SPELL_INSTANT),
    INSTANT_SPELL(Particle.SPELL_INSTANT),
    SPELL_MOB(Particle.SPELL_MOB),
    MOB_SPELL(Particle.SPELL_MOB),
    SPELL_MOB_AMBIENT(GeneralMethods.getMCVersion() >= 1205 ? Particle.SPELL_MOB : Particle.SPELL_MOB_AMBIENT),
    MOB_SPELL_AMBIENT(GeneralMethods.getMCVersion() >= 1205 ? Particle.SPELL_MOB : Particle.SPELL_MOB_AMBIENT),
    SPELL_WITCH(Particle.SPELL_WITCH),
    WITCH_SPELL(Particle.SPELL_WITCH),
    SPIT(Particle.SPIT),
    SQUID_INK(Particle.SQUID_INK),
    SUSPENDED(Particle.SUSPENDED),
    SUSPEND(Particle.SUSPENDED),
    SUSPENDED_DEPTH(Particle.SUSPENDED_DEPTH),
    DEPTH_SUSPEND(Particle.SUSPENDED_DEPTH),
    SWEEP_ATTACK(Particle.SWEEP_ATTACK),
    TOTEM(Particle.TOTEM),
    TOWN_AURA(Particle.TOWN_AURA),
    VILLAGER_ANGRY(Particle.VILLAGER_ANGRY),
    ANGRY_VILLAGER(Particle.VILLAGER_ANGRY),
    VILLAGER_HAPPY(Particle.VILLAGER_HAPPY),
    HAPPY_VILLAGER(Particle.VILLAGER_HAPPY),
    WARPED_SPORE(Particle.WARPED_SPORE),
    WATER_BUBBLE(Particle.WATER_BUBBLE),
    BUBBLE(Particle.WATER_BUBBLE),
    WATER_DROP(Particle.WATER_DROP),
    WATER_SPLASH(Particle.WATER_SPLASH),
    SPLASH(Particle.WATER_SPLASH),
    WATER_WAKE(Particle.WATER_WAKE),
    WAKE(Particle.WATER_WAKE),
    WHITE_ASH(Particle.WHITE_ASH),
    ELECTRIC_SPARK(Particle.ELECTRIC_SPARK);

    Particle particle;
    Class<?> dataClass;

    private ParticleEffect(Particle particle) {
        this.particle = particle;
        this.dataClass = particle.getDataType();
    }

    public Particle getParticle() {
        return this.particle;
    }

    public void display(Location loc, int amount) {
        this.display(loc, amount, (double)0.0F, (double)0.0F, (double)0.0F);
    }

    public void display(Location loc, int amount, double offsetX, double offsetY, double offsetZ) {
        this.display(loc, amount, offsetX, offsetY, offsetZ, (double)0.0F);
    }

    public void display(Location loc, int amount, double offsetX, double offsetY, double offsetZ, double extra) {
        loc.getWorld().spawnParticle(this.particle, loc, amount, offsetX, offsetY, offsetZ, extra, (Object)null, true);
    }

    public void display(Location loc, int amount, double offsetX, double offsetY, double offsetZ, Object data) {
        this.display(loc, amount, offsetX, offsetY, offsetZ, (double)0.0F, data);
    }

    public void display(Location loc, int amount, double offsetX, double offsetY, double offsetZ, double extra, Object data) {
        if (!this.dataClass.isAssignableFrom(Void.class) && data != null && this.dataClass.isAssignableFrom(data.getClass())) {
            loc.getWorld().spawnParticle(this.particle, loc, amount, offsetX, offsetY, offsetZ, extra, data, true);
        } else {
            this.display(loc, amount, offsetX, offsetY, offsetZ, extra);
        }

    }
}
