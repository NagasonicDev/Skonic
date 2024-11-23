package ca.nagasonic.skonic.elements.citizens.effects;

import ca.nagasonic.skonic.Skonic;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Direction;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.DespawnReason;
import net.citizensnpcs.api.event.SpawnReason;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

public class EffDespawnCitizen extends Effect {
    static {
        Skript.registerEffect(EffDespawnCitizen.class,
                "[(citizen|npc)] despawn %npc%",
                "(respawn|despawn) (citizen|npc) %number% at %direction% %location%");
    }

    private int pattern;
    private Expression<Number> id;
    private Expression<Location> loc;

    @Override
    protected void execute(Event e) {
        //Check if id is null
        if (id.getSingle(e) != null){
            //Check if location is null
            if (loc.getSingle(e) != null){
                Location location = loc.getSingle(e);
                NPC npc = CitizensAPI.getNPCRegistry().getById(id.getSingle(e).intValue());
                if (pattern == 1){
                    //Check if citizen is spawned
                    if (npc.isSpawned()) {
                        Skonic.log(Level.SEVERE, "Citizen is already spawned.");
                    }else {
                        npc.spawn(location, SpawnReason.PLUGIN);
                    }
                }else {
                    //Check if citizen isn't spawned
                    if (!npc.isSpawned()){
                        Skonic.log(Level.SEVERE, "Citizen is not spawned.");
                    }else{
                        npc.despawn(DespawnReason.PLUGIN);
                    }
                }
            }else Skonic.log(Level.SEVERE, "Specified location is null");
        }else Skonic.log(Level.SEVERE, "Specified citizen is null");
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "despawn " + id.getSingle(e).toString();
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        pattern = matchedPattern;
        id = (Expression<Number>) exprs[0];
        if (matchedPattern == 1) {
            loc = Direction.combine((Expression<? extends Direction>) exprs[1], (Expression<? extends Location>) exprs[2]);
        }
        return true;
    }
}
