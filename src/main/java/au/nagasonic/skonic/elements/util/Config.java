package au.nagasonic.skonic.elements.util;

import au.nagasonic.skonic.Skonic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Config for Skonic plugin.
 *
 * @author  Nagasonic
 * @author  Faiizer
 *
 * @since   1.0.0
 */
public class Config {

    /** The main plugin instance. */
    private final Skonic plugin;
    /** The actual configuration data loaded from the {@code config.yml} file. */
    private FileConfiguration configData;
    /** The file object pointing to the {@code config.yml} on data folder. */
    private File configFile;

    // Config Values
    /** Boolean indicating whether the plugin's internal debug logging is enabled. */
    private boolean SETTINGS_DEBUG;
    /** Boolean indicating whether the automatic update checker feature is enabled. */
    private boolean SETTINGS_UPDATE_CHECKER_ENABLED;
    /** The maximum pathfinding range for NPCs. */
    private int SETTINGS_CITIZENS_PATHFINDING_MAXIMUM_RANGE;

    public Config(Skonic plugin) {
        this.plugin = plugin;
        reload();
    }

    // For Loading Configuration
    /**
     * Fully reloads the configuration from the plugin's data folder.
     * <p>The process includes:</p>
     * <ol>
     * <li>Loading the {@code config.yml} file from data folder via {@link #loadConfigFile()}</li>
     * <li>Synchronizing the loaded configuration with the default values via {@link #matchConfig()}.</li>
     * <li>Caching all necessary settings via {@link #loadConfigs()}.</li>
     * </ol>
     *
     * @since 1.2.5
     */
    public void reload() {
        loadConfigFile();
        matchConfig();
        loadConfigs();
    }

    /**
     * Ensures the configuration file exists on data folder and loads the data.
     * <p>
     * If the configuration file does not exist, it save the default configuration to the data folder.
     *
     * @since 1.0.0
     */
    private void loadConfigFile() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), "config.yml");
        }
        if (!configFile.exists()) {
            plugin.saveDefaultConfig();
        }
        configData = YamlConfiguration.loadConfiguration(configFile);
    }


    /**
     * Synchronizes the plugin's active configuration file with the default configuration file.
     *
     * @since 1.2.5
     */
    private void matchConfig() {
        boolean hasUpdated = false;

        InputStream inputStream = plugin.getResource(configFile.getName());
        if (inputStream == null) {
            Skonic.logger().severe("Could not find default config.yml in the plugin JAR.");
            return;
        }

        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {

            YamlConfiguration defaultConfigData = YamlConfiguration.loadConfiguration(inputStreamReader);

            for (String key : defaultConfigData.getKeys(true)) {
                if (!configData.contains(key)) {
                    configData.set(key, defaultConfigData.get(key));
                    hasUpdated = true;
                }
            }
            for (String key : configData.getKeys(true)) {
                if (!defaultConfigData.contains(key)) {
                    configData.set(key, null);
                    hasUpdated = true;
                }
            }

            if (hasUpdated)
                configData.save(configFile);

        } catch (IOException e) {
            Skonic.logger().severe("Failed to sync config.yml due to IO error.", e);
        } catch (Exception e) {
            Skonic.logger().severe("Failed to sync config.yml with defaults.", e);
        }
    }

    /**
     * Retrieves a boolean value from the "settings" section of the configuration.
     * <p>
     * The method automatically prepends "{@code settings.}" to the provided key.
     *
     * @param setting   the specific key within the "settings" section (e.g., "debug").
     *
     * @return          the boolean value associated with the full path (e.g., "settings.debug").
     *
     * @since           1.0.0
     */
    private boolean getSetting(String setting) {
        return configData.getBoolean("settings." + setting);
    }

    /**
     * Loads and caches all necessary configuration values from the active {@code FileConfiguration}
     * (e.g., {@code SETTINGS_DEBUG}).
     *
     * @since 1.0.0
     */
    private void loadConfigs() {
        // Settings section
        SETTINGS_DEBUG = getSetting("debug");
        SETTINGS_UPDATE_CHECKER_ENABLED = getSetting("update-checker.enabled");
    }


    // For Getting Config Values
    /**
     * Checks if the debug mode is enabled.
     *
     * @return  {@code true} if debug mode is enabled, {@code false} otherwise.
     *
     * @since   1.2.5
     */
    public boolean isDebugEnabled() {
        return SETTINGS_DEBUG;
    }

    /**
     * Checks if the automatic update checker functionality is enabled.
     *
     * @return  {@code true} if the automatic update checker functionality is enabled, {@code false} otherwise.
     *
     * @since   1.2.5
     */
    public boolean isUpdateCheckerEnabled() {
        return SETTINGS_UPDATE_CHECKER_ENABLED;
    }

    /**
     * Returns the maximum pathfinding range for NPCs.
     *
     * @return  the maximum pathfinding range for NPCs.
     *
     * @since   1.2.5
     */
    public int getCitizensPathfindingMaximumRange() {
        return SETTINGS_CITIZENS_PATHFINDING_MAXIMUM_RANGE;
    }

}
