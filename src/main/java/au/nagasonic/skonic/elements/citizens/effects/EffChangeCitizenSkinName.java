package au.nagasonic.skonic.elements.citizens.effects;

import au.nagasonic.skonic.Skonic;
import ch.njol.skript.Skript;
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
@Examples("set skin of last spawned npc to \"Nagasonic\"")
public class EffChangeCitizenSkinName extends AsyncEffect {
    static {
        Skript.registerEffect(EffChangeCitizenSkinName.class,
                "(set|change) (citizen|npc)[s] %npcs%['s] skin to %string%",
                "(set|change) skin of (citizen|npc)[s] %npcs% to %string%");
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
