package au.nagasonic.skonic.elements.citizens.effects;

import au.nagasonic.skonic.Skonic;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Direction;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.world.LootGenerateEvent;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

@Name("Citizen Pathfind")
@Description("Makes the citizen attempt to pathfind to the given location." +
        "If that location is linked to an entity, it will not move with the entity.")
@RequiredPlugins("Citizens")
@Since("1.0.7, 1.2.2-b1 (straight line)")
@Examples("make all npcs pathfind to player")
public class EffCitizenPathfind extends Effect {
    static {
        Skript.registerEffect(EffCitizenPathfind.class,
                "make (citizen|npc) %npcs% (pathfind|move|walk) to[wards] %location% [s:in [a] [straight] line]");
    }
    private Expression<NPC> npcExpr;
    private Expression<Location> locExpr;
    private boolean s;
    @Override
    protected void execute(Event e) {
        Location loc = locExpr.getSingle(e);
        NPC[] npcs = npcExpr.getArray(e);
        if (loc != null && npcs != null){
            for (NPC npc : npcs){
                if (npc != null){
                    if (this.s == true) npc.getNavigator().setStraightLineTarget(loc);
                    else npc.getNavigator().setTarget(loc);
                }else Skonic.log(Level.SEVERE, "NPC cannot be null");
            }
        }else Skonic.log(Level.SEVERE, "Location or NPCS cannot be null.");
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "make citizens " + npcExpr.toString(e, b) + " pathfind to " + locExpr.toString(e, b);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        locExpr = (Expression<Location>) exprs[1];
        this.s = parseResult.hasTag("s");
        return true;
    }
}
