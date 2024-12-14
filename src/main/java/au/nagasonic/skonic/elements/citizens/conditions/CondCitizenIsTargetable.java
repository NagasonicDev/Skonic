package au.nagasonic.skonic.elements.citizens.conditions;

import au.nagasonic.skonic.Skonic;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.TargetableTrait;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

@Name("Citizen Is Targetable")
@Description("Make a Citizens NPC targetable by other mobs.")
@Since("1.2")
@RequiredPlugins("Citizens")
@Examples({"if last created npc can be targeted:",
        "\tmake last created npc not targetable"})
@DocumentationId("")
public class CondCitizenIsTargetable extends Condition {
    static {
        Skript.registerCondition(CondCitizenIsTargetable.class,
                "%npcs% (is|can be|are) target(able|ed)",
                "%npcs% (is(n't| not)|are(n't| not)|can('t|not be)) target(able|ed)");
    }
    private Expression<NPC> npcExpr;
    private int pattern;
    @Override
    public boolean check(Event event) {
        if (npcExpr == null) {
            Skonic.log(Level.SEVERE, "NPC is null");
            return false;
        }
        NPC npc = npcExpr.getSingle(event);
        if (npc == null) {
            Skonic.log(Level.SEVERE, "NPC is null");
            return false;
        }
        boolean targetable = npc.getOrAddTrait(TargetableTrait.class).isTargetable();
        return targetable;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return npcExpr.toString(event, b) + " is targetable";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        return true;
    }
}
