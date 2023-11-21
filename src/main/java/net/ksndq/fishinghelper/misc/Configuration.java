package net.ksndq.fishinghelper.misc;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import org.apache.commons.lang3.tuple.Pair;

public class Configuration {
    public static final ForgeConfigSpec CONFIG_SPEC;
    private static final Configuration CONFIG;

    static {
        Pair<Configuration, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Configuration::new);

        CONFIG_SPEC = specPair.getRight();
        CONFIG = specPair.getLeft();
    }

    public final BooleanValue enabled;
    public final ForgeConfigSpec.ConfigValue<String> prefix;

    Configuration(ForgeConfigSpec.Builder builder) {
        enabled = builder
                .comment("enable fishing helper")
                .define("enabled", true);
        prefix = builder
                .comment("general command output prefix")
                .define("text", "§3\uD83C\uDFA3 §b§lFishingHelper §c- §r");
    }

    public static boolean enabled() {
        return CONFIG.enabled.get();
    }

    public static String prefix() {
        return CONFIG.prefix.get();
    }

    public static void toggleEnabled() {
        CONFIG.enabled.set(!enabled());
        CONFIG_SPEC.save();
    }
}
