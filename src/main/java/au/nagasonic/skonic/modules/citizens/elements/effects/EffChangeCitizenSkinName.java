package au.nagasonic.skonic.modules.citizens.elements.effects;

import au.nagasonic.skonic.Skonic;
import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.SyntaxInfo;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.AsyncEffect;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

@Name("Set Citizen Skin - Name")
@Description("Set a citizen's skin by name." +
        "Only works if citizen is a player.")
@RequiredPlugins("Citizens")
@Since("1.0.0")
@Examples("set skin of citizen last spawned npc to \"Nagasonic\"")
public class EffChangeCitizenSkinName extends AsyncEffect {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EFFECT,
            SyntaxInfo.builder(EffChangeCitizenSkinName.class)
                .addPatterns(
                    "(set|change) (citizen|npc)[s] %npcs%['s] skin to %string%",
                    "(set|change) skin of (citizen|npc)[s] %npcs% to %string%"
                )
                .build()
        );
    }

    private Expression<NPC> npcExpr;
    private Expression<String> name;

    @Override
    protected void execute(Event e) {
        final NPC[] npcs = npcExpr.getArray(e);
        final String skinName = name.getSingle(e);

        if (npcs == null || npcs.length == 0) {
            Skonic.log(Level.INFO, "No NPCs were specified for the skin change.");
            return;
        }
        if (skinName == null) {
            Skonic.log(Level.SEVERE, "The specified skin name is null, cannot apply skin.");
            return;
        }

        Bukkit.getScheduler().runTask(
                Skonic.getInstance(),
                () -> {
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
                            trait.setShouldUpdateSkins(true);
                            trait.setSkinName(skinName);
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
        );
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "change skin of citizen with id " + npcExpr.toString(e, debug) + " to name " + name.toString(e, debug);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        name = (Expression<String>) exprs[1];
        return true;
    }
}
