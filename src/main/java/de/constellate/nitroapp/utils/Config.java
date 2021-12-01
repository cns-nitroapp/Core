package de.constellate.nitroapp.utils;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    private final File file;
    private final YamlConfiguration config;

    public Config(String filename) {
        File dir = new File("./plugins/Core/");

        if (!dir.exists()) {
            dir.mkdirs();
        }

        this.file = new File(dir, filename + ".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public File getFile() {
        return file;
    }

    public void set(String path, Object value) {
        config.set(path, value);
        save();
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}