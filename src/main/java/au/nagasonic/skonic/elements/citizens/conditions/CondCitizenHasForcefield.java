package au.nagasonic.skonic.elements.citizens.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.ForcefieldTrait;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Has Forcefield")
@Description("Whether a Citizens NPC has a forcefield.")
@Since("1.2.1-b1")
@Examples("")
@RequiredPlugins("Citizens")
public class CondCitizenHasForcefield extends Condition {
    static {
        Skript.registerCondition(CondCitizenHasForcefield.class,
                "%npcs% (has|have) [a] forcefield",
                "%npcs% (do(n't| not|esn't|es not)) (has|have) [a] forcefield");
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
                        if (npc.hasTrait(ForcefieldTrait.class) == false) return false;
                    }else{
                        if (npc.hasTrait(ForcefieldTrait.class) == true) return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        if (pattern == 0) return "Citizen " + npcExpr.toString(event, debug) + " has a forcefield";
        else return "Citizen " + npcExpr.toString(event, debug) + " does not have a forcefield";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.npcExpr = (Expression<NPC>) exprs[0];
        this.pattern = matchedPattern;
        return true;
    }
}
