package au.nagasonic.skonic.elements.citizens.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPC.Metadata;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class CondCitizenIsCollidable extends Condition {
    static {
        Skript.registerCondition(CondCitizenIsCollidable.class,
                "%npcs% (is|are) (collidable|pushable) [f:by fluids]",
                "%npcs% (are(n't| not)|is(n't| not)) (collidable|pushable) [f:by fluids]");
    }
    private Expression<NPC> npcExpr;
    private boolean f;
    private int pattern;
    @Override
    public boolean check(Event event) {
        NPC[] npcs = npcExpr.getArray(event);
        if (npcs == null) return false;
        for (NPC npc : npcs){
            if (npcs == null) return false;
            Metadata metadata = f == true ? Metadata.FLUID_PUSHABLE: Metadata.COLLIDABLE;
            if (pattern == 0){
                if (npc.data().has(metadata) == false) return false;
                return true;
            }else{
                if (npc.data().has(metadata) == true) return false;
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        if (pattern == 0) return "Citizen " + npcExpr.toString(event, debug) + " are collidable";
        else return "Citizen " + npcExpr.toString(event, debug) + " aren't collidable";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.npcExpr = (Expression<NPC>) exprs[0];
        this.pattern = matchedPattern;
        this.f = parseResult.hasTag("f");
        return true;
    }
}