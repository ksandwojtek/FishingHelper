package ksndq.fishinghelper.event.fishing;

import ksndq.fishinghelper.ModConfig;
import ksndq.fishinghelper.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModInfo.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class FishingBobberEvent {

    public static double bobberTime;

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        if (!ModConfig.FISHING_HELPER_ENABLED.get() | !ModConfig.SLUGFISH_MODE_ENABLED.get()) return;
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;
        if (player.fishing == null) return;
        bobberTime = (double) player.fishing.tickCount / 20;
    }
}
