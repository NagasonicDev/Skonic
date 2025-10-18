package au.nagasonic.skonic.elements.util;

import au.nagasonic.skonic.Skonic;
import au.nagasonic.skonic.elements.skins.Skin;
import au.nagasonic.skonic.exceptions.SkinGenerationException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        URL url = null;
        try {
            url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            JsonObject object = new Gson().fromJson(reader, JsonObject.class);
            String id = object.get("id").getAsString();
            Skin skin = Skin.fromURL("https://sessionserver.mojang.com/session/minecraft/profile/" + id + "?unsigned=false");
            return headFromBase64(skin.getTexture());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ItemStack headFromUuid(String id) {
        ItemStack item = getPlayerSkullItem();

        return headWithUuid(item, id);
    }

    public static ItemStack headWithUuid(ItemStack item, String id) {
        notNull(item, "item");
        notNull(id, "id");

        Skin skin = Skin.fromURL("https://sessionserver.mojang.com/session/minecraft/profile/" + id + "?unsigned=false");
        return headFromBase64(skin.getTexture());
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
//        Skonic.info(profile.getTextures().getSkin().toString()); // ERROR : SPAM
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
        JsonObject jsonObject = JsonParser.parseString(decoded).getAsJsonObject();
        // Extract the "textures" JSONObject
        JsonObject textures = jsonObject.get("textures").getAsJsonObject();

        // Extract the "SKIN" JSONObject and then the "url"
        String url = textures.getAsJsonObject("SKIN").get("url").getAsString();
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

    @Deprecated (forRemoval = true)
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

    /**
     * Attempts to retrieve the texture value of a player head via Mineskin.
     *
     * @param headItem  the ItemStack representing the player head.
     *
     * @return          the texture value string, or {@code null}.
     *
     * @since           1.2.5
     */
    @SuppressWarnings("deprecation")
    public static String getHeadSkinValue(ItemStack headItem) {
        if (headItem == null || !(headItem.getItemMeta() instanceof SkullMeta skullMeta)) {
            throw new IllegalArgumentException("ItemStack must be a player head with SkullMeta.");
        }

        PlayerProfile playerProfile = skullMeta.getOwnerProfile();

        if (playerProfile == null || playerProfile.getTextures().getSkin() == null) {
            return null;
        }

        String url = playerProfile.getTextures().getSkin().toString();

        String value;
        try {
            Skin skin = SkinUtils.getSkinFromMineskinUrl(url, false);
            value = skin.getTexture();
        } catch (SkinGenerationException e) {
            Skonic.logger().severe("Failed to retrieve skin value from external API for head URL: " + url, e);
            return null;
        }

        return value;
    }

    public static URL getURL(ItemStack head){
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        PlayerProfile profile = meta.getOwnerProfile();
        URL url = profile.getTextures().getSkin();
        return url;
    }
}
