package ksndq.fishinghelper.utils;

import com.google.gson.*;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class FishCountUtils {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String CONFIG_FILE_NAME = "fishcaught.json";

    public static void saveFishCount(String fishType, String fishRarity) {
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
