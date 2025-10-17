package au.nagasonic.skonic.elements.skins;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * Represents a Minecraft skin, defined by its texture {@code value} and its {@code signature}.
 * <p>
 * This class provides utility methods for creating {@code Skin} objects from raw data, JSON structures, or URLs.
 *
 * @author Nagasonic
 * @author Faiizer
 *
 * @since 1.0.0
 */
public class Skin {
    // Example default values
//    public static final Skin EMPTY = new Skin("", "");
//    public static final Skin STEVE = new Skin(
//            "eyJ0aW1lc3RhbXAiOjE0NzQyMTc3NjkwMDAsInByb2ZpbGVJZCI6ImIwZDRiMjhiYzFkNzQ4ODlhZjBlODY2MWNlZTk2YWFiIiwicHJvZmlsZU5hbWUiOiJJbnZlbnRpdmVHYW1lcyIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWE5MmI0NTY2ZjlhMjg2OTNlNGMyNGFiMTQxNzJjZDM0MjdiNzJiZGE4ZjM0ZDRhNjEwODM3YTQ3ZGEwZGUifX19",
//            "pRQbSEnKkNmi0uW7r8H4xzoWS3E4tkWNbiwwRYgmvITr0xHWSKii69TcaYDoDBXGBwZ525Ex5z5lYe5Xg6zb7pyBPiTJj8J0QdKenQefVnm6Vi1SAR1uN131sRddgK2Gpb2z0ffsR9USDjJAPQtQwCqz0M7sHeXUJhuRxnbznpuZwGq+B34f1TqyVH8rcOSQW9zd+RY/MEUuIHxmSRZlfFIwYVtMCEmv4SbhjLNIooGp3z0CWqDhA7GlJcDFb64FlsJyxrAGnAsUwL2ocoikyIQceyj+TVyGIEuMIpdEifO6+NkCnV7v+zTmcutOfA7kHlj4d1e5ylwi3/3k4VKZhINyFRE8M8gnLgbVxNZ4mNtI3ZMWmtmBnl9dVujyo+5g+vceIj5Admq6TOE0hy7XoDVifLWyNwO/kSlXl34ZDq1MCVN9f1ryj4aN7BB8/Tb2M4sJf3YoGi0co0Hz/A4y14M5JriG21lngw/vi5Pg90GFz64ASssWDN9gwuf5xPLUHvADGo0Bue8KPZPyI0iuIi/3sZCQrMcdyVcur+facIObTQhMut71h8xFeU05yFkQUOKIQswaz2fpPb/cEypWoSCeQV8T0w0e3YKLi4RaWWvKS1MFJDHn7xMYaTk0OhALJoV5BxRD8vJeRi5jYf3DjEgt9+xB742HrbVRDlJuTp4="
//    );
//    public static final Skin ALEX = new Skin(
//            "eyJ0aW1lc3RhbXAiOjE0NzQyMTc5MjMyMDAsInByb2ZpbGVJZCI6IjQzYTgzNzNkNjQyOTQ1MTBhOWFhYjMwZjViM2NlYmIzIiwicHJvZmlsZU5hbWUiOiJTa3VsbENsaWVudFNraW42Iiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsibWV0YWRhdGEiOnsibW9kZWwiOiJzbGltIn0sInVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTcxMDFmYTQ3NWI2NjA1NmQ2ZDcxZjJmZDI1Y2NkZmI1NjNhOTI1NGZhMjEzZTYyOGNkN2Q4MWQxZWVlOGUifX19",
//            "vwUYP+IUwLgb5X4EEzZ9ThB8Pv2hq2LZWuSUr8i/FWcmCc9C4Q4FzxbeMPFKZihVdL7zL2cnmuTiXwxo7TewDjH0S4pIIm2fIvuYKSgoAjStVozL81vdWnhIuB5nNlgigjFLTuWMol36upujFcSDhvzF2ebZQprOEYWVjo3BjqccMBYsz4Uqy8/Kl2dzvPK7V8A167+Zt2l1LTkSBMMmvYoBHYC+L0eu5OCAe81WdtpXHAsKbVcz1VSGKNKhXE+eh2PsC5OHNQo7hc3H3gfVksrrJXjx3TmA5XFzA/7JAz3jmtYWhe3YGoJlZIBC9Y1WVK99c+yHl2x6TJUjwIS6IGqicNcSlhuqu51qnz6ICp7nklK7UPWA0lCME5Ufxu4Ao5aU5F4C9erelJt/t40vWq/2NiBaz7YUjOFZ2gvq1CKnnJnNjqbW0fuZsU4Gc1PtGiX36teq5BBNew7vmOWK0KmObUlXFoF2/tCsbYKP+GiJ8PG+XxGJ5OImIznmh/Y/ZI3tcRdcw8SL8UvgbdqaGjeScq+az8iHxLGSEHwu6ZGdkq3I3oJxUz7eCLkfrqhbRWOwQ8YHh8oz48iGLxiQoElQqzwEIbr6qaXrvCWam0ZcyLc2T9u+K9PcAnUFF781YIveI3kuUytQVm+kbWeb0+31xAzQfrOCFOP3O1WEIMU="
//    );

