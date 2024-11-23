package ca.nagasonic.skonic.elements.citizens.effects;

import ca.nagasonic.skonic.Skonic;
import ch.njol.skript.Skript;
import ch.njol.skript.bukkitutil.EntityUtils;
import ch.njol.skript.doc.*;
import ch.njol.skript.entity.EntityData;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Direction;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

@Name("Spawn Citizen")
@Description({"Spawn a customisable citizen with:",
        "* Name",
        "* Direction",
        "* Location",
        "* Entity Type"})
@RequiredPlugins("Citizens")
@Examples("")
@Since("1.0.0")
public class EffSpawnCitizen extends Effect {

    public static NPC lastSpawnedNPC;

    static {
        Skript.registerEffect(EffSpawnCitizen.class,
                "(spawn|create) [a[n]] [%-entitydata%] citizen [named %string%] [at|%direction%] %location%");
    }

    private Expression<String> name;
    private Expression<Location> location;
    private Expression<EntityData<?>> type;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
        type = (Expression<EntityData<?>>) exprs[0];
        name = (Expression<String>) exprs[1];
        location = Direction.combine((Expression<? extends Direction>) exprs[2],
                (Expression<? extends Location>) exprs[3]);
        return true;
    }

    @Override
    public String toString(@Nullable Event evt, boolean arg1) {
        if (type != null){
            return "Created NPC named: " + name.toString(evt, arg1) + " Location: "
                    + location.toString(evt, arg1) + " Type:" + type.toString();
        }else{
            return "Created NPC named: " + name.toString(evt, arg1) + " Location: "
                    + location.toString(evt, arg1) + " Type: Player";
        }
    }

    @Override
    public void execute(Event evt) {
        if (location == null || location.getSingle(evt) == null) Skonic.log(Level.SEVERE, "The specified location is null");
        EntityType CitizenType = EntityType.PLAYER;
        if (type != null){
            EntityData<?> data = type.getSingle(evt);
            if (data != null) CitizenType = EntityUtils.toBukkitEntityType(data);
        }
        String citizenName = (this.name != null && this.name.getSingle(evt) != null) ? this.name.getSingle(evt) : "";
        Location location = this.location.getSingle(evt);
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(CitizenType, name.getSingle(evt), location);
        lastSpawnedNPC = npc;
    }


}
