package au.nagasonic.skonic.elements.util;

import au.nagasonic.skonic.Skonic;
import au.nagasonic.skonic.elements.skins.Skin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utility class responsible for serializing and deserializing the Skin cache
 * (Map of URL to {@link Skin} objects) to/from a YAML configuration file.
 *
 * @author  Faiizer
 *
 * @since   1.2.5
 */
public class SkinCacheSerializer {

    /**
     * Reads and deserializes the skin cache data from a YAML file.
     *
     * @param file          the file.
     *
     * @return              a {@code Map} containing the deserialized skins, keyed by their URL.
     *
     * @throws IOException  if an input/output error occurs during file loading.
     *
     * @since               1.2.5
     */
    public static Map<String, Skin> read(File file) throws IOException {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        ConcurrentHashMap<String, Skin> memoryCache = new ConcurrentHashMap<>();

        for (String urlKey : config.getKeys(false)) {
            String valuePath = urlKey + ".value";
            String signaturePath = urlKey + ".signature";

            if (config.isString(valuePath) && config.isString(signaturePath)) {
                String value = config.getString(valuePath);
                String signature = config.getString(signaturePath);

                if (value != null && signature != null) {
                    Skin skin = new Skin(value, signature);
                    memoryCache.put(urlKey, skin);
                }
            } else {
                Skonic.logger().warn("Skipping malformed skin cache entry for URL: " + urlKey);
            }
        }

        return memoryCache;
    }

    /**
     * Serializes the in-memory skin cache data into a YAML configuration file on disk.
     *
     * @param file          the file.
     *
     * @param data          the {@code Map} of URL to Skin objects to be serialized.
     *
     * @throws IOException  if an input/output error occurs during file saving.
     *
     * @since               1.2.5
     */
    public static void write(File file, Map<String, Skin> data) throws IOException {
        YamlConfiguration config = new YamlConfiguration();

        for (Map.Entry<String, Skin> entry : data.entrySet()) {
            String urlKey = entry.getKey();
            Skin skin = entry.getValue();

            config.set(urlKey + ".value", skin.getTexture());
            config.set(urlKey + ".signature", skin.getSignature());
        }

        try {
            config.save(file);
        } catch (IOException e) {
            Skonic.logger().severe("Could not save skin cache to file: " + file.getName(), e);
        }
    }

}
