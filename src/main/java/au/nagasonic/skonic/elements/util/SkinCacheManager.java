package au.nagasonic.skonic.elements.util;

import au.nagasonic.skonic.Skonic;
import au.nagasonic.skonic.elements.skins.Skin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages the persistence and in-memory storage for cached skins.
 *
 * @author Faiizer
 *
 * @since 1.2.5
 */
public class SkinCacheManager {

    private final File UrlCacheFile;
    private final File FileCacheFile;
    private ConcurrentHashMap<String, Skin> UrlMemoryCache;
    private ConcurrentHashMap<String, Skin> FileMemoryCache;

    /**
     * Constructs the {@link SkinCacheManager}, initializes the cache file location, and attempts to load existing data.
     *
     * @param plugin        the main plugin instance.
     * @param dataFolder    the plugin's data directory.
     *
     * @since               1.2.5
     */
    public SkinCacheManager(Skonic plugin, File dataFolder) {
        this.UrlCacheFile = new File(dataFolder, "skin_cache_url.yml");
        this.FileCacheFile = new File(dataFolder, "skin_cache_file.yml");
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
        if (!UrlCacheFile.exists()) {
            this.UrlMemoryCache = new ConcurrentHashMap<>();
            return;
        }
        if (!FileCacheFile.exists()) {
            this.FileMemoryCache = new ConcurrentHashMap<>();
            return;
        }
        try {
            this.UrlMemoryCache = new ConcurrentHashMap<>(SkinCacheSerializer.readURL(UrlCacheFile));
            this.FileMemoryCache = new ConcurrentHashMap<>(SkinCacheSerializer.readFile(FileCacheFile));
            Skonic.logger().info("Successfully loaded %d skins from cache.", UrlMemoryCache.size() + FileMemoryCache.size());
        } catch (IOException e) {
            this.UrlMemoryCache = new ConcurrentHashMap<>();
            this.FileMemoryCache = new ConcurrentHashMap<>();
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
            if (UrlMemoryCache != null){
                SkinCacheSerializer.writeURL(UrlCacheFile, UrlMemoryCache);
            }
            if (FileMemoryCache != null){
                SkinCacheSerializer.writeFile(FileCacheFile, FileMemoryCache);
            }
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
    public Skin getURL(String url) {
        if (UrlMemoryCache != null){
            return UrlMemoryCache.get(url);
        }
        return null;
    }

    /**
     * Adds or updates a {@link Skin} in the in-memory cache.
     *
     * @param url   the URL of the skin.
     * @param skin  the {@link Skin} object to store.
     *
     * @since       1.2.5
     */
    public void setURL(String url, Skin skin) {
        if (url != null && skin != null) {
            UrlMemoryCache.put(url, skin);
        }
    }

    /**
     * Retrieves a {@link Skin} from the in-memory cache using its stored file name.
     *
     * @param file   the file name of the skin image to retrieve.
     *
     * @return      the cached {@link Skin} object, or {@code null} if not found.
     *
     * @since       1.2.5
     */
    public Skin getFile(String file) {
        if (FileMemoryCache != null){
            return FileMemoryCache.get(file);
        }
        return null;
    }

    /**
     * Adds or updates a {@link Skin} in the in-memory cache.
     *
     * @param file   the file name of the skin image.
     * @param skin  the {@link Skin} object to store.
     *
     * @since       1.2.5
     */
    public void setFile(String file, Skin skin) {
        if (file != null && skin != null) {
            FileMemoryCache.put(file, skin);
        }
    }

}
