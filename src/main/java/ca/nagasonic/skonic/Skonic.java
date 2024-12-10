package ca.nagasonic.skonic;

import ca.nagasonic.skonic.elements.util.SkinUtils;
import ca.nagasonic.skonic.elements.util.UpdateChecker;
import ca.nagasonic.skonic.elements.util.Util;
import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.util.Version;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.A;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ca.nagasonic.skonic.elements.util.SkinUtils.getEffectiveProfile;
import static org.bukkit.Bukkit.getPluginManager;

public final class Skonic extends JavaPlugin implements Listener {
    static final int[] EARLIEST_VERSION = new int[]{1, 19};
    private static Skonic instance;
    private static Logger logger;
    public static String path;
    private AddonLoader addonLoader = null;

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        instance = this;
        logger = getLogger();
        path = this.getDataFolder().getPath();
        PluginManager pm = Bukkit.getPluginManager();

        this.addonLoader = new AddonLoader(this);
        if (!addonLoader.canLoadPlugin()){
            pm.disablePlugin(this);
            return;
        }

        String version = getDescription().getVersion();
        if (version.contains("dev")) {
            Util.log("&eThis is a DEV build, things may not work as expected, please report any bugs on GitHub");
            Util.log("&ehttps://github.com/Nagasonic/Skonic/issues");
        }
        new UpdateChecker(this);
        Util.log("&aSuccessfully enabled v%s&7 in &b%.2f seconds", version, (float) (System.currentTimeMillis() - start) / 1000);
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

    public static String getPath(){ return path; }
}
