package au.nagasonic.skonic.elements.forcefield;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.ForcefieldTrait;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Forcefield")
@Description("Creates a forcefield around the NPCs with the specified width, height, strength and vertical strength.")
@Since("1.2.1-b1")
@RequiredPlugins("Citizens")
@Examples({"make all npcs have a forcefield with 10 width and 5 height and with 6 strength and 6 vert strength",
"wait 1 minute",
"remove forcefield of all npcs"})
public class EffCitizenForcefield extends Effect {
    static {
        Skript.registerEffect(EffCitizenForcefield.class,
                "make %npcs% have %npcforcefield%",
                "remove forcefield of %npcs%",
                "remove %npcs%['s] forcefield");
    }
    private Expression<NPC> npcExpr;
    private Expression<NPCForcefield> forcefieldExpr;
    private int pattern;

    @Override
    protected void execute(Event event) {
        NPC[] npcs = npcExpr.getArray(event);
        if (npcs != null){
            for (NPC npc : npcs){
                if (npc != null){
                    if (pattern == 0){
                        NPCForcefield forcefield = this.forcefieldExpr.getSingle(event);
                        if (forcefield != null){
                            forcefield.setForcefield(npc);
                        }
                    }else{
                        npc.removeTrait(ForcefieldTrait.class);
                    }
                }
            }
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        if (pattern == 0){ return "make " + npcExpr.toString(event, debug) + " have forcefield " + forcefieldExpr.toString(event, debug); }
        else return "remove forcefield of " + npcExpr.toString(event, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.npcExpr = (Expression<NPC>) exprs[0];
        if (matchedPattern == 0){
            this.forcefieldExpr = (Expression<NPCForcefield>) exprs[1];
        }
        this.pattern = matchedPattern;
        return true;
    }
}
