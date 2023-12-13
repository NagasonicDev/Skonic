package ca.nagasonic.skonic.elements.citizens.effects;

import ca.nagasonic.skonic.Skonic;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Direction;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.event.DespawnReason;
import net.citizensnpcs.api.event.SpawnReason;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

public class EffDespawnCitizen extends Effect {
    static {
        Skript.registerEffect(EffDespawnCitizen.class, "[(citizen|npc)] despawn %npc%",
                "[(citizen|npc)] respawn %npc% at %direction% %location%");
    }

    private int pattern;
    private Expression<NPC> npc;
    private Expression<Location> loc;

    @Override
    protected void execute(Event e) {
        //Check if citizen is null
        if (npc.getSingle(e) != null){
            //Check if location is null
            if (loc.getSingle(e) != null && loc != null){
                Location location = loc.getSingle(e);
                for (NPC npcs : npc.getArray(e)) {
                    if (this.pattern == 0) {
                        npcs.despawn(DespawnReason.PLUGIN);
                    } else {
                        npcs.spawn(location, SpawnReason.PLUGIN);
                    }
                }
            }else Skonic.log(Level.SEVERE, "Specified location is null");
        }else Skonic.log(Level.SEVERE, "Specified citizen is null");
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "despawn " + npc.getSingle(e).toString();
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        pattern = matchedPattern;
        npc = (Expression<NPC>) exprs[0];
        if (matchedPattern == 1) {
            loc = Direction.combine((Expression<? extends Direction>) exprs[1], (Expression<? extends Location>) exprs[2]);
        }
        return true;
    }
}
