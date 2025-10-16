package au.nagasonic.skonic.elements.citizens.effects;

import au.nagasonic.skonic.Skonic;
import au.nagasonic.skonic.elements.skins.Skin;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
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
public class EffChangeCitizenSkin extends Effect {

    static {
        Skript.registerEffect(EffChangeCitizenSkin.class,
                "(change|set) (npc|citizen)[s] %npcs%['s] skin to %skin%",
                "(change|set) skin of (npc|citizen)[s] %npcs% to %skin%");
    }

    private Expression<NPC> npcExpr;
    private Expression<Skin> skinExpr;

    @Override
    protected void execute(Event e) {
        final NPC[] npcs = npcExpr.getArray(e);
        final Skin skin = skinExpr.getSingle(e);

        if (npcs == null || npcs.length == 0) {
            Skonic.log(Level.INFO, "No NPCs were specified for the skin change.");
            return;
        }
        if (skin == null) {
            Skonic.log(Level.SEVERE, "The specified skin is null, cannot apply skin.");
            return;
        }

        final String value = skin.getTexture();
        if (value == null) Skript.error("Specified skin's value is null");
        final String uuid = String.valueOf(skin.getUUID());
        if (uuid == null) Skript.error("Specified skin's uuid is null");
        final String signature = skin.getSignature();
        if (signature == null) Skript.error("Specified skin's signature is null");

        for (NPC npc : npcs) {
            if (npc == null) {
                Skonic.log(
                        Level.WARNING,
                        "Skipping NPC: NPC object is null."
                );
                continue;
            }

            try {
                SkinTrait trait = npc.getOrAddTrait(SkinTrait.class);
                trait.setSkinPersistent(uuid, signature, value);
            } catch (Exception ex) {
                Skonic.log(
                        Level.SEVERE,
                        "Failed to set skin for NPC ('"
                                + npc.getId()
                                + "'). Error details:\n"
                                + ex.getMessage()
                );
            }
        }

    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "change skin of citizens " + npcExpr.toString(e, debug) + " to skin " + skinExpr.toString(e, debug);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        skinExpr = (Expression<Skin>) exprs[1];
        return true;
    }
}


//package au.nagasonic.skonic.elements.citizens.effects;
//
//import au.nagasonic.skonic.Skonic;
//import au.nagasonic.skonic.elements.skins.Skin;
//import ch.njol.skript.Skript;
//import ch.njol.skript.doc.*;
//import ch.njol.skript.lang.Expression;
//import ch.njol.skript.lang.SkriptParser;
//import ch.njol.skript.util.AsyncEffect;
//import ch.njol.util.Kleenean;
//import net.citizensnpcs.api.npc.NPC;
//import net.citizensnpcs.trait.SkinTrait;
//import org.bukkit.Bukkit;
//import org.bukkit.event.Event;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.logging.Level;
//
//@Name("Set Citizen Skin - Skin")
//@Description("Sets the skin of the npc to the given skin.")
//@RequiredPlugins("Citizens")
//@Since("1.0.7")
//@Examples({"set skin of last spawned npc to player's skin", "change npc with id 2's skin to player's skin"})
//public class EffChangeCitizenSkin extends AsyncEffect {
//
//    static {
//        Skript.registerEffect(EffChangeCitizenSkin.class,
//                "(change|set) (npc|citizen)[s] %npcs%['s] skin to %skin%",
//                "(change|set) skin of (npc|citizen)[s] %npcs% to %skin%");
//    }
//
//    private Expression<NPC> npcExpr;
//    private Expression<Skin> skinExpr;
//
//    @Override
//    protected void execute(Event e) {
//        final NPC[] npcs = npcExpr.getArray(e);
//        final Skin skin = skinExpr.getSingle(e);
//
//        if (npcs == null || npcs.length == 0) {
//            Skonic.log(Level.INFO, "No NPCs were specified for the skin change.");
//            return;
//        }
//        if (skin == null) {
//            Skonic.log(Level.SEVERE, "The specified skin is null, cannot apply skin.");
//            return;
//        }
//
//        final String value = skin.getTexture();
//        if (value == null) Skript.error("Specified skin's value is null");
//        final String uuid = String.valueOf(skin.getUUID());
//        if (uuid == null) Skript.error("Specified skin's uuid is null");
//        final String signature = skin.getSignature();
//        if (signature == null) Skript.error("Specified skin's signature is null");
//
//        if (!Skonic.getInstance().isEnabled()) {
//            Skonic.log(
//                    Level.WARNING,
//                    "Plugin is disabled. Skipping citizen skin change task."
//            );
//            return;
//        }
//
//        Bukkit.getScheduler().runTask(
//                Skonic.getInstance(),
//                () -> {
//                    for (NPC npc : npcs) {
//                        if (npc == null) {
//                            Skonic.log(
//                                    Level.WARNING,
//                                    "Skipping NPC: NPC object is null."
//                            );
//                            continue;
//                        }
//
//                        try {
//                            SkinTrait trait = npc.getOrAddTrait(SkinTrait.class);
//                            trait.setSkinPersistent(uuid, signature, value);
//                        } catch (Exception ex) {
//                            Skonic.log(
//                                    Level.SEVERE,
//                                    "Failed to set skin for NPC ('"
//                                            + npc.getId()
//                                            + "'). Error details:\n"
//                                            + ex.getMessage()
//                            );
//                        }
//                    }
//                }
//        );
//
//    }
//
//    @Override
//    public String toString(@Nullable Event e, boolean debug) {
//        return "change skin of citizens " + npcExpr.toString(e, debug) + " to skin " + skinExpr.toString(e, debug);
//    }
//
//    @Override
//    @SuppressWarnings("unchecked")
//    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
//        npcExpr = (Expression<NPC>) exprs[0];
//        skinExpr = (Expression<Skin>) exprs[1];
//        return true;
//    }
//}
