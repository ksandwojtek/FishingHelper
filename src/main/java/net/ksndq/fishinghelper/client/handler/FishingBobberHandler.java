package net.ksndq.fishinghelper.client.handler;

import net.ksndq.fishinghelper.FishingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.ksndq.fishinghelper.misc.Configuration.enabled;
import static net.ksndq.fishinghelper.misc.Configuration.slugMode;

@Mod.EventBusSubscriber(modid = FishingHelper.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class FishingBobberHandler {

    public static double bobberTime;

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        if (!enabled() | !slugMode()) return;
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;
        if (player.fishing == null) return;
        bobberTime = (double) player.fishing.tickCount / 20;
    }
}
