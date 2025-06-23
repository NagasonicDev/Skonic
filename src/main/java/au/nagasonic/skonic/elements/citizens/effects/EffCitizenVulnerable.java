package au.nagasonic.skonic.elements.citizens.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.AsyncEffect;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Make Citizen Vulnerable")
@Description("Changes whether a Citizens NPC can be attacked or not.")
@Examples({"on citizen damage:", "\tmake event-npc invulnerable", "\twait 5 seconds", "\tmake event-npc protected"})
@Since("1.2")
@RequiredPlugins("Citizens")
public class EffCitizenVulnerable extends AsyncEffect {
    static {
        Skript.registerEffect(EffCitizenVulnerable.class,
                "make (citizen|npc) %npcs% [not:in]vulnerable",
                "make (citizen|npc) %npcs% [not:un]protected");
    }
    private Expression<NPC> npcsExpr;
    private int pattern;
    private boolean not;
    @Override
    protected void execute(Event event) {
        if (npcsExpr != null){
            NPC[] npcs = npcsExpr.getArray(event);
            if (npcs != null){
                for (NPC npc : npcs){
                    if (npc != null){
                        if (pattern == 0){
                            npc.setProtected(not);
                        }else{
                            npc.setProtected(!not);
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        if (pattern == 0){
            return "make "+ npcsExpr.toString(event, debug) + (not ? " invulnerable" : " vulnerable");

        }else{
            return "make "+ npcsExpr.toString(event, debug) + (not ? " protected" : " unprotected");
        }
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        npcsExpr = (Expression<NPC>) exprs[0];
        pattern = matchedPattern;
        not = parseResult.hasTag("not");
        return true;
    }
}
