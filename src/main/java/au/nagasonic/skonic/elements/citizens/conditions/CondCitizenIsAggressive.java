package au.nagasonic.skonic.elements.citizens.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.util.NMS;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Is Aggressive")
@Description("Returns the bool of whether the citizen is aggressive.")
@RequiredPlugins("Citizens")
@Examples({"if npc with id 3 is aggressive:",
        "\tbroadcast \"Npc is Aggressive\""})
@Since("1.2")
@DocumentationId("")
public class CondCitizenIsAggressive extends Condition {
    static {
        Skript.registerCondition(CondCitizenIsAggressive.class,
                "%npc% [is] aggressive");
    }
    private Expression<NPC> npcExpr;
    @Override
    public boolean check(Event e) {
        NPC npc = npcExpr.getSingle(e);
        if (npc == null) return false;

        return npc.data().get(NPC.Metadata.AGGRESSIVE);
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "Citizen " + npcExpr.toString(e, b) + " is aggressive.";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        return true;
    }
}
