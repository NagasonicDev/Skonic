package au.nagasonic.skonic.elements.citizens.effects;

import au.nagasonic.skonic.Skonic;
import au.nagasonic.skonic.elements.util.Util;
import ch.njol.skript.Skript;
import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.SyntaxInfo;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.AsyncEffect;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

@Name("Citizen Attack")
@Description("Make a citizen attack an entity" +
        "Will also stop any pathfinding.")
@Since("1.0.0")
@Examples({"make npcs all citizens attack player", "wait 5 seconds", "stop citizens all citizens from attacking player"})
@RequiredPlugins("Citizens")
public class EffCitizenAttack extends Effect {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EFFECT,
            SyntaxInfo.builder(EffCitizenAttack.class)
                .addPatterns(
                    "make (npc|citizen)[s] %npcs% (attack|fight) %entity%",
                    "stop (npc|citizen)[s] %npcs% from (attacking|fighting) %entity%"
                )
                .build()
        );
    }

    private int patterns;
    private Expression<NPC> npcExpr;
    private Expression<Entity> victim;

    @Override
    protected void execute(Event e) {
        NPC[] npcs = npcExpr.getArray(e);
        //Check if ID is null
        if (npcs != null){
            for (NPC npc : npcs){
                //Check if there is a citizen with the ID
                if (npc != null){
                    if (patterns == 0){
                        if (npc.isSpawned()){
                            npc.getNavigator().setTarget(victim.getSingle(e), true);
                            Util.log("Made npc: " + npc.toString() + "attack player");
                        }
                    }else if (patterns == 1){
                        if (npc.isSpawned()){
                            npc.getNavigator().setPaused(true);
                        }
                    }else{
                        Skonic.log(Level.SEVERE, "Pattern was entered incorrectly.");
                    }
                }else Skonic.log(Level.SEVERE, "There is no npc " + npc.toString());
            }
        }else Skonic.log(Level.SEVERE, "The Specified ID is null");

    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        if (patterns == 0){
            return "make citizens " + npcExpr.toString(e, debug) + " attack entity " + victim.toString(e, debug);
        }else{
            return "make citizens " + npcExpr.toString(e, debug) + " stop attacking entity " + victim.toString(e, debug);
        }

    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        patterns = matchedPattern;
        npcExpr = (Expression<NPC>) exprs[0];
        victim = (Expression<Entity>) exprs[1];
        return true;
    }
}
