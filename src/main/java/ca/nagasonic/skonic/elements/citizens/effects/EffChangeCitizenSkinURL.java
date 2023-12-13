package ca.nagasonic.skonic.elements.citizens.effects;

import ca.nagasonic.skonic.elements.util.SkinUtils;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import com.google.gson.JsonObject;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

@Name("Set Citizen Skin - URL")
@Description("Sets the citizen with the id specified to the skin linked on the url.")
@RequiredPlugins("Citizens")
@Since("1.0.0")
@Examples("")
public class EffChangeCitizenSkinURL extends Effect {

    static {
        Skript.registerEffect(EffChangeCitizenSkinURL.class, "(change|set) (citizen|npc) %number% skin to url %string%");
    }

    private Expression<Number> id;
    private Expression<String> url;

    @Override
    protected void execute(Event e) {
        //Check if ID is null
        if (id.getSingle(e) != null){
            NPC npc = CitizensAPI.getNPCRegistry().getById(id.getSingle(e).intValue());
            //Check if there is a citizen with the ID
            if (npc != null){
                //Check if the URL is Null
                if (url.getSingle(e) != null){
                    //Check if the URL String is actually a URL
                    try {
                        URL Url = new URL(url.getSingle(e));
                        //Check if the url is a API valid url
                        String urlString = Url.toString();
                        if (!urlString.contains("https://minecraftskins.com") || urlString.contains("http://textures.minecraft.net")){
                            Skript.error("Specified URL is not a valid URL. Please use a url from \"https://minecraftskins.com\" or \"http://textures.minecraft.net\"", ErrorQuality.SEMANTIC_ERROR);
                        }
                    } catch (MalformedURLException ex) {
                        Skript.error("Specified 'URL' is not a valid URL");
                        throw new RuntimeException(ex);
                    }
                    SkinTrait trait = npc.getOrAddTrait(SkinTrait.class);
                    JsonObject data = null;
                    try {
                        data = SkinUtils.generateFromURL(url.getSingle(e), false);
                    } catch (InterruptedException ex) {
                        Skript.error("There was an error in retrieving the skin from " + url.getSingle(e), ErrorQuality.SEMANTIC_ERROR);
                        throw new RuntimeException(ex);
                    } catch (ExecutionException ex) {
                        Skript.error("There was an error in retrieving the skin from " + url.getSingle(e), ErrorQuality.SEMANTIC_ERROR);
                        throw new RuntimeException(ex);
                    }
                    String uuid = data.get("uuid").getAsString();
                    JsonObject texture = data.get("texture").getAsJsonObject();
                    String textureEncoded = texture.get("value").getAsString();
                    String signature = texture.get("signature").getAsString();
                    Bukkit.getScheduler().runTask(CitizensAPI.getPlugin(), () -> {
                        try {
                            trait.setSkinPersistent(uuid, signature, textureEncoded);
                            Bukkit.getLogger().log(Level.INFO, "Set skin of citizen with id " + id.getSingle(e) + " to " + url.getSingle(e));
                        } catch (IllegalArgumentException err) {
                            Bukkit.getLogger().log(Level.SEVERE, "There was an error setting the skin of citizen with id " + id.getSingle(e) + " to " + url.getSingle(e) + err.getMessage());
                        }
                    });
                }else {
                    Skript.error("Specified URL is null");
                }
            }else {
                Skript.error("There is no citizen with ID" + id.getSingle(e).toString(), ErrorQuality.SEMANTIC_ERROR);
            }
        }else{
            Skript.error("Specified ID is null", ErrorQuality.SEMANTIC_ERROR);
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "change skin of citizen with id " + id.getSingle(e) + " to url " + url.getSingle(e);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        id = (Expression<Number>) exprs[0];
        url = (Expression<String>) exprs[1];
        return true;
    }
}
