package au.nagasonic.skonic.modules.citizens.elements.conditions;

import au.nagasonic.skonic.Skonic;
import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.SyntaxInfo;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

@Name("Citizen Is Paused")
@Description("Whether the navigator of a Citizens NPC is paused.")
@Since("1.2")
@Examples({"if all citizens are paused:", "\tunpause all citizens"})
@RequiredPlugins("Citizens")
public class CondCitizenIsPaused extends Condition {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.CONDITION,
            SyntaxInfo.builder(CondCitizenIsPaused.class)
                .addPatterns(
                    "(citizen|npc)[s] %npcs% (is|are) paused",
                    "(citizen|npc)[s] %npcs% (isn't|is not|aren't|are not) paused"
                )
                .build()
        );
    }
    private Expression<NPC> npcExpr;
    private int pattern;
    @Override
    public boolean check(Event event) {
        if (npcExpr != null){
            NPC[] npcs = npcExpr.getArray(event);
            if (npcs != null){
                if (pattern == 0){
                    for (NPC npc : npcs) {
                        if (npc.getNavigator().isPaused() ==  false) return false;
                    }
                }else{
                    for (NPC npc : npcs) {
                        if (npc.getNavigator().isPaused() ==  true) return false;
                    }
                }
                return true;
            }else Skonic.log(Level.SEVERE, "No npcs were specified.");
        }else Skonic.log(Level.SEVERE, "No npcs were specified.");
        return false;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        if (pattern == 0) { return npcExpr.toString(event, debug) + " is paused"; }
        else { return npcExpr.toString(event, debug) + " isn't paused"; }
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        pattern = matchedPattern;
        return true;
    }
}
