package au.nagasonic.skonic.elements.citizens.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class CondCitizenIsFlyable extends Condition {
    static {
        Skript.registerCondition(CondCitizenIsFlyable.class,
                "%npcs% (is|are|can) fly[able]",
                "%npcs% (is(n't| not)|are(n't| not)|can('t|not| not)) fly[able]");
    }
    private Expression<NPC> npcExpr;
    private int pattern;
    @Override
    public boolean check(Event event) {
        NPC[] npcs = npcExpr.getArray(event);
        if (npcs != null){
            for (NPC npc : npcs){
                if (npc != null){
                    if (pattern == 0){
                        if (npc.isFlyable() == false) return false;
                        return true;
                    }else{
                        if (npc.isFlyable() == true) return false;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        if (pattern == 0) return "Citizen " + npcExpr.toString(event, debug) + " are flyable";
        else return "Citizen " + npcExpr.toString(event, debug) + " aren't flyable";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.npcExpr = (Expression<NPC>) exprs[0];
        this.pattern = matchedPattern;
        return true;
    }
}
