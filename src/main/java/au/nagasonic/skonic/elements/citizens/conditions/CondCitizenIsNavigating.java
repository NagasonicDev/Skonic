package au.nagasonic.skonic.elements.citizens.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Is Navigating")
@Description("Returns the bool of whether the citizen is pathfinding.")
@RequiredPlugins("Citizens")
@Examples({"if npc with id 1 is navigating:",
        "\tmake citizen npc with id 1 attack player"})
@Since("1.0.7")
@DocumentationId("12482")
public class CondCitizenIsNavigating extends Condition {
    static {
        Skript.registerCondition(CondCitizenIsNavigating.class,
                "%npcs% (is|are) navigating",
                "%npcs% (is(n't| not)|are(n't| not)) navigating");
    }
    private Expression<NPC> npcExpr;
    private int pattern;
    @Override
    public boolean check(Event e) {
        NPC[] npcs = npcExpr.getArray(e);
        for (NPC npc : npcs){
            if (npc != null){
                if (pattern == 0){
                    if (npc.getNavigator().isNavigating() == false) return false;
                    return true;
                }else{
                    if (npc.getNavigator().isNavigating() == true) return false;
                    return true;
                }
            }
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
        this.pattern = pattern;
        return true;
    }
}
