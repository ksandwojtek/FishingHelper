package ksndq.fishinghelper.client;

import com.mojang.blaze3d.platform.InputConstants;
import ksndq.fishinghelper.ModInfo;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public final class KeyBindings {
    public static final KeyBindings INSTANCE = new KeyBindings();

    private KeyBindings() {}

    private static final String CATEGORY = "Fishing Helper";

    public final KeyMapping switchFishing = new KeyMapping(
            "key." + ModInfo.MOD_ID + ".switch_fishing",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_B, -1),
            CATEGORY
    );
}
