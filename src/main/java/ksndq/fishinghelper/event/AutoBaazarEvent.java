package ksndq.fishinghelper.event;


import ksndq.fishinghelper.ModConfig;
import ksndq.fishinghelper.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModInfo.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class AutoBaazarEvent {
    @SubscribeEvent
    public static void clientOnChatMessage(ClientChatReceivedEvent event) {
        if (!ModConfig.FISHING_HELPER_ENABLED.get()) return;
        String msg = event.getMessage().getString();
        if (!msg.startsWith("TROPHY FISH!") || Minecraft.getInstance().player == null) return;
        if (!String.valueOf(Minecraft.getInstance().player.connection.getConnection().getRemoteAddress()).contains("hypixel.net"))
            return;
        System.out.println("INV FULL");
    }
}
