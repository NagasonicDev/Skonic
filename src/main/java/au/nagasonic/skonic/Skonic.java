package au.nagasonic.skonic;

import au.nagasonic.skonic.elements.util.Config;
import au.nagasonic.skonic.elements.util.SkonicLogger;
import au.nagasonic.skonic.elements.util.UpdateChecker;
import au.nagasonic.skonic.elements.util.Util;
import ch.njol.skript.Skript;
import ch.njol.skript.util.Version;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main class for the Skonic Skript Addon.
 *
 * @author Nagasonic
 * @author Faiizer
 *
 * @since 1.0.0
 */
public final class Skonic extends JavaPlugin {
    // -- Statics
    /** The instance of the plugin. */
    private static Skonic instance;
    /** The primary logger used by the plugin for console output. */
    private static Logger logger;
    private static SkonicLogger skonicLogger;
    /** The absolute path to the plugin's data folder. */
    private static String dataDirectory;
    /** The Bukkit {@link PluginManager} instance. */
    private PluginManager pm;

    // -- Config
    /** The plugin's {@link Config}. */
    private Config config;

    // -- Dependencies
    // --- Earliest Minecraft Version
    /** The earliest required Minecraft version supported by this addon (e.g., [1, 19, 4]). */
    static final int[] EARLIEST_VERSION = new int[]{1, 19, 4};
    // --- Citizens
    /** The official name of the Citizens plugin used for dependency checking. */
    private static final String CITIZENS_PLUGIN_NAME = "Citizens";
    /** Flag indicating whether the Citizens plugin is installed and enabled. */
    private static boolean citizensEnabled = false;

    // -- Addon Information
    /** The Skript {@link AddonLoader} responsible for registering all elements. */
    private AddonLoader addonLoader;
    /** The current version {@link String} of the plugin. */
    private String version;


    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();

        // Plugin Initialisation
        initializeStatics();
        setupConfiguration();
        checkDependenciesAndAlert();

        // Addon Loading
        setupAddonLoader();
        if (!addonLoader.canLoadPlugin()) {
            pm.disablePlugin(this);
            return;
        }

        // Other Services
        checkVersionAndAlert();
        new UpdateChecker(this);
        setupMetrics();

        // Final Log
        Util.log(
                "&aSuccessfully enabled v%s&7 in &b%.2f seconds",
                version,
                (float) (System.currentTimeMillis() - start) / 1000
        );
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onDisable() {
        // Plugin shutdown logic
        Util.logLoading("&eDisabled Skonic v%s", getDescription().getVersion());
    }


    // Private Setup Methods
    /**
     * Initializes all static fields ({@link #instance}, {@link #logger}, {@link #dataDirectory}, {@link #pm}).
     *
     * @since 1.2.5
     */
    private void initializeStatics() {
        instance = this;
        logger = getLogger();
        skonicLogger = new SkonicLogger(this);
        dataDirectory = this.getDataFolder().getPath();
        pm = Bukkit.getPluginManager();
    }

    /**
     * Initializes the plugin's {@link Config} file.
     *
     * @since 1.2.5
     */
    private void setupConfiguration() {
        config = new Config(this);
    }

    /**
     * Checks for dependencies (Citizens), sets the associated boolean flag, and sends a log message to the console.
     *
     * @since 1.2.5
     */
    private void checkDependenciesAndAlert() {
        Plugin citizensPlugin = pm.getPlugin(CITIZENS_PLUGIN_NAME);

        if (citizensPlugin != null && citizensPlugin.isEnabled()) {
            citizensEnabled = true;
            Util.log("&aHooked into Citizens! Citizen-related elements are active.");
        } else {
            citizensEnabled = false;
            Util.log("&cCitizens not found. Citizen-related elements will not be loaded.");
        }
    }

    /**
     * Initializes the Skript {@link AddonLoader}.
     *
     * @since 1.2.5
     */
    private void setupAddonLoader() {
        addonLoader = new AddonLoader(this);
    }

    /**
     * Checks if the current plugin version is a beta build and alerts the console if it is.
     *
     * @since 1.2.5
     */
    @SuppressWarnings("deprecation")
    private void checkVersionAndAlert() {
        version = getDescription().getVersion();
        if (version.contains("b")) {
            Util.log("&eThis is a Beta build, things may not work as expected, please report any bugs on GitHub");
            Util.log("&ehttps://github.com/NagasonicDev/Skonic/issues");
        }
    }

