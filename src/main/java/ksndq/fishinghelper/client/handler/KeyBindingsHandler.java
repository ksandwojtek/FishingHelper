package ksndq.fishinghelper.client.handler;

import ksndq.fishinghelper.ModInfo;
import ksndq.fishinghelper.client.KeyBindings;
import ksndq.fishinghelper.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static ksndq.fishinghelper.config.ModConfig.SPEC;

@Mod.EventBusSubscriber(modid = ModInfo.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class KeyBindingsHandler {
    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        if (!KeyBindings.INSTANCE.switchFishing.isDown()) {return;}
        if(KeyBindings.INSTANCE.switchFishing.consumeClick()) {
            assert Minecraft.getInstance().player != null;
            String message = !ModConfig.FISHING_HELPER_ENABLED.get() ? "§a§lEnabled" : "§c§lDisabled";
            Minecraft.getInstance().player.sendSystemMessage(Component.literal(ModInfo.PREFIX + message));
            ModConfig.FISHING_HELPER_ENABLED.set(!ModConfig.FISHING_HELPER_ENABLED.get());
            SPEC.save();
    }}
}
