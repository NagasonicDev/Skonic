package au.nagasonic.skonic.elements.util;

import au.nagasonic.skonic.Skonic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class Config {

    private final Skonic plugin;
    private FileConfiguration config;
    private File configFile;

    // Config things
    private boolean SETTINGS_DEBUG;
    private boolean SETTINGS_UPDATE_CHECKER_ENABLED;

    public Config(Skonic plugin) {
        this.plugin = plugin;
        loadConfigFile();
    }

    public void reload() {
        loadConfigFile();
    }

    private void loadConfigFile() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), "config.yml");
        }
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        matchConfig();
        loadConfigs();
    }

    @SuppressWarnings("ConstantConditions")
    private void matchConfig() {
        try {
            boolean hasUpdated = false;
            InputStream stream = plugin.getResource(configFile.getName());
            assert stream != null;
            InputStreamReader is = new InputStreamReader(stream);
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(is);
            for (String key : defConfig.getConfigurationSection("").getKeys(true)) {
                if (!config.contains(key)) {
                    config.set(key, defConfig.get(key));
                    hasUpdated = true;
                }
            }
            for (String key : config.getConfigurationSection("").getKeys(true)) {
                if (!defConfig.contains(key)) {
                    config.set(key, null);
                    hasUpdated = true;
                }
            }
            if (hasUpdated)
                config.save(configFile);
        } catch (Exception e) {
            Skonic.logger().log(Level.SEVERE, "Error matching config", e);
        }
    }

    private boolean getSetting(String setting) {
        return this.config.getBoolean("settings." + setting);
    }

    private void loadConfigs() {
        this.SETTINGS_DEBUG = getSetting("debug");
        this.SETTINGS_UPDATE_CHECKER_ENABLED = getSetting("update-checker.enabled");
    }

    public boolean isDebugEnabled() {
        return SETTINGS_DEBUG;
    }

    public boolean isUpdateCheckerEnabled() {
        return SETTINGS_UPDATE_CHECKER_ENABLED;
    }
}
