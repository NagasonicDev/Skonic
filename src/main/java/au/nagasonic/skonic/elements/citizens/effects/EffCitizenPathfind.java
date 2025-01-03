package au.nagasonic.skonic.elements.citizens.effects;

import au.nagasonic.skonic.Skonic;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

@Name("Citizen Pathfind")
@Description("Makes the citizen attempt to pathfind to the given location." +
        "If that location is linked to an entity, it will not move with the entity.")
@RequiredPlugins("Citizens")
@Since("1.0.7")
@Examples("make all npcs pathfind to player")
public class EffCitizenPathfind extends Effect {
    static {
        Skript.registerEffect(EffCitizenPathfind.class,
                "make %npcs% pathfind to %location%");
    }
    private Expression<NPC> npcExpr;
    private Expression<Location> locExpr;
    @Override
    protected void execute(Event e) {
        Location loc = locExpr.getSingle(e);
        NPC[] npcs = npcExpr.getArray(e);
        if (loc != null && npcs != null){
            for (NPC npc : npcs){
                if (npc != null){
                    npc.getNavigator().setTarget(loc);
                }else Skonic.log(Level.SEVERE, "NPC cannot be null");
            }
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "make citizens " + npcExpr.toString(e, b) + " pathfind to " + locExpr.toString(e, b);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        locExpr = (Expression<Location>) exprs[1];
        return true;
    }
}
