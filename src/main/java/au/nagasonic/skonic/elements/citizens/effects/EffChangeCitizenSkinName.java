package au.nagasonic.skonic.elements.citizens.effects;

import au.nagasonic.skonic.Skonic;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.AsyncEffect;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

@Name("Set Citizen Skin - Name")
@Description("Set a citizen's skin by name." +
        "Only works if citizen is a player.")
@RequiredPlugins("Citizens")
@Since("1.0.0")
@Examples("set skin of last spawned npc to \"Nagasonic\"")
public class EffChangeCitizenSkinName extends AsyncEffect {
    static {
        Skript.registerEffect(EffChangeCitizenSkinName.class,
                "(set|change) %npcs%['s] skin to %string%",
                "(set|change) skin of %npcs% to %string%");
    }

    private Expression<NPC> npcExpr;
    private Expression<String> name;

    @Override
    protected void execute(Event e) {
        //Check if the ID is null
        NPC[] npcs = npcExpr.getArray(e);
        if (npcs != null) {
            //Check if there is a citizen with the ID
            for (NPC npc : npcs){
                if (npc != null){
                    SkinTrait trait = npc.getOrAddTrait(SkinTrait.class);
                    trait.setShouldUpdateSkins(true);
                    //Check if the name is null
                    if (name.getSingle(e) != null){
                        trait.setSkinName(name.getSingle(e));
                    }else Skonic.log(Level.SEVERE, "The specified name is null.");
                }else Skonic.log(Level.SEVERE, "There is no citizen " + npc.toString());
            }
        }else Skonic.log(Level.SEVERE, "Specified NPCs are null");
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "change skin of citizen with id " + npcExpr.toString(e, debug) + " to name " + name.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        name = (Expression<String>) exprs[1];
        return true;
    }
}
