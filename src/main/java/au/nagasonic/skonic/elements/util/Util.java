package au.nagasonic.skonic.elements.util;

import au.nagasonic.skonic.Skonic;
import ch.njol.skript.Skript;
import ch.njol.skript.log.ErrorQuality;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    private static final String PREFIX = "&7[&9Skonic&7] ";
    private static final String PREFIX_ERROR = "&7[&9Skonic &cERROR&7] ";
    private static final Pattern HEX_PATTERN = Pattern.compile("<#([A-Fa-f\\d]){6}>");
    private static final boolean SKRIPT_IS_THERE = Bukkit.getPluginManager().getPlugin("Skript") != null;

    @SuppressWarnings("deprecation") // Paper deprecation
    public static String getColString(String string) {
        Matcher matcher = HEX_PATTERN.matcher(string);
        if (SKRIPT_IS_THERE) {
            while (matcher.find()) {
                final ChatColor hexColor = ChatColor.of(matcher.group().substring(1, matcher.group().length() - 1));
                final String before = string.substring(0, matcher.start());
                final String after = string.substring(matcher.end());
                string = before + hexColor + after;
                matcher = HEX_PATTERN.matcher(string);
            }
        } else {
            string = HEX_PATTERN.matcher(string).replaceAll("");
        }
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void sendColMsg(CommandSender receiver, String format, Object... objects) {
        receiver.sendMessage(getColString(String.format(format, objects)));
    }

    public static void log(String format, Object... objects) {
        String log = String.format(format, objects);
        Bukkit.getConsoleSender().sendMessage(getColString(PREFIX + log));
    }

    public static void skriptError(String format, Object... objects) {
        String error = String.format(format, objects);
        Skript.error(getColString(PREFIX_ERROR + error), ErrorQuality.SEMANTIC_ERROR);
    }

    public static void debug(String format, Object... objects) {
        String debug = String.format(format, objects);
        Bukkit.getConsoleSender().sendMessage(getColString(PREFIX_ERROR + debug));
    }

    private static final List<String> DEBUGS = new ArrayList<>();

    public static void logLoading(String format, Object... objects) {
        String form = String.format(format, objects);
        DEBUGS.add(form);
        log(form);
    }

    public static List<String> getDebugs() {
        return DEBUGS;
    }

    /**
     * Gets a Minecraft NamespacedKey from string
     * <p>If no colon (':') is not include it will prepend it to the start</p>
     *
     * @param key   Key for new Minecraft NamespacedKey
     * @param error Whether to send a skript/console error if one occurs
     * @return new Minecraft NamespacedKey
     */
    @Nullable
    public static NamespacedKey getMCNamespacedKey(@NotNull String key, boolean error) {
        if (!key.contains(":")) key = "minecraft:" + key;
        return getNamespacedKey(key, error);
    }

    /**
     * Get a NamespacedKey from string
     * <p>If no namespace is provided, it will default to namespace in SkBee config (default = "skbee")</p>
     *
     * @param key   Key for new NamespacedKey, ex: "plugin:key" or "minecraft:something"
     * @param error Whether to send a skript/console error if one occurs
     * @return new NamespacedKey
     */
    @Nullable
    public static NamespacedKey getNamespacedKey(@NotNull String key, boolean error) {
        if (key.length() > 255) {
            if (error)
                skriptError("An invalid key was provided, key must be less than 256 characters: %s", key);
            return null;
        }
        key = key.toLowerCase();
        if (key.contains(" ")) {
            key = key.replace(" ", "_");
        }

        NamespacedKey namespacedKey = null;
        if (key.contains(":")) {
            namespacedKey = NamespacedKey.fromString(key);
        } else { // Just a safety check, settings_namespace can't be null but in case this is defaulted to.
            try {
                namespacedKey = new NamespacedKey(Skonic.getInstance(), key);
            } catch (Exception exception) {
                if (error) {
                    skriptError(exception.getMessage());
                }
            }
        }
        if (namespacedKey == null && error)
            skriptError("An invalid key was provided, that didn't follow [a-z0-9/._-:]. key: %s", key);
        return namespacedKey;
    }

    /**
     * Convert a UUID to an int array
     * <p>Used for Minecraft 1.16+</p>
     *
     * @param uuid UUID to convert
     * @return int array from UUID
     */
    public static int[] uuidToIntArray(UUID uuid) {
        long most = uuid.getMostSignificantBits();
        long least = uuid.getLeastSignificantBits();
        return new int[]{(int) (most >> 32), (int) most, (int) (least >> 32), (int) least};
    }

    public static Date getDate(){
        Date date = new Date();
        return date;
    }

    public static String fromDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        return format.format(date);
    }

    public static boolean isURL(String url){
        boolean c = false;
        try {
            URL Url = new URL(url);
            c = true;
        } catch (MalformedURLException ex) {
            Skript.error("Specified 'URL' is not a valid URL");
            throw new RuntimeException(ex);
        }
        return c;
    }
}
