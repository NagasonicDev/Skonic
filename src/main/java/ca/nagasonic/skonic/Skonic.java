package ca.nagasonic.skonic;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.CustomChart;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Skonic extends JavaPlugin {
    private static Skonic instance;
    private static Logger logger;
    private static SkriptAddon addon;

    @Override
    public void onEnable() {
        instance = this;
        logger = getLogger();
        addon = Skript.registerAddon(this);
        try {
            addon.loadClasses("ca.nagasonic.skonic");
        } catch (IOException e) {
            e.printStackTrace();
        }
        info("Skonic has been enabled");
        Metrics metrics = new Metrics(this, 20479);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Skonic getInstance(){ return instance; }

    public static void info(String message){
        logger.info(message);
    }

    public static void log(Level level, String message){
        logger.log(level, message);
    }
}
