package net.ksndq.fishinghelper.client.handler;

import net.ksndq.fishinghelper.FishingHelper;
import net.ksndq.fishinghelper.client.KeyBindings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.awt.*;

@Mod.EventBusSubscriber(modid = FishingHelper.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModHandler {
//    @SubscribeEvent
//    public static void clientSetup(FMLClientSetupEvent event) {
//        event.enqueueWork(() -> {
//
//        })

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(KeyBindings.INSTANCE.switchFishing);
    }
}
