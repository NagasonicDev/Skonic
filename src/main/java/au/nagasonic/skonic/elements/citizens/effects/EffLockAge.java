package au.nagasonic.skonic.elements.citizens.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.AsyncEffect;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.Age;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Lock Citizen Age")
@Description("Stops the aging process of a Citizens NPC")
@RequiredPlugins("Citizens")
@Since("1.2.3-b1")
@Examples({"lock all npcs age", "unlock the age of all citizens"})
public class EffLockAge extends AsyncEffect {
    static {
        Skript.registerEffect(EffLockAge.class,
                "[n:un]lock %npcs%['s] age",
                "[n:un]lock [the] age of %npcs%");
    }
    private Expression<NPC> npcsExpr;
    private boolean un;
    @Override
    protected void execute(Event event) {
        if (npcsExpr != null){
            NPC[] npcs = npcsExpr.getArray(event);
            if (npcs != null){
                for (NPC npc : npcs){
                    if (npc != null){
                        npc.getOrAddTrait(Age.class).setLocked(!un);
                    }
                }
            }
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return (un ? "unlock" : "lock") + "age of " + npcsExpr.toString(event, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.npcsExpr = (Expression<NPC>) exprs[0];
        this.un = parseResult.hasTag("n");
        return true;
    }
}
