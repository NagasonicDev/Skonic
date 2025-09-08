package au.nagasonic.skonic.elements.citizens.conditions;

import au.nagasonic.skonic.Skonic;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

@Name("Citizen Uses Minecraft AI")
@Description("Whether a Citizens NPC is using Minecraft's built-in AI or Citizens AI.")
@Since("1.2.3")
@RequiredPlugins("Citizens")
@Examples("")
public class CondCitizenUsesMCAI extends Condition {
    static {
        Skript.registerCondition(CondCitizenUsesMCAI.class,
                "(citizen|npc)[s] %npcs% (use[s]|(is|are) using) (minecraft|normal) (ai|AI)",
                "(citizen|npc)[s] %npcs% (is(n't| not) using|are(n't| not) using|do(n't| not) use) (minecraft|normal) (ai|AI)");
    }
    private Expression<NPC> npcExpr;
    private int pattern;
    @Override
    public boolean check(Event event) {
        if (npcExpr == null) {
            Skonic.log(Level.SEVERE, "NPC is null");
            return false;
        }
        NPC[] npcs = npcExpr.getArray(event);
        if (npcs != null) {
            for (NPC npc : npcs) {
                if (npc != null){
                    if (pattern == 0) {
                        if (npc.useMinecraftAI() == false) return false;
                    } else {
                        if (npc.useMinecraftAI() == true) return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return npcExpr.toString(event, b) + " uses minecraft ai";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        pattern = i;
        return true;
    }
}
