package ksndq.fishinghelper.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public class ModConfig {
    public static final String CATEGORY_CLIENT = "client";
    public static final ForgeConfigSpec.BooleanValue FISHING_HELPER_ENABLED;
    public static final String FISHING_HELPER_ENABLED_NAME = "fishingHelperEnabled";
    public static final String FISHING_HELPER_ENABLED_COMMENT = "If true, it assists you with fishing";
    public static final ForgeConfigSpec.BooleanValue SLUGFISH_MODE_ENABLED;
    public static final String SLUGFISH_MODE_ENABLED_NAME = "slugfishModeEnabled";
    public static final String SLUGFISH_MODE_ENABLED_COMMENT = "If true, waits at least 20 seconds before pulling the rod";
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC = BUILDER.build();
    private static final String CATEGORY_CLIENT_COMMENT =
            "These config settings are client-side only";
    public static boolean FISHING_HELPER_ENABLED_DEFAULT = true;
    public static boolean SLUGFISH_MODE_ENABLED_DEFAULT = false;

    static {
        BUILDER.push(CATEGORY_CLIENT);
        FISHING_HELPER_ENABLED = BUILDER
                .comment(FISHING_HELPER_ENABLED_COMMENT)
                .define(FISHING_HELPER_ENABLED_NAME, FISHING_HELPER_ENABLED_DEFAULT);
        SLUGFISH_MODE_ENABLED = BUILDER
                .comment(SLUGFISH_MODE_ENABLED_COMMENT)
                .define(SLUGFISH_MODE_ENABLED_NAME, SLUGFISH_MODE_ENABLED_DEFAULT);
        BUILDER.pop();
    }

    public static void init(Path file) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(file)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        SPEC.setConfig(configData);
    }
}
