package net.ksndq.fishinghelper.client.handler;


import com.google.gson.*;
import net.ksndq.fishinghelper.FishingHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = FishingHelper.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientChatMessageHandler {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String CONFIG_FILE_NAME = "fishcaught.json";

    @SubscribeEvent
    public static void clientChatMessage(ClientChatReceivedEvent event) {
        String msg = event.getMessage().getString();
        if (!msg.startsWith("TROPHY FISH!")) return;
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

    private static void saveFishCount(String fishType, String fishRarity) {
        Path configPath = FMLPaths.CONFIGDIR.get();
        File configFile = new File(configPath.toFile(), CONFIG_FILE_NAME);

        try {
            Files.createDirectories(configPath);

            Map<String, Map<String, Integer>> fishCounts = loadFishCounts(configFile);

            Map<String, Integer> rarityCounts = fishCounts.computeIfAbsent(fishType, k -> new HashMap<>());

            int count = rarityCounts.getOrDefault(fishRarity, 0);
            rarityCounts.put(fishRarity, count + 1);

            try (FileWriter writer = new FileWriter(configFile)) {
                GSON.toJson(fishCounts, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Map<String, Integer>> loadFishCounts(File configFile) {
        Map<String, Map<String, Integer>> fishCounts = new HashMap<>();

        try {
            if (configFile.exists()) {
                JsonObject json = JsonParser.parseReader(new FileReader(configFile)).getAsJsonObject();
                for (Map.Entry<String, JsonElement> fishEntry : json.entrySet()) {
                    String fishType = fishEntry.getKey();
                    JsonObject rarityCounts = fishEntry.getValue().getAsJsonObject();
                    Map<String, Integer> rarityCountMap = new HashMap<>();

                    for (Map.Entry<String, JsonElement> rarityEntry : rarityCounts.entrySet()) {
                        String fishRarity = rarityEntry.getKey();
                        int count = rarityEntry.getValue().getAsInt();
                        rarityCountMap.put(fishRarity, count);
                    }

                    fishCounts.put(fishType, rarityCountMap);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fishCounts;
    }
}