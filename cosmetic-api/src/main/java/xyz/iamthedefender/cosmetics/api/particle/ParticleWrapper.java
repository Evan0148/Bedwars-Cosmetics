package xyz.iamthedefender.cosmetics.api.particle;

import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedParticle;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.iamthedefender.cosmetics.api.cosmetics.Cosmetics;
import xyz.iamthedefender.cosmetics.api.util.Utility;
import xyz.iamthedefender.cosmetics.api.versionsupport.IVersionSupport;

import java.util.Objects;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;


@Getter
public class ParticleWrapper {

    /**
     * Maps legacy (pre-1.20.5) Bukkit particle names to the names
     * used on modern (1.20.5+) servers. On modern servers the old names
     * no longer exist in the {@code org.bukkit.Particle} enum, so configs
     * (and cosmetics) written for older versions would silently fail.
     */
    private static final Map<String, String> LEGACY_PARTICLE_NAMES;

    static {
        Map<String, String> map = new HashMap<>();
        // Bukkit 1.20.5 renamed these to match vanilla particle names
        map.put("REDSTONE", "DUST");
        map.put("REDSTONE_TRANSITION", "DUST_COLOR_TRANSITION");
        map.put("SPELL", "EFFECT");
        map.put("SPELL_INSTANT", "INSTANT_EFFECT");
        map.put("SPELL_MOB", "ENTITY_EFFECT");
        map.put("SPELL_MOB_AMBIENT", "ENTITY_EFFECT");
        map.put("SPELL_WITCH", "WITCH");
        map.put("EXPLOSION_NORMAL", "POOF");
        map.put("EXPLOSION_LARGE", "EXPLOSION");
        map.put("EXPLOSION_HUGE", "EXPLOSION_EMITTER");
        map.put("SMOKE_NORMAL", "SMOKE");
        map.put("SMOKE_LARGE", "LARGE_SMOKE");
        map.put("CRIT_MAGIC", "ENCHANTED_HIT");
        map.put("FIREWORKS_SPARK", "FIREWORK");
        map.put("VILLAGER_ANGRY", "ANGRY_VILLAGER");
        map.put("VILLAGER_HAPPY", "HAPPY_VILLAGER");
        map.put("SLIME", "ITEM_SLIME");
        map.put("SNOW_SHOVEL", "ITEM_SNOWBALL");
        map.put("SNOWBALL", "ITEM_SNOWBALL");
        map.put("BLOCK_CRACK", "BLOCK");
        map.put("BLOCK_DUST", "BLOCK");
        map.put("ENCHANTMENT_TABLE", "ENCHANT");
        map.put("TOTEM", "TOTEM_OF_UNDYING");
        map.put("MOB_APPEARANCE", "ELDER_GUARDIAN");
        map.put("WATER_BUBBLE", "BUBBLE");
        map.put("WATER_SPLASH", "SPLASH");
        map.put("WATER_FISHING", "FISHING");
        map.put("DRIP_WATER", "DRIPPING_WATER");
        map.put("DRIP_LAVA", "DRIPPING_LAVA");
        map.put("SUSPEND", "BUBBLE");
        map.put("SUSPENDED", "BUBBLE");
        map.put("SUSPENDED_DEPTH", "BUBBLE");
        map.put("FOOTSTEP", "CLOUD");
        map.put("TOWN_AURA", "MYCELIUM");
        map.put("COLOURED_ARROW", "CRIT");
        map.put("CAVE_VIBES", "SONIC_BOOM");
        map.put("VIBRATION_INFINITE", "VIBRATION");
        map.put("FLASH", "FLASH");
        map.put("BUBBLE_POP_UP", "BUBBLE_POP");
        map.put("SCRAP", "SCRAPE");
        LEGACY_PARTICLE_NAMES = Collections.unmodifiableMap(map);
    }

    /**
     * Returns the modern particle name for a legacy name, or {@code null}
     * if the name is not a known legacy name.
     */
    public static @Nullable String mapLegacyParticleName(@NotNull String legacyName) {
        return LEGACY_PARTICLE_NAMES.get(legacyName.toUpperCase());
    }

    private final @Nullable EnumWrappers.Particle wrapperParticle;
    private final @Nullable WrappedParticle<?> newWrapperParticle;

    public ParticleWrapper(@Nullable EnumWrappers.Particle wrapperParticle, @Nullable WrappedParticle<?> newWrapperParticle) {
        this.wrapperParticle = wrapperParticle;
        this.newWrapperParticle = newWrapperParticle;

        if (wrapperParticle == null && newWrapperParticle == null) {
            throw new IllegalArgumentException("Both arguments cannot be null!");
        }
    }

    public ParticleWrapper(@Nullable EnumWrappers.Particle wrapperParticle) {
        this(wrapperParticle, null);
    }

    public ParticleWrapper(@Nullable WrappedParticle<?> newWrapperParticle) {
        this(null, newWrapperParticle);
    }

    public @NotNull IVersionSupport support() {
        return Utility.getApi().getVersionSupport();
    }

    public static @NotNull Optional<ParticleWrapper> getParticle(@NotNull String name) {
        Objects.requireNonNull(name, "The particle name cannot be null!");

        name = name.toUpperCase();

        ParticleWrapper particleWrapper;

        try {
            Class.forName("org.bukkit.Particle");

            org.bukkit.Particle particle;
            try {
                particle = org.bukkit.Particle.valueOf(name);
            } catch (IllegalArgumentException legacyName) {
                // 1.20.5+ servers removed the legacy particle names from the enum
                String mapped = mapLegacyParticleName(name);
                particle = mapped == null ? null : org.bukkit.Particle.valueOf(mapped);
            }

            if (particle == null) throw new IllegalArgumentException("Unknown particle: " + name);

            particleWrapper = new ParticleWrapper(WrappedParticle.create(particle, null));
        } catch (Exception exception) {
            try {
                particleWrapper = new ParticleWrapper(EnumWrappers.Particle.valueOf(name));
                Objects.requireNonNull(particleWrapper.getWrapperParticle());

                if (!Utility.getApi().getVersionSupport().isValidParticle(particleWrapper.getWrapperParticle().name())) {
                    throw new RuntimeException("Invalid particle: " + particleWrapper.getWrapperParticle().getName());
                }

            }catch (Exception exception1) {
                particleWrapper = null;
            }
        }


        return Optional.ofNullable(particleWrapper);
    }
}
