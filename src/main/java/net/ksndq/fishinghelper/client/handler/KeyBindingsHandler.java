package net.ksndq.fishinghelper.client.handler;


import net.ksndq.fishinghelper.FishingHelper;
import net.ksndq.fishinghelper.client.KeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.ksndq.fishinghelper.misc.Configuration.*;

@Mod.EventBusSubscriber(modid = FishingHelper.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class KeyBindingsHandler {
    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        assert Minecraft.getInstance().player != null;
        if (!KeyBindings.INSTANCE.switchFishing.isDown()) {return;}
        if(KeyBindings.INSTANCE.switchFishing.consumeClick()) {
            assert Minecraft.getInstance().player != null;
            String message = !enabled() ? "§a§lEnabled" : "§c§lDisabled";
            Minecraft.getInstance().player.sendSystemMessage(Component.literal(prefix() + message));
            toggleEnabled();
//            FishingHelper.enabled = !FishingHelper.enabled;
    }}
}
