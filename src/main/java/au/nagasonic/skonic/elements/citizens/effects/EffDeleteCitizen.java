package au.nagasonic.skonic.elements.citizens.effects;

import au.nagasonic.skonic.Skonic;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

@Name("Delete Citizen")
@Description("Destroy a citizen by id.")
@Since("1.0.0, 1.2.2 (Multiple)")
@Examples("delete all npcs")
@RequiredPlugins("Citizens")
public class EffDeleteCitizen extends Effect {
    static {
        Skript.registerEffect(EffDeleteCitizen.class,
                "delete %npcs%");
    }

    private Expression<NPC> npcExpr;

    @Override
    protected void execute(Event e) {
        NPC[] npcs = npcExpr.getArray(e);
        if (npcs != null){
            for (NPC npc : npcs) {
                if (npc.getOwningRegistry() != null){
                    npc.destroy();
                }else Skonic.log(Level.SEVERE, "The citizen has no Owning Registry");
            }
        }else Skonic.log(Level.SEVERE, "There is no citizen " + npcs.toString());
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "delete citizens " + npcExpr.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        return true;
    }
}
