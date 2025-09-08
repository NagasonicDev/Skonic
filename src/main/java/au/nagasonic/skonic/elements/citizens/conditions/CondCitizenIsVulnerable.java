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

@Name("Citizen Is Vulnerable")
@Description("Returns whether the Citizen can be attacked or not.")
@Examples({"on citizen right click:",
        "\tif event-npc is vulnerable:",
        "\t\tsend \"Please don't hit me.\" to event-player"})
@Since("1.1")
@RequiredPlugins("Citizens")
public class CondCitizenIsVulnerable extends Condition {
    static {
        Skript.registerCondition(CondCitizenIsVulnerable.class,
                "(citizen|npc)[s] %npcs% (is|are) vulnerable",
                "(citizen|npc)[s] %npcs% (is|are) protected");
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
                        if (!npc.isProtected() == false) return false;
                    } else {
                        if (!npc.isProtected() == true) return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return npcExpr.toString(event, b) + "is vulnerable";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        pattern = i;
        return true;
    }
}
