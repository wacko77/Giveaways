package me.wacko.giveaways.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.wacko.giveaways.model.Giveaway;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GiveawaysStorage {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILE_PATH = "plugins/Giveaways/giveaways.json";

    // Load giveaways from file
    public static List<Giveaway> loadGiveaways() {
        List<Giveaway> giveaways = new ArrayList<>();
        try {
            File file = new File(FILE_PATH);
            if (file.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    giveaways.addAll(Arrays.asList(gson.fromJson(reader, Giveaway[].class)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return giveaways;
    }

    // Save giveaways to file
    public static void saveGiveaways(List<Giveaway> giveaways) {
        try {
            File file = new File(FILE_PATH);
            File parentDir = file.getParentFile();
            if (!parentDir.exists() && !parentDir.mkdirs()) {
                throw new IOException("Failed to create directories: " + parentDir.getAbsolutePath());
            }
            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(giveaways, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Add a new giveaway
    public static void addGiveaway(Giveaway giveaway) {
        List<Giveaway> giveaways = loadGiveaways();
        giveaways.add(giveaway);
        saveGiveaways(giveaways);
    }

    public static void removeGiveaway(Giveaway giveaway) {
        List<Giveaway> giveaways = loadGiveaways();
        giveaways.removeIf(g -> g.getId() == giveaway.getId());
        saveGiveaways(giveaways);
    }

}
