package ksndq.fishinghelper.client.handler;

import ksndq.fishinghelper.ModInfo;
import ksndq.fishinghelper.config.ModConfig;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModInfo.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientFishingHandler {

    private static boolean isEventRunning = false;
    private static int counter = 4;

    @SubscribeEvent
    public static void clientSound(SoundEvent.SoundSourceEvent event) {
        if (!ModConfig.FISHING_HELPER_ENABLED.get() || isEventRunning) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.isSingleplayer()) return;
        LocalPlayer player = mc.player;
        if (player == null || player.hasContainerOpen()) return;
        ItemStack itemStack = player.getInventory().getSelected();
        if (itemStack.getItem() != Items.FISHING_ROD) return;
        if (ModConfig.SLUGFISH_MODE_ENABLED.get()) {
            if (FishingBobberHandler.bobberTime < 20) return;
        }
        if (!event.getName().equals("block.note_block.pling") || event.getSound().getPitch() != 1.0) return;
        isEventRunning = true;
        counter -= 1;
        new Thread(() -> {
            try {
                if (counter == 0) {
                    for (int i = 0; i < 10; i++) {
                        player.setXRot((float) ((double) player.getViewXRot(0) + 0.25));
                        Thread.sleep(2L);
                    }
                    counter = 4;
                } else if (counter == 3) {
                    for (int i = 0; i < 10; i++) {
                        player.setXRot((float) ((double) player.getViewXRot(0) - 0.25));
                        Thread.sleep(2L);
                    }
                }
                Thread.sleep(200L);
                KeyMapping.click(mc.options.keyUse.getKey());
                Thread.sleep(200L);
                KeyMapping.click(mc.options.keyUse.getKey());
                Thread.sleep(400L);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                isEventRunning = false;
            }
        }).start();
    }
}
