package au.nagasonic.skonic.elements.citizens.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Make Citizen Vulnerable")
@Description("Changes whether a Citizens NPC can be attacked or not.")
@Examples({"on citizen damage:", "\tmake event-npc invulnerable", "\twait 5 seconds", "\tmake event-npc protected"})
@Since("1.2")
@RequiredPlugins("Citizens")
public class EffCitizenVulnerable extends Effect {
    static {
        Skript.registerEffect(EffCitizenVulnerable.class,
                "make %npcs% [not:in]vulnerable",
                "make %npcs% [not:un]protected");
    }
    private Expression<NPC> npcsExpr;
    private int pattern;
    private ParseResult parseResult;
    private boolean protect;
    @Override
    protected void execute(Event event) {
        if (npcsExpr != null){
            NPC[] npcs = npcsExpr.getArray(event);
            if (npcs != null){
                for (NPC npc : npcs){
                    npc.setProtected(protect);
                }
            }
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        if (pattern == 0){
            return "make "+ npcsExpr.toString(event, debug) + (protect ? " invulnerable" : " vulnerable");

        }else{
            return "make "+ npcsExpr.toString(event, debug) + (protect ? " protected" : " unprotected");
        }
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        npcsExpr = (Expression<NPC>) exprs[0];
        pattern = matchedPattern;
        this.parseResult = parseResult;
        protect = matchedPattern == 0 ^ parseResult.hasTag("not");
        return true;
    }
}
