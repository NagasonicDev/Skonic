package au.nagasonic.skonic.modules.citizens.elements.conditions;

import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.SyntaxInfo;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Is Flyable")
@Description("Whether a Citizens NPC is flyable.")
@Since("1.2.1-b1")
@Examples("")
@RequiredPlugins("Citizens")
public class CondCitizenIsFlyable extends Condition {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.CONDITION,
            SyntaxInfo.builder(CondCitizenIsFlyable.class)
                .addPatterns(
                    "(citizen|npc)[s] %npcs% (is|are|can) fly[able]",
                    "(citizen|npc)[s] %npcs% (is(n't| not)|are(n't| not)|can('t|not| not)) fly[able]"
                )
                .build()
        );
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
                    }else{
                        if (npc.isFlyable() == true) return false;
                    }
                }
            }
            return true;
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
