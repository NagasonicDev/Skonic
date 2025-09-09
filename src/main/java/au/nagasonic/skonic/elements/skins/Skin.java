package au.nagasonic.skonic.elements.skins;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class Skin {

    private String value;
    private String signature;
    private UUID uuid;

    public Skin(String value, String signature, UUID uuid) {
        this.value = value == null ? "" : value;
        this.signature = signature == null ? "" : signature;
        this.uuid = uuid == null ? UUID.fromString("") : uuid;
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("value", value);
        jsonObject.addProperty("signature", signature);
        jsonObject.addProperty("uuid", uuid.toString());
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

    public static Skin fromJson(JsonObject object, UUID id){
        return new Skin(object.get("value").getAsString(), object.get("signature").getAsString(), id);
    }

    public static Skin fromURL(String urlString){
        try {
            URL url = new URL(urlString);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            UUID id = UUID.fromString(new JsonParser().parse(reader).getAsJsonObject().get("id").getAsString());
            JsonObject textures = new JsonParser().parse(reader).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            return fromJson(textures, id);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getTexture() {
        return this.value;
    }

    public String getSignature() {
        return this.signature;
    }

    public UUID getUUID() {
        return this.uuid;
    }
}
