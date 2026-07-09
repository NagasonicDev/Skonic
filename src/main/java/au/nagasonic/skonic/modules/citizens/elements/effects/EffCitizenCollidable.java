package au.nagasonic.skonic.modules.citizens.elements.effects;

import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.SyntaxInfo;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.AsyncEffect;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Make Citizen Collidable")
@Description("Changes whether a Citizens NPC can be collided with by entities or fluids.")
@Since("1.2.1")
@RequiredPlugins("Citizens")
@Examples("make citizen npc with id 3 not pushable by fluids")
public class EffCitizenCollidable extends AsyncEffect {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EFFECT,
            SyntaxInfo.builder(EffCitizenCollidable.class)
                .addPatterns(
                    "make (npc|citizen) %npc% [not:not] (collidable|pushable) [f:(with|by) fluids]"
                )
                .build()
        );
    }
    private Expression<NPC> npcExpr;
    private boolean not;
    private boolean f;

    @Override
    protected void execute(Event event) {
        NPC npc = npcExpr.getSingle(event);
        if (npc != null){
            if (f){
                npc.data().setPersistent(NPC.Metadata.FLUID_PUSHABLE, !not);
            }else{
                npc.data().setPersistent(NPC.Metadata.COLLIDABLE, !not);
            }
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        String f = this.f ? " by fluids": "";
        String n = this.not ? " not": "";
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
