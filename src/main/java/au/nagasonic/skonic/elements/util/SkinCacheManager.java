package au.nagasonic.skonic.elements.util;

import au.nagasonic.skonic.Skonic;
import au.nagasonic.skonic.elements.skins.Skin;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages the persistence and in-memory storage for cached skins.
 *
 * @author Faiizer
 *
 * @since 1.2.5
 */
public class SkinCacheManager {

    private final File cacheFile;
    private ConcurrentHashMap<String, Skin> memoryCache;

    /**
     * Constructs the {@link SkinCacheManager}, initializes the cache file location, and attempts to load existing data.
     *
     * @param plugin        the main plugin instance.
     * @param dataFolder    the plugin's data directory.
     *
     * @since               1.2.5
     */
    public SkinCacheManager(Skonic plugin, File dataFolder) {
        this.cacheFile = new File(dataFolder, "skin_cache.yml");
        if (!this.cacheFile.exists()) {
            plugin.saveResource(dataFolder + "skin_cache.yml", true);
        }
        this.load();
    }

    /**
     * Loads the skin cache data from the {@code skin_cache.yml} file into the in-memory map.
     * <p>
     * If the file is not found or reading fails, the memory cache is initialized as empty.
     *
     * @since 1.2.5
     */
    public void load() {
        if (!cacheFile.exists()) {
            this.memoryCache = new ConcurrentHashMap<>();
            return;
        }

        try {
            this.memoryCache = new ConcurrentHashMap<>(SkinCacheSerializer.read(cacheFile));
            Skonic.logger().info("Successfully loaded %d skins from cache.", memoryCache.size());
        } catch (IOException e) {
            this.memoryCache = new ConcurrentHashMap<>();
            Skonic.logger().severe("Failed to load skin cache from disk. Starting with an empty cache.", e);
        }
    }

    /**
     * Saves the current state of the in-memory cache to the {@code skin_cache.yml} file.
     *
     * @since 1.2.5
     */
    public void save() {
        try {
            SkinCacheSerializer.write(cacheFile, memoryCache);
        } catch (IOException e) {
            Skonic.logger().severe("Failed to save skin cache.", e);
        }
    }

    /**
     * Retrieves a {@link Skin} from the in-memory cache using its URL.
     *
     * @param url   the URL of the skin texture to retrieve.
     *
     * @return      the cached {@link Skin} object, or {@code null} if not found.
     *
     * @since       1.2.5
     */
    public Skin get(String url) {
        return memoryCache.get(url);
    }

    /**
     * Adds or updates a {@link Skin} in the in-memory cache.
     *
     * @param url   the URL of the skin.
     * @param skin  the {@link Skin} object to store.
     *
     * @since       1.2.5
     */
    public void set(String url, Skin skin) {
        if (url != null && skin != null) {
            memoryCache.put(url, skin);
        }
    }

}
