package au.nagasonic.skonic.elements.citizens.effects;

import au.nagasonic.skonic.Skonic;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.util.NMS;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

@Name("Make Citizen Aggressive")
@Description("Make a Citizens NPC aggressive.")
@Since("1.1")
@RequiredPlugins("Citizens")
@Examples("make npc with id 1 aggressive")
@DocumentationId("")
public class EffCitizenAggressive extends Effect {
    static {
        Skript.registerEffect(EffCitizenAggressive.class,
                "make %npc% aggressive",
                "make %npc% passive");
    }
    private Expression<NPC> npcExpr;
    private int pattern;
    @Override
    protected void execute(Event event) {
        if (npcExpr != null){
            NPC npc = npcExpr.getSingle(event);
            if (npc != null){
                if (pattern == 0){
                    npc.data().set(NPC.Metadata.AGGRESSIVE, true);
                    NMS.setAggressive(npc.getEntity(), true);
                } else if (pattern == 1) {
                    npc.data().set(NPC.Metadata.AGGRESSIVE, false);
                    NMS.setAggressive(npc.getEntity(), false);
                }else{
                    Skonic.log(Level.SEVERE, "How? That shouldn't be possible.");
                }
            }else Skonic.log(Level.SEVERE, "Npc cannot be null");
        }else Skonic.log(Level.SEVERE, "NPC expression can't be null.");
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        if (pattern == 0){
            return "make " + npcExpr.toString(event, b) + " aggressive";
        }else{
            return "make " + npcExpr.toString(event, b) + " passive";
        }
    }

    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        pattern = i;
        return true;
    }
}
