package ca.nagasonic.skonic.elements.citizens.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Direction;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.event.DespawnReason;
import net.citizensnpcs.api.event.SpawnReason;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Despawn/Respawn Citizen")
@Description("Despawn or respawn a citizen.")
@RequiredPlugins("Citizens")
@Since("1.0.5")
@Examples("")
@DocumentationId("12487")
public class EffDespawnCitizen extends Effect {
    static {
        Skript.registerEffect(EffDespawnCitizen.class,
                "[citizens|npc] despawn %npcs%",
                "[citizens|npc] respawn %npcs% %direction% %location%");
    }

    private int pattern;
    private Expression<NPC> npcs;
    private Expression<Location> loc;

    @Override
    protected void execute(Event e) {
        Location location = this.loc != null ? this.loc.getSingle(e) : null;
        for (NPC npc : this.npcs.getArray(e)) {
            if (this.pattern == 0) {
                npc.despawn(DespawnReason.PLUGIN);
            } else if (location != null) {
                npc.spawn(location, SpawnReason.PLUGIN);
            }
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "despawn " + npcs.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        pattern = matchedPattern;
        if (pattern == 1) {
            loc = Direction.combine((Expression<? extends Direction>) exprs[1], (Expression<? extends Location>) exprs[2]);
        }
        npcs = (Expression<NPC>) exprs[0];
        return true;
    }
}
