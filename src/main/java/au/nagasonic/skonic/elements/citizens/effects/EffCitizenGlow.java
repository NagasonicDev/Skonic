package au.nagasonic.skonic.elements.citizens.effects;

import au.nagasonic.skonic.Skonic;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.AsyncEffect;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.ScoreboardTrait;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

@Name("Citizen Start/Stop Glowing")
@Description("Makes a Citizens NPC start or stop glowing, with color.")
@Since("1.2.2-b1")
@RequiredPlugins("Citizens")
@Examples("make all npcs start glowing with color red")
@SuppressWarnings("deprecation")
// ChatColor is deprecated, but Citizen's API still hasn't updated its ScoreboardTrait.setColor() method.
public class EffCitizenGlow extends AsyncEffect {
    static {
        Skript.registerEffect(EffCitizenGlow.class,
                "make %npcs% (start|:stop) glowing [c:[with colo[u]r] %-chatcolor%]",
                "(start|:stop) %npcs% [from] glowing [c:[with colo[u]r] %-chatcolor%]");
    }

    private Expression<NPC> npcsExpr;
    private Expression<ChatColor> colorExpr;
    private boolean not;
    private boolean c;

    @Override
    protected void execute(Event e) {
        final NPC[] npcs = npcsExpr.getArray(e);
        final ChatColor color = c ? colorExpr.getSingle(e) : null;

        if (npcs == null || npcs.length == 0) {
            Skonic.log(Level.INFO, "No NPCs were specified for the glow effect.");
            return;
        }
        if (c && color == null) {
            Skonic.log(Level.SEVERE, "Glow color was requested but the expression returned null.");
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

                        if (!npc.isSpawned()) {
                            Skonic.log(
                                    Level.WARNING,
                                    "Skipping NPC ('"
                                            + npc.getId()
                                            + "'): NPC object exists but is not spawned."
                            );
                            continue;
                        }

                        try {
                            npc.data().setPersistent(NPC.Metadata.GLOWING, not);

                            if (c) {
                                ScoreboardTrait trait = npc.getOrAddTrait(ScoreboardTrait.class);
                                trait.setColor(color);
                            }

                        } catch (Exception ex) {
                            Skonic.log(
                                    Level.SEVERE,
                                    "Failed to set glow/color for NPC ('"
                                            + npc.getId()
                                            + "'). Error details:\n"
                                            + ex
                            );
                        }
                    }
                }
        );
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return !not ? "make " + npcsExpr.toString(event, debug) + " stop glowing" : "make " + npcsExpr.toString(event, debug) + " start glowing";
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.npcsExpr = (Expression<NPC>) exprs[0];
        this.colorExpr = (Expression<ChatColor>) exprs[1];
        this.not = !parseResult.hasTag("stop");
        this.c = parseResult.hasTag("c");
        return true;
    }
}
