package au.nagasonic.skonic.elements.citizens.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Is Navigating")
@Description("Returns the bool of whether the citizen is pathfinding.")
@RequiredPlugins("Citizens")
@Examples({"if npc with id 1 is navigating:",
        "\tmake citizen npc with id 1 attack player"})
@Since({"1.0.7, 1.2.3(location)"})
public class CondCitizenIsNavigating extends Condition {
    static {
        Skript.registerCondition(CondCitizenIsNavigating.class,
                "(citizen|npc)[s] %npcs% (is|are) navigating [l:to %location%]",
                "(citizen|npc)[s] %npcs% (is(n't| not)|are(n't| not)) navigating [l:to %location%]");
    }
    private Expression<NPC> npcExpr;
    private Expression<Location> locExpr;
    private int pattern;
    private boolean l;
    @Override
    public boolean check(Event e) {
        NPC[] npcs = npcExpr.getArray(e);
        if (npcs != null) {
            for (NPC npc : npcs) {
                if (npc != null) {
                    if (pattern == 0) {
                        if (l){
                            Location loc = locExpr.getSingle(e);
                            if (loc != null){
                                if (npc.getNavigator().getTargetAsLocation() == loc) return false;
                            }
                        }else{
                            if (npc.getNavigator().isNavigating() == false) return false;
                        }
                    } else {
                        if (l){
                            Location loc = locExpr.getSingle(e);
                            if (loc != null){
                                if (npc.getNavigator().getTargetAsLocation() != loc) return false;
                            }
                        }else{
                            if (npc.getNavigator().isNavigating() == true) return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "Citizen " + npcExpr.toString(e, b) + " is navigating.";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        locExpr = (Expression<Location>) exprs[1];
        this.l = parseResult.hasTag("l");
        this.pattern = pattern;
        return true;
    }
}
