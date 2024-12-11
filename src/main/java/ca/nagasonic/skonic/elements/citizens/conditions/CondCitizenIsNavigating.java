package ca.nagasonic.skonic.elements.citizens.conditions;

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
@Examples("")
@Since("1.0.7")
@DocumentationId("12482")
public class CondCitizenIsNavigating extends Condition {
    static {
        Skript.registerCondition(CondCitizenIsNavigating.class,
                "%npc% [is] navigating");
    }
    private Expression<NPC> npcExpr;
    @Override
    public boolean check(Event e) {
        NPC npc = npcExpr.getSingle(e);
        if (npc == null) return false;
        return npc.getNavigator().isNavigating();
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "Citizen " + npcExpr.toString(e, b) + " is navigating.";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        return true;
    }
}
