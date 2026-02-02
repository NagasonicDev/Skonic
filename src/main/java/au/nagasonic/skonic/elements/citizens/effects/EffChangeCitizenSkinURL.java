package au.nagasonic.skonic.elements.citizens.effects;

import au.nagasonic.skonic.Skonic;
import au.nagasonic.skonic.elements.skins.Skin;
import au.nagasonic.skonic.elements.util.SkinUtils;
import au.nagasonic.skonic.exceptions.SkinGenerationException;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;


@Name("Set Citizen Skin - URL")
@Description("Sets the citizen with the id specified to the skin linked on the url.")
@RequiredPlugins("Citizens")
@Since("1.0.0")
@Examples("set skin of npc last spawned npc to url \"https://www.minecraftskins.com/uploads/skins/2024/12/12/--*ginger-bread-man*----tcer3--22929673.png?v695\"")
public class EffChangeCitizenSkinURL extends Effect {

    static {
        Skript.registerEffect(EffChangeCitizenSkinURL.class,
                "(change|set) (npc|citizen)[s] %npcs%['s] skin to url %string%",
                "(change|set) skin of (npc|citizen)[s] %npcs% to url %string%");
    }

    private Expression<NPC> npcExpr;
    private Expression<String> urlExpr;

    @Override
    protected void execute(Event e) {
        final NPC[] npcs = npcExpr.getArray(e);
        final String url = urlExpr.getSingle(e);

        if (npcs == null || npcs.length == 0) {
            Skript.error("No citizens found to change the skin of.", ErrorQuality.SEMANTIC_ERROR);
            return;
        }

        if (url == null) {
            Skript.error("Specified URL is null.", ErrorQuality.SEMANTIC_ERROR);
            return;
        }

        if (!Skonic.getInstance().isEnabled()) {
            Skonic.logger().warn("Plugin is disabled. Skipping citizen skin change task for URL: %s", url);
            return;
        }

        Skin skin;
        try {
            skin = SkinUtils.getSkinFromMineskinUrl(url, false);
        } catch (SkinGenerationException exception) {
            Skript.error(
                    "Failed to generate skin from URL: "
                            + url
                            + ". Details: "
                            + exception.getMessage(),
                    ErrorQuality.SEMANTIC_ERROR);
            return;
        }

        String textureEncoded = skin.getTexture();
        String signature = skin.getSignature();
        String uuid = skin.getUUID().toString();

        for (NPC npc : npcs) {
            if (npc == null) {
                Skonic.logger().warn("Skipping NPC: object is null.");
                continue;
            }

            try {
                SkinTrait trait = npc.getOrAddTrait(SkinTrait.class);
                trait.setSkinPersistent(uuid, signature, textureEncoded);
                Skonic.logger().info("Set skin of citizen ID %d to URL %s.", npc.getId(), url);
            } catch (Exception exception) {
                Skonic.logger().severe(
                        exception,
                        "Failed to set skin for NPC ID %d to URL %s.",
                        npc.getId(), url
                );
            }
        }
    }

//    @Override
//    protected void execute(Event e) {
//        final NPC[] npcs = npcExpr.getArray(e);
//        final String url = urlExpr.getSingle(e);
//
//        if (npcs == null || npcs.length == 0) {
//            Skript.error("No citizens found to change the skin of.", ErrorQuality.SEMANTIC_ERROR);
//            return;
//        }
//        if (url == null) {
//            Skript.error("Specified URL is null.", ErrorQuality.SEMANTIC_ERROR);
//            return;
//        }
//
//        if (!Skonic.getInstance().isEnabled()) {
//            Skonic.logger().warn("Plugin is disabled. Skipping citizen skin change task for URL: ('%s').", url);
//            return;
//        }
//
//        CompletableFuture<JsonObject> skinDataFuture = CompletableFuture.supplyAsync(() -> {
//            try {
//                URL urlObject = new URL(url);
//                String urlString = urlObject.toString();
//
//                if (!urlString.contains("https://www.minecraftskins.com") && !urlString.contains("http://textures.minecraft.net")){
//                    return null;
//                }
//
//                return SkinUtils.generateFromURL(url, false);
//            } catch (MalformedURLException | InterruptedException | ExecutionException ex) {
//                Skonic.log(
//                        Level.SEVERE,
//                        "Error retrieving skin from URL: ('"
//                                + url
//                                + "'). Error details:\n"
//                                + ex);
//                return null;
//            }
//        });
//
//
//
//        JsonObject data;
//        try {
//            data = skinDataFuture.join();
//        } catch (Exception ex) {
//            Skript.error(
//                    "An error occurred during asynchronous skin retrieval for URL: ('"
//                            + url
//                            +"').");
//            return;
//        }
//
//        if (data == null) {
//            Skonic.log(
//                    Level.SEVERE,
//                    "Skin retrieval failed for URL: ('"
//                            + url
//                            + "').");
//            Skript.error("Skin retrieval failed to get data.", ErrorQuality.SEMANTIC_ERROR);
//            return;
//        }
//
//        String uuid = data.get("uuid").getAsString();
//        JsonObject texture = data.get("texture").getAsJsonObject();
//        String textureEncoded = texture.get("value").getAsString();
//        String signature = texture.get("signature").getAsString();
//
//        for (NPC npc : npcs) {
//            if (npc == null) {
//                Skonic.log(
//                        Level.WARNING,
//                        "Skipping NPC: NPC object is null."
//                );
//                continue;
//            }
//
//            try {
//                SkinTrait trait = npc.getOrAddTrait(SkinTrait.class);
//                trait.setSkinPersistent(uuid, signature, textureEncoded);
//                Skonic.log(Level.INFO, "Set skin of citizen with id " + npc.getId() + " to " + url);
//            } catch (Exception ex) {
//                Skonic.log(
//                        Level.SEVERE,
//                        "Failed to set skin for NPC ('"
//                                + npc.getId()
//                                + "') to ('"
//                                + url
//                                + "'). Error details:\n"
//                                + ex.getMessage()
//                );
//            }
//        }
//    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "change skin of citizens " + npcExpr.toString(e, debug) + " to url " + urlExpr.toString(e, debug);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        urlExpr = (Expression<String>) exprs[1];
        return true;
    }
}
