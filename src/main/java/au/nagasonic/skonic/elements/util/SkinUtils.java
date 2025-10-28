package au.nagasonic.skonic.elements.util;

import au.nagasonic.skonic.Skonic;
import au.nagasonic.skonic.elements.skins.Skin;
import au.nagasonic.skonic.exceptions.SkinGenerationException;
import com.google.common.io.CharStreams;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Utility class dedicated to handling the retrieval and generation of {@link Skin} from the Mineskin API.
 *
 * @author  Nagasonic
 * @author  Faiizer
 *
 * @since   1.2.5
 */
public class SkinUtils {

    /** The URL for the Mineskin API */
    private static final String MINESKIN_API_URL = "https://api.mineskin.org/generate/url";
    /** An {@link ExecutorService} dedicated to running the network tasks */
    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();


    /**
     * Returns a {@link Skin} object from a Mineskin URL, if in-memory storage doesn't contain url and skin.
     *
     * @param url                       the public URL of the skin image to be processed by Mineskin.
     * @param slim                      {@code true} to request the 'slim' model; {@code false} for the standard model.
     *
     * @return                          a new {@link Skin} object.
     *
     * @since                           1.2.5
     */
    public static Skin getSkinFromMineskinUrl(String url, boolean slim) throws SkinGenerationException {
        JsonObject mineskinData;

        try {

            Skin cachedSkin = Skonic.getSkinCacheManager().get(url);
            if (cachedSkin != null) {
                return cachedSkin;
            } else {
                mineskinData = getMineskinData(url, slim);
                JsonObject skinTexture = mineskinData.get("texture").getAsJsonObject();
                String skinValue = skinTexture.get("value").getAsString();
                String skinSignature = skinTexture.get("signature").getAsString();

                Skin urlSkin = new Skin(skinValue, skinSignature);
                Skonic.getSkinCacheManager().set(url, urlSkin);

                return urlSkin;
            }
        } catch (Exception e) {
            throw new SkinGenerationException("An unexpected error occurred during skin retrieval for URL: " + url, e);
        }
    }

    /**
     * Performs the HTTP request to the Mineskin API.
     *
     * @param url   the URL to be submitted to the API.
     * @param slim  {@code true} to request the 'slim' model; {@code false} for the standard model.
     *
     * @return      a {@link JsonObject} containing the complete 'data' section from the Mineskin response.
     *
     * @since       1.2.5
     */
    private static JsonObject getMineskinData(String url, boolean slim) throws SkinGenerationException {

        try {
            return EXECUTOR.<JsonObject>submit(() -> {
                HttpURLConnection connection = null;
                try {
                    URL target = new URL(MINESKIN_API_URL);
                    connection = (HttpURLConnection) target.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);

                    connection.setRequestProperty("User-Agent", "Skonic/" + Skonic.getInstance().getDescription().getVersion());
                    connection.setRequestProperty("Cache-Control", "no-cache");
                    connection.setRequestProperty("Accept", "application/json");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setConnectTimeout(1000);
                    connection.setReadTimeout(30000);

                    JsonObject request = new JsonObject();
                    request.addProperty("url", url);
                    request.addProperty("name", "");
                    if (slim)
                        request.addProperty("variant", "slim");

                    String jsonString = request.toString().replace("\\", "");
                    try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
                        outputStream.writeBytes(jsonString);
                    }
                    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        throw new IOException("Mineskin API request failed with HTTP error code: " + connection.getResponseCode());
                    }
                    try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
                        JsonObject output = JsonParser.parseReader(reader).getAsJsonObject();
                        return output.get("data").getAsJsonObject();
                    }

                } catch (IOException e) {
                    Skonic.logger().severe("Failed to generate skin from URL: " + url, e);
                    throw new RuntimeException("Network or IO error during Mineskin generation.", e);
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }).get();
        } catch (InterruptedException e) {
            throw new SkinGenerationException("Skin generation was interrupted for URL: " + url, e);
        } catch (ExecutionException e) {
            throw new SkinGenerationException("Mineskin API call failed for URL: " + url, e);
        }
    }

    /**
     * Performs the HTTP request to the Mineskin API.
     *
     * @param url   the URL to be submitted to the API.
     * @param slim  {@code true} to request the 'slim' model; {@code false} for the standard model.
     *
     * @return      a {@link JsonObject} containing the complete 'data' section from the Mineskin response.
     *
     * @since       1.0.0
     */
    @Deprecated(forRemoval = true)
    public static JsonObject generateFromURL(String url, boolean slim) throws InterruptedException, ExecutionException {
        return EXECUTOR.<JsonObject>submit(() -> {
            DataOutputStream out = null;
            InputStreamReader reader = null;
            try {
                URL target = new URL("https://api.mineskin.org/generate/url");
                HttpURLConnection con = (HttpURLConnection)target.openConnection();
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                con.setRequestProperty("User-Agent", "Skonic/" + Skonic.getInstance().getDescription().getVersion());
                con.setRequestProperty("Cache-Control", "no-cache");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestProperty("Content-Type", "application/json");
                con.setConnectTimeout(1000);
                con.setReadTimeout(30000);
                out = new DataOutputStream(con.getOutputStream());
                JsonObject req = new JsonObject();
                req.addProperty("url", url);
                req.addProperty("name", "");
                if (slim)
                    req.addProperty("variant", "slim");
                out.writeBytes(req.toString().replace("\\", ""));
                out.close();
                reader = new InputStreamReader(con.getInputStream());
                String str = CharStreams.toString(reader);
                JsonObject output = (new JsonParser()).parse(str).getAsJsonObject();
                JsonObject data = output.get("data").getAsJsonObject();
                con.disconnect();
                return data;
            } finally {
                if (out != null)
                    try {
                        out.close();
                    } catch (IOException iOException) {}
                if (reader != null)
                    try {
                        reader.close();
                    } catch (IOException iOException) {}
            }
        }).get();
    }

}
