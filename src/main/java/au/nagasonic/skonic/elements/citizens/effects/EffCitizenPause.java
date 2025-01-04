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

@Name("Pause Citizen")
@Description("Pauses or unpauses the citizens if they are navigating.")
@Since("1.2")
@Examples({"if all citizens are paused:", "\tunpause all citizens"})
@RequiredPlugins("Citizens")
public class EffCitizenPause extends Effect {
    static {
        Skript.registerEffect(EffCitizenPause.class,
                "[not:un]pause [the] [[citizen|npc] navigation] [of] %npcs%");
    }
    private Expression<NPC> npcExpr;
    private boolean pause;
    @Override
    protected void execute(Event event) {
        if (npcExpr != null){
            NPC[] npcs = npcExpr.getArray(event);
            if (npcs != null){
                for (NPC npc : npcs) {
                    npc.getNavigator().setPaused(!pause);
                }
            }

        }
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        pause = parseResult.hasTag("not");
        return true;
    }
}
