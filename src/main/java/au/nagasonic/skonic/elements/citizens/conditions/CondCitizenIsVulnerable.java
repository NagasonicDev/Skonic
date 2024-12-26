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

@Name("Citizen Is Vulnerable")
@Description("Returns whether the Citizen can be attacked or not.")
@Examples({"on citizen right click:",
        "\tif event-npc is vulnerable:",
        "\t\tsend \"Please don't hit me.\" to event-player"})
@Since("1.1")
@RequiredPlugins("Citizens")
@DocumentationId("12483")
public class CondCitizenIsVulnerable extends Condition {
    static {
        Skript.registerCondition(CondCitizenIsVulnerable.class, "%npc% is vulnerable", "%npc% is protected");
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
        if (pattern == 0){
            return !npc.isProtected();
        }else{
            return npc.isProtected();
        }
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
