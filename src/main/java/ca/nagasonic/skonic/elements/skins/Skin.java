package ca.nagasonic.skonic.elements.skins;

import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class Skin {
    public static final Skin EMPTY = new Skin("", "");
    public static final Skin STEVE = new Skin(
            "eyJ0aW1lc3RhbXAiOjE0NzQyMTc3NjkwMDAsInByb2ZpbGVJZCI6ImIwZDRiMjhiYzFkNzQ4ODlhZjBlODY2MWNlZTk2YWFiIiwicHJvZmlsZU5hbWUiOiJJbnZlbnRpdmVHYW1lcyIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWE5MmI0NTY2ZjlhMjg2OTNlNGMyNGFiMTQxNzJjZDM0MjdiNzJiZGE4ZjM0ZDRhNjEwODM3YTQ3ZGEwZGUifX19",
            "pRQbSEnKkNmi0uW7r8H4xzoWS3E4tkWNbiwwRYgmvITr0xHWSKii69TcaYDoDBXGBwZ525Ex5z5lYe5Xg6zb7pyBPiTJj8J0QdKenQefVnm6Vi1SAR1uN131sRddgK2Gpb2z0ffsR9USDjJAPQtQwCqz0M7sHeXUJhuRxnbznpuZwGq+B34f1TqyVH8rcOSQW9zd+RY/MEUuIHxmSRZlfFIwYVtMCEmv4SbhjLNIooGp3z0CWqDhA7GlJcDFb64FlsJyxrAGnAsUwL2ocoikyIQceyj+TVyGIEuMIpdEifO6+NkCnV7v+zTmcutOfA7kHlj4d1e5ylwi3/3k4VKZhINyFRE8M8gnLgbVxNZ4mNtI3ZMWmtmBnl9dVujyo+5g+vceIj5Admq6TOE0hy7XoDVifLWyNwO/kSlXl34ZDq1MCVN9f1ryj4aN7BB8/Tb2M4sJf3YoGi0co0Hz/A4y14M5JriG21lngw/vi5Pg90GFz64ASssWDN9gwuf5xPLUHvADGo0Bue8KPZPyI0iuIi/3sZCQrMcdyVcur+facIObTQhMut71h8xFeU05yFkQUOKIQswaz2fpPb/cEypWoSCeQV8T0w0e3YKLi4RaWWvKS1MFJDHn7xMYaTk0OhALJoV5BxRD8vJeRi5jYf3DjEgt9+xB742HrbVRDlJuTp4="
    );
    public static final Skin ALEX = new Skin(
            "eyJ0aW1lc3RhbXAiOjE0NzQyMTc5MjMyMDAsInByb2ZpbGVJZCI6IjQzYTgzNzNkNjQyOTQ1MTBhOWFhYjMwZjViM2NlYmIzIiwicHJvZmlsZU5hbWUiOiJTa3VsbENsaWVudFNraW42Iiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsibWV0YWRhdGEiOnsibW9kZWwiOiJzbGltIn0sInVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTcxMDFmYTQ3NWI2NjA1NmQ2ZDcxZjJmZDI1Y2NkZmI1NjNhOTI1NGZhMjEzZTYyOGNkN2Q4MWQxZWVlOGUifX19",
            "vwUYP+IUwLgb5X4EEzZ9ThB8Pv2hq2LZWuSUr8i/FWcmCc9C4Q4FzxbeMPFKZihVdL7zL2cnmuTiXwxo7TewDjH0S4pIIm2fIvuYKSgoAjStVozL81vdWnhIuB5nNlgigjFLTuWMol36upujFcSDhvzF2ebZQprOEYWVjo3BjqccMBYsz4Uqy8/Kl2dzvPK7V8A167+Zt2l1LTkSBMMmvYoBHYC+L0eu5OCAe81WdtpXHAsKbVcz1VSGKNKhXE+eh2PsC5OHNQo7hc3H3gfVksrrJXjx3TmA5XFzA/7JAz3jmtYWhe3YGoJlZIBC9Y1WVK99c+yHl2x6TJUjwIS6IGqicNcSlhuqu51qnz6ICp7nklK7UPWA0lCME5Ufxu4Ao5aU5F4C9erelJt/t40vWq/2NiBaz7YUjOFZ2gvq1CKnnJnNjqbW0fuZsU4Gc1PtGiX36teq5BBNew7vmOWK0KmObUlXFoF2/tCsbYKP+GiJ8PG+XxGJ5OImIznmh/Y/ZI3tcRdcw8SL8UvgbdqaGjeScq+az8iHxLGSEHwu6ZGdkq3I3oJxUz7eCLkfrqhbRWOwQ8YHh8oz48iGLxiQoElQqzwEIbr6qaXrvCWam0ZcyLc2T9u+K9PcAnUFF781YIveI3kuUytQVm+kbWeb0+31xAzQfrOCFOP3O1WEIMU="
    );

    public final String value;
    public final String signature;
    public final UUID uuid;

    public Skin(String value, String signature) {
        this.value = value == null ? "" : value;
        this.signature = signature == null ? "" : signature;
        this.uuid = UUID.nameUUIDFromBytes(this.value.getBytes());
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("value", value);
        jsonObject.addProperty("signature", signature);
        return jsonObject;
    }

    @Override
    public String toString(){
        return toJson().toString();
    }

    @Override
    public boolean equals(Object other){
        if (other instanceof Skin){
            Skin otherSkin = (Skin) other;
            return value.equals(otherSkin.value) && signature.equals(otherSkin.signature);
        }else {
            return false;
        }
    }

    public static Skin fromJson(JsonObject object){
        return new Skin(object.get("value").getAsString(), object.get("signature").getAsString());
    }

    public static Skin fromURL(String urlString){
        try {
            URL url = new URL(urlString);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            JsonObject textures = new JsonParser().parse(reader).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String value = textures.get("value").getAsString();
            String signature = textures.get("signature").getAsString();
            return new Skin(value, signature);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Skin fromWrappedSignedProperty(WrappedSignedProperty wrappedSignedProperty) {
        return new Skin(wrappedSignedProperty.getValue(), wrappedSignedProperty.getSignature());
    }

    public static Skin fromGameProfile(WrappedGameProfile gameProfile) {
        WrappedSignedProperty[] wrappedSignedProperties = gameProfile.getProperties().get("textures").toArray(new WrappedSignedProperty[0]);
        if (wrappedSignedProperties.length == 0) {
            return Skin.EMPTY;
        } else {
            return fromWrappedSignedProperty(wrappedSignedProperties[0]);
        }
    }
    public Skin clear(){
        return Skin.EMPTY;
    }
}
