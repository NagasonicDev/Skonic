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
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

@Name("Set Citizen Skin - Name")
@Description("Set a citizen's skin by name." +
        "Only works if citizen is a player.")
@RequiredPlugins("Citizens")
@Since("1.0.0")
@Examples("")
public class EffChangeCitizenSkinName extends Effect {
    static {
        Skript.registerEffect(EffChangeCitizenSkinName.class, "(set|change) (citizen|npc) %number% skin to %string%");
    }

    private Expression<Number> id;
    private Expression<String> name;

    @Override
    protected void execute(Event e) {
        //Check if the ID is null
        if (id.getSingle(e) != null) {
            NPC npc = CitizensAPI.getNPCRegistry().getById(id.getSingle(e).intValue());
            //Check if there is a citizen with the ID
            if (npc != null){
                SkinTrait trait = npc.getOrAddTrait(SkinTrait.class);
                trait.setShouldUpdateSkins(true);
                //Check if the name is null
                if (name.getSingle(e) != null){
                    trait.setSkinName(name.getSingle(e));
                }else Skonic.log(Level.SEVERE, "The specified name is null.");
            }else Skonic.log(Level.SEVERE, "There is no citizen with ID " + id.getSingle(e).toString());
        }else Skonic.log(Level.SEVERE, "Specified ID is null");
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "change skin of citizen with id " + id.getSingle(e) + " to name " + name.getSingle(e);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        id = (Expression<Number>) exprs[0];
        name = (Expression<String>) exprs[1];
        return true;
    }
}
