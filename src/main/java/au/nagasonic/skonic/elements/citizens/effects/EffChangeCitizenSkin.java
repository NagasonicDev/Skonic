package au.nagasonic.skonic.elements.citizens.effects;

import au.nagasonic.skonic.elements.skins.Skin;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.skript.util.AsyncEffect;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

@Name("Set Citizen Skin - Skin")
@Description("Sets the skin of the npc to the given skin.")
@RequiredPlugins("Citizens")
@Since("1.0.7")
@Examples({"set skin of last spawned npc to player's skin", "change npc with id 2's skin to player's skin"})
public class EffChangeCitizenSkin extends AsyncEffect {

    static {
        Skript.registerEffect(EffChangeCitizenSkin.class,
                "(change|set) %npcs%['s] skin to %skin%",
                "(change|set) skin of %npcs% to %skin%");
    }

    private Expression<NPC> npcExpr;
    private Expression<Skin> skinExpr;

    @Override
    protected void execute(Event e) {
        //Check if ID is null
        NPC[] npcs = npcExpr.getArray(e);
        //Check if there is a citizen with the ID
        if (npcs != null){
            //Check if the URL is Null
            for (NPC npc : npcs){
                if (npc == null) { Skript.error("NPC " + npc.toString() + " is null"); }
            }
            Skin skin = skinExpr.getSingle(e);
            if (skin != null){
                String value = skin.getTexture();
                if (value == null) Skript.error("Specified skin's value is null");
                String uuid = String.valueOf(skin.getUUID());
                if (uuid == null) Skript.error("Specified skin's uuid is null");
                String signature = skin.getSignature();
                if (signature == null) Skript.error("Specified skin's signature is null");
                for (NPC npc : npcs){
                    SkinTrait trait = npc.getOrAddTrait(SkinTrait.class);
                    Bukkit.getScheduler().runTaskAsynchronously(CitizensAPI.getPlugin(), () -> {
                        try {
                            trait.setSkinPersistent(uuid, signature, value);
                        } catch (IllegalArgumentException err) {
                            Bukkit.getLogger().log(Level.SEVERE, "There was an error setting the skin of citizen with id " + npc.getId() + err.getMessage());
                        }
                    });
                }
            }else {
                    Skript.error("Specified skin is null");
            }
        }else {
            Skript.error("There are no citizens to change the skin of.", ErrorQuality.SEMANTIC_ERROR);
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "change skin of citizens " + npcExpr.toString(e, debug) + " to skin " + skinExpr.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        skinExpr = (Expression<Skin>) exprs[1];
        return true;
    }
}
