package au.nagasonic.skonic.elements.util;

import au.nagasonic.skonic.Skonic;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import org.json.JSONObject;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class HeadUtils {
    @Deprecated
    public static ItemStack headFromName(String name) {
        ItemStack item = getPlayerSkullItem();

        return headWithName(item, name);
    }

    @Deprecated
    public static ItemStack headWithName(ItemStack item, String name) {
        notNull(item, "item");
        notNull(name, "name");
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(name));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack headFromUuid(UUID id) {
        ItemStack item = getPlayerSkullItem();

        return headWithUuid(item, id);
    }

    public static ItemStack headWithUuid(ItemStack item, UUID id) {
        notNull(item, "item");
        notNull(id, "id");

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(id));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack headFromUrl(String url) {
        ItemStack item = getPlayerSkullItem();

        return headWithUrl(item, url);
    }

    private static PlayerProfile getProfile(String url) {
        notNull(url, "url");
        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID()); // Get a new player profile
        PlayerTextures textures = profile.getTextures();
        URL urlObject;
        try {
            urlObject = new URL(url);
        } catch (MalformedURLException exception) {
            throw new RuntimeException("Invalid URL", exception);
        }
        textures.setSkin(urlObject); // Set the skin of the player profile to the URL
        profile.setTextures(textures); // Set the textures back to the profile
        return profile;
    }
    public static ItemStack headWithUrl(ItemStack item, String url) {
        PlayerProfile profile = getProfile(url);
        Skonic.info(profile.getTextures().getSkin().toString());
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwnerProfile(profile); // Set the owning player of the head to the player profile
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack headFromBase64(String base64) {
        ItemStack item = getPlayerSkullItem();
        try {
            return headWithBase64(item, base64);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static URL getUrlFromBase64(String base64) throws MalformedURLException {
        String decoded = new String(Base64.getDecoder().decode(base64));
        JSONObject jsonObject = new JSONObject(decoded);

        // Extract the "textures" JSONObject
        JSONObject textures = jsonObject.getJSONObject("textures");

        // Extract the "SKIN" JSONObject and then the "url"
        String url = textures.getJSONObject("SKIN").getString("url");
        return new URL(url);
    }

    public static ItemStack headWithBase64(ItemStack item, String base64) throws MalformedURLException {
        notNull(item, "item");
        notNull(base64, "base64");

        return headWithUrl(item, getUrlFromBase64(base64).toString());
    }

    private static boolean newerApi() {
        try {
            Material.valueOf("PLAYER_HEAD");
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static ItemStack getPlayerSkullItem() {
        if (newerApi()) {
            return new ItemStack(Material.valueOf("PLAYER_HEAD"));
        } else {
            return new ItemStack(Material.valueOf("SKULL_ITEM"), 1, (byte) 3);
        }
    }

    private static void notNull(Object o, String name) {
        if (o == null) {
            throw new NullPointerException(name + " should not be null!");
        }
    }

    public static String getValue(ItemStack head){
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        PlayerProfile profile = meta.getOwnerProfile();
        String url = profile.getTextures().getSkin().toString();
        JsonObject data = null;
        try {
            data = SkinUtils.generateFromURL(url, false);
        } catch (ExecutionException ex) {
            throw new RuntimeException(ex);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        JsonObject texture = data.get("texture").getAsJsonObject();
        String value = texture.get("value").getAsString();
        return value;
    }

    public static URL getURL(ItemStack head){
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        PlayerProfile profile = meta.getOwnerProfile();
        URL url = profile.getTextures().getSkin();
        return url;
    }
}