    /**
     * Initializes metrics collection.
     *
     * @since 1.2.5
     */
    private void setupMetrics() {
        Metrics metrics = new Metrics(this, 20479);
        addSkriptVersionChart(metrics);
    }

    /**
     * Adds the Skript version chart to the metrics.
     *
     * @param metrics   the {@link Metrics} instance to add the chart to.
     *
     * @since           1.2.5
     */
    private void addSkriptVersionChart(@NotNull Metrics metrics) {
        metrics.addCustomChart(new Metrics.DrilldownPie("skript_version", () -> {
            Map<String, Map<String, Integer>> map = new HashMap<>();

            Version skriptVersion = Skript.getVersion();
            Map<String, Integer> entry = new HashMap<>();
            entry.put(skriptVersion.toString(), 1);

            map.put(skriptVersion.getMajor()+"."+skriptVersion.getMinor()+"."+skriptVersion.getRevision(), entry);

            return map;
        }));
    }


    // Getters
    /**
     * Returns the instance of the {@link Skonic} plugin.
     *
     * @return  the plugin instance.
     *
     * @since   1.2.5
     */
    public static Skonic getInstance() {
        return instance;
    }

    /**
     * Returns the absolute path to the plugin's data directory.
     *
     * @return  the absolute path to the plugin's data directory as a {@link String}.
     *
     * @since   1.0.0
     */
    public static String getDataDirectory(){
        return dataDirectory;
    }

    /**
     * Returns the plugin's {@link Config} object.
     *
     * @return  the plugin's {@link Config} object.
     *
     * @since   1.2.5
     */
    public Config getPluginConfig() {
        return this.config;
    }

    /**
     * Returns the Skript {@link AddonLoader} instance responsible for registering elements.
     *
     * @return  the {@link AddonLoader} used by this plugin.
     *
     * @since   1.2.5
     */
    @SuppressWarnings("unused")
    public AddonLoader getAddonLoader() {
        return addonLoader;
    }

    /**
     * Returns the current version {@link String} of the plugin.
     *
     * @return  the plugin version {@link String}.
     *
     * @since   1.2.5
     */
    @SuppressWarnings("unused")
    public String getVersion() {
        return version;
    }


    // Booleans
    /**
     * Check if Citizens is installed and enabled.
     *
     * @return {@code true} if Citizens is active, {@code false} otherwise.
     *
     * @since  1.2.5
     */
    @SuppressWarnings("unused")
    public boolean isCitizensEnabled() {
        return citizensEnabled;
    }


    // Utils Methods
    /**
     * Logs an informational message to the console using the plugin's logger.
     * <p>
     * This is a shorthand for {@code getLogger().info(message)}.
     * Get the logger with {@link #logger()}
     *
     * @deprecated As of release 1.2.5, replaced by {@link SkonicLogger#info(String)}.
     * Get the logger with {@link #logger()}
     *
     * @since      1.0.0
     */
    @Deprecated(forRemoval = true)
    public static void info(String message){
        logger.info(message);
    }

    /**
     * Logs a message at a specified logging level (e.g., {@link Level#WARNING}, {@link Level#SEVERE}).
     * 
     * @deprecated As of release 1.2.5, replaced by {@link SkonicLogger#log(Level, String)}.
     * Get the logger with {@link #logger()}
     *
     * @param level     the severity level of the log message
     * @param message   the message to log
     *
     * @since           1.0.0
     */
    @Deprecated(forRemoval = true)
    public static void log(Level level, String message) {
        logger.log(level, message);
    }

    /**
     * Logs a message with an exception at a specified logging level (e.g., {@link Level#WARNING}, {@link Level#SEVERE}).
     * 
     * @deprecated As of release 1.2.5, replaced by {@link SkonicLogger#log(Level, String, Throwable)}.
     * Get the logger with {@link #logger()}
     *
     * @param level     the severity level of the log message
     * @param message   the message to log
     * @param throwable the exception/error to log with its stack trace
     *
     * @since           1.2.5
     */
    @Deprecated(forRemoval = true)
    public static void log(Level level, String message, Throwable throwable) {
        logger.log(level, message, throwable);
    }

    /**
     * Returns the plugin's logger instance.
     *
     * @return  the {@link SkonicLogger} instance
     *
     * @since   1.2.5
     */
    public static SkonicLogger logger() {
        return skonicLogger;
    }

}