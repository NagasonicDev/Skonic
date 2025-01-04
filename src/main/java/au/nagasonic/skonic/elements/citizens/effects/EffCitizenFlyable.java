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

@Name("Make Citizen Flyable")
@Description({"Makes a Citizens NPC flyable.", "Some entity types are always flyable (e.g bat), therefore will always be flyable."})
@Since("1.2.1")
@RequiredPlugins("Citizens")
@Examples({"make npc with id 3 flyable",
"wait 4 seconds",
"make npc with id 3 unflyable"})
public class EffCitizenFlyable extends Effect {
    static {
        Skript.registerEffect(EffCitizenFlyable.class,
                "make %npc% [not:not |un]flyable");
    }
    private Expression<NPC> npcExpr;
    private boolean not;
    @Override
    protected void execute(Event event) {
        NPC npc = npcExpr.getSingle(event);
        if (npc != null) {
            npc.setFlyable(!not);
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        String n = this.not == true ? " un": " ";
        return "make " + npcExpr.toString(event, debug) + n + "flyable";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.npcExpr = (Expression<NPC>) exprs[0];
        this.not = parseResult.hasTag("not");
        return true;
    }
}
