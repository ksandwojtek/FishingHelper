package net.ksndq.fishinghelper.client.handler;


import net.ksndq.fishinghelper.FishingHelper;
import net.ksndq.fishinghelper.client.KeyBindings;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.SimpleChannel;
import org.checkerframework.checker.units.qual.A;

import java.util.concurrent.*;


@Mod.EventBusSubscriber(modid = FishingHelper.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeHandler {

    private static boolean isEventRunning = false;
    private static int counter = 4;

    @SubscribeEvent
    public static void clientSound(SoundEvent.SoundSourceEvent event) throws InterruptedException {
        if (!FishingHelper.enabled || isEventRunning) {return;}
        Minecraft mc = Minecraft.getInstance();
        if (mc.isSingleplayer()) {return;}
        LocalPlayer player = mc.player;
        if (player == null || player.hasContainerOpen()) {return;}
        ItemStack itemStack = player.getInventory().getSelected();
        if (itemStack.getItem() != Items.FISHING_ROD) {return;}
        if(!event.getName().equals("block.note_block.pling") || event.getSound().getPitch() != 1.0) {return;}
        isEventRunning = true;
        counter -= 1;
        new Thread(() -> {
            try {
                if(counter == 0) {
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
