package ca.nagasonic.skonic.elements.citizens.effects;

import ca.nagasonic.skonic.Skonic;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

@Name("Delete Citizen")
@Description("Destroy a citizen by id.")
@Since("1.0.0")
@Examples("")
@RequiredPlugins("Citizens")
public class EffDeleteCitizen extends Effect {
    static {
        Skript.registerEffect(EffDeleteCitizen.class,
                "delete citizen %number%");
    }

    private Expression<Number> id;

    @Override
    protected void execute(Event e) {
        if (id != null){
            NPC npc = CitizensAPI.getNPCRegistry().getById(id.getSingle(e).intValue());
            if (npc != null){
                if (npc.getOwningRegistry() != null){
                    npc.destroy();
                }else Skonic.log(Level.SEVERE, "The citizen has no Owning Registry");
            }else Skonic.log(Level.SEVERE, "There is no citizen with id " + id.getSingle(e));
        }else Skonic.log(Level.SEVERE, "Specified ID is null");
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "delete citizen with id " + id.getSingle(e);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        id = (Expression<Number>) exprs[0];
        return true;
    }
}
