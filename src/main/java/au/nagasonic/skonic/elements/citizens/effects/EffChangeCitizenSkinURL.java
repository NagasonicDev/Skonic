package au.nagasonic.skonic.elements.citizens.effects;

import au.nagasonic.skonic.Skonic;
import au.nagasonic.skonic.elements.util.SkinUtils;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.skript.util.AsyncEffect;
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
@Examples("set skin of last spawned npc to url \"https://www.minecraftskins.com/uploads/skins/2024/12/12/--*ginger-bread-man*----tcer3--22929673.png?v695\"")
public class EffChangeCitizenSkinURL extends AsyncEffect {

    static {
        Skript.registerEffect(EffChangeCitizenSkinURL.class,
                "(change|set) (npc|citizen)[s] %npcs%['s] skin to url %string%",
                "(change|set) skin of (npc|citizen)[s] %npcs% to url %string%");
    }

    private Expression<NPC> npcExpr;
    private Expression<String> urlExpr;

    @Override
    protected void execute(Event e) {
        NPC[] npcs = npcExpr.getArray(e);
        //Check if ID is null
        if (npcs != null){
            for (NPC npc : npcs){
                if (npc == null) { Skript.error("NPC " + npc.toString() + " is null"); }
            }
            String url = urlExpr.getSingle(e);
            //Check if the URL is Null
            if (url != null){
                //Check if the URL String is actually a URL
                try {
                    URL Url = new URL(url);
                    //Check if the url is a API valid url
                    String urlString = Url.toString();
                    if (!urlString.contains("https://www.minecraftskins.com") || urlString.contains("http://textures.minecraft.net")){
                        Skript.error("Specified URL is not a valid URL. Please use a url from \"https://www.minecraftskins.com\" or \"http://textures.minecraft.net\"", ErrorQuality.SEMANTIC_ERROR);
                    }
                } catch (MalformedURLException ex) {
                    Skript.error("Specified 'URL' is not a valid URL");
                    throw new RuntimeException(ex);
                }
                JsonObject data = null;
                try {
                    data = SkinUtils.generateFromURL(url, false);
                } catch (InterruptedException ex) {
                    Skript.error("There was an error in retrieving the skin from " + url, ErrorQuality.SEMANTIC_ERROR);
                    throw new RuntimeException(ex);
                } catch (ExecutionException ex) {
                    Skript.error("There was an error in retrieving the skin from " + url, ErrorQuality.SEMANTIC_ERROR);
                    throw new RuntimeException(ex);
                }
                String uuid = data.get("uuid").getAsString();
                JsonObject texture = data.get("texture").getAsJsonObject();
                String textureEncoded = texture.get("value").getAsString();
                String signature = texture.get("signature").getAsString();
                for (NPC npc : npcs){
                    SkinTrait trait = npc.getOrAddTrait(SkinTrait.class);
                    Bukkit.getScheduler().runTask(Skonic.getInstance(), () -> {
                        try {
                            trait.setSkinPersistent(uuid, signature, textureEncoded);
                            Skonic.log(Level.INFO, "Set skin of citizen with id " + npc.getId() + " to " + url);
                        } catch (IllegalArgumentException err) {
                            Skonic.log(Level.SEVERE, "There was an error setting the skin of citizen with id " + npc.getId() + " to " + url + err.getMessage());
                        }
                    });
                }
            }else {
                Skript.error("Specified URL is null");
            }
        }else{
            Skript.error("Specified ID is null", ErrorQuality.SEMANTIC_ERROR);
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "change skin of citizens " + npcExpr.toString(e, debug) + " to url " + urlExpr.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        urlExpr = (Expression<String>) exprs[1];
        return true;
    }
}
