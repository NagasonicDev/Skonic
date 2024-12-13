package au.nagasonic.skonic.elements.citizens.effects;

import au.nagasonic.skonic.Skonic;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.TargetableTrait;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

@Name("Make Citizen Targetable")
@Description({"Make a citizen targetable to other mobs.", "Or you can stop this process."})
@Examples({"make npc with id 2 targetable", "make npc with id 3 not targetable"})
@Since("1.2")
@RequiredPlugins("Citizens")
@DocumentationId("")
public class EffCitizenTargetable extends Effect {
    static {
        Skript.registerEffect(EffCitizenTargetable.class,
                "make %npc% targetable",
                "make %npc% not targetable");
    }
    private int pattern;
    private Expression<NPC> npcExpr;
    @Override
    protected void execute(Event event) {
        if (npcExpr != null){
            NPC npc = npcExpr.getSingle(event);
            if (npc != null){
                if (pattern == 0){
                    npc.getOrAddTrait(TargetableTrait.class).setTargetable(true);
                }else if (pattern == 1){
                    npc.getOrAddTrait(TargetableTrait.class).setTargetable(false);
                }else{
                    Skonic.log(Level.SEVERE, "Pattern was entered incorrectly.");
                }
            }else Skonic.log(Level.SEVERE, "NPC cannot be null");
        }else Skonic.log(Level.SEVERE, "NPC Expression cannot be null");
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        if (pattern == 0){
            return "make " + npcExpr.toString(event, b) + " targetable";
        }else return "make " + npcExpr.toString(event, b) + " not targetable";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        pattern = i;
        npcExpr = (Expression<NPC>) exprs[0];
        return true;
    }
}
