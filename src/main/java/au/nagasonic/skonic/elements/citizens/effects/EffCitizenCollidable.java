package au.nagasonic.skonic.elements.citizens.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Make Citizen Collidable")
@Description("Changes whether a Citizens NPC can be collided with by entities or fluids.")
@Since("1.2.1")
@RequiredPlugins("Citizens")
@Examples("make npc with id 3 not pushable by fluids")
public class EffCitizenCollidable extends Effect {
    static {
        Skript.registerEffect(EffCitizenCollidable.class,
                "make %npc% [not:not] (collidable|pushable) [f:(with|by) fluids]");
    }
    private Expression<NPC> npcExpr;
    private boolean not;
    private boolean f;

    @Override
    protected void execute(Event event) {
        NPC npc = npcExpr.getSingle(event);
        if (npc != null){
            if (f == true){
                npc.data().setPersistent(NPC.Metadata.FLUID_PUSHABLE, !not);
            }else{
                npc.data().setPersistent(NPC.Metadata.COLLIDABLE, !not);
            }
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        String f = this.f == true ? " by fluids": "";
        String n = this.not == true ? " not": "";
        return "make " + npcExpr.toString(event, debug) + n + " collidable" + f;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.npcExpr = (Expression<NPC>) exprs[0];
        this.not = parseResult.hasTag("not");
        this.f = parseResult.hasTag("f");
        return true;
    }
}