    // Class values
    /** The texture value of the skin. */
    private final String value;
    /** The signature of the skin. */
    private final String signature;
    /** A UUID from the skin's texture value. */
    private final UUID uuid;


    /**
     * Constructs a new {@link Skin} object with the specified value and signature.
     *
     * @param value     the texture value data.
     * @param signature the signature data.
     *
     * @since           1.0.0
     */
    public Skin(String value, String signature) {
        this.value = value == null ? "" : value;
        this.signature = signature == null ? "" : signature;
        this.uuid = UUID.nameUUIDFromBytes(this.value.getBytes());
    }

    /**
     * Converts this {@link Skin} object into a {@link JsonObject} containing the "value" and "signature" properties.
     *
     * @return  a {@link JsonObject} representation of this skin.
     *
     * @since   1.0.0
     */
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("value", value);
        jsonObject.addProperty("signature", signature);
        return jsonObject;
    }

    /**
     * Returns the JSON string representation of this {@link Skin}.
     *
     * @return  the JSON string.
     *
     * @since   1.0.0
     */
    @Override
    public String toString(){
        return toJson().toString();
    }

    /**
     * Compares this {@link Skin} object to another object for equality.
     *
     * @param other the object to compare against.
     *
     * @return      {@code true} if the other object is a {@link Skin} and has the same value and signature, {@code false} otherwise.
     *
     * @since       1.0.0
     */
    @Override
    public boolean equals(Object other){
        if (other instanceof Skin otherSkin){
            return value.equals(otherSkin.value) && signature.equals(otherSkin.signature);
        }else {
            return false;
        }
    }

    /**
     * Creates a {@link Skin} object from a {@link JsonObject}.
     *
     * @param object    the {@link JsonObject} containing the skin data.
     *
     * @return          a new {@link Skin} instance.
     *
     * @since           1.0.0
     */
    public static Skin fromJson(JsonObject object){
        return new Skin(object.get("value").getAsString(), object.get("signature").getAsString());
    }

    /**
     * Creates a {@link Skin} object by fetching and parsing texture data from a URL.
     * <p>
     *
     * @param urlString                 the URL pointing to the JSON texture data.
     *
     * @return                          a new {@link Skin} instance with the fetched data.
     *
     * @throws IllegalArgumentException if the provided URL string is malformed.
     * @throws RuntimeException         if an IO error occurs, or if the expected JSON structure is invalid.
     *
     * @since                           1.0.0
     */
    public static Skin fromURL(String urlString){
        URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("The provided URL string is malformed: " + urlString, e);
        }

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()))) {

            JsonElement jsonElement = JsonParser.parseReader(bufferedReader);
            JsonObject textures = jsonElement
                    .getAsJsonObject()
                    .getAsJsonArray("properties")
                    .get(0)
                    .getAsJsonObject();

            return fromJson(textures);

        } catch (IOException e) {
            throw new RuntimeException("Failed to read texture data from URL: " + urlString, e);
        } catch (IllegalStateException | IndexOutOfBoundsException e) {
            throw new RuntimeException("Failed to parse JSON structure from URL: " + urlString, e);
        }
    }


    // Getters
    /**
     * Gets the texture of the skin.
     *
     * @return  the texture value string of the skin.
     *
     * @since   1.0.0
     */
    public String getTexture() {
        return this.value;
    }

    /**
     * Gets the signature of the skin.
     *
     * @return  the texture signature string of the skin.
     *
     * @since   1.0.0
     */
    public String getSignature() {
        return this.signature;
    }

    /**
     * Gets the deterministic UUID generated from the texture value.
     *
     * @return  the UUID of the skin.
     *
     * @since   1.0.0
     */
    public UUID getUUID() {
        return this.uuid;
    }
}
