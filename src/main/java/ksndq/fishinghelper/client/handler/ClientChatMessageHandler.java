package ksndq.fishinghelper.client.handler;

import ksndq.fishinghelper.ModInfo;
import ksndq.fishinghelper.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static ksndq.fishinghelper.utils.FishCountUtils.saveFishCount;

@Mod.EventBusSubscriber(modid = ModInfo.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientChatMessageHandler {

    @SubscribeEvent
    public static void clientChatMessage(ClientChatReceivedEvent event) {
        if (!ModConfig.FISHING_HELPER_ENABLED.get()) return;
        String msg = event.getMessage().getString();
        if (!msg.startsWith("TROPHY FISH!") || Minecraft.getInstance().player == null) return;
        if (!String.valueOf(Minecraft.getInstance().player.connection.getConnection().getRemoteAddress()).contains("hypixel.net"))
            return;
        msg = msg.substring(26).replace(".", "");
        String[] arguments = msg.split(" ");
        String fishRarity, fishType;
        if (arguments.length == 3) {
            fishType = (arguments[0] + " " + arguments[1]).replace(" ", "_");
            fishRarity = arguments[2];
        } else {
            fishType = arguments[0];
            fishRarity = arguments[1];
        }

        saveFishCount(fishType.toLowerCase(), fishRarity);
    }
}