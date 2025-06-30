package au.nagasonic.skonic.elements.citizens.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.ScoreboardTrait;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Is Glowing")
@Description("Checks whether a Citizens NPC is glowing.")
@Since("1.2.2-b1")
@Examples({"if {_npc} is glowing:", "\tbroadcast npc glow colour of {_npc}"})
@RequiredPlugins("Citizens")
public class CondCitizenIsGlowing extends Condition {
    static {
        Skript.registerCondition(CondCitizenIsGlowing.class,
                "%npcs% (is|are) glowing [c:%chatcolor%]",
                "%npcs% (is(n't| not)|are(n't| not)) glowing [c:%chatcolor%]");
    }
    private Expression<NPC> npcExpr;
    private Expression<ChatColor> colorExpr;
    private boolean c;
    private int pattern;
    @Override
    public boolean check(Event event) {
        NPC[] npcs = npcExpr.getArray(event);
        if (npcs != null){
            for (NPC npc : npcs){
                if (npc != null){
                    if (pattern == 0){
                        if (npc.data().get(NPC.Metadata.GLOWING, false) == false) return false;
                        if (c){
                            ChatColor color = colorExpr.getSingle(event);
                            if (color == null) return false;
                            if (npc.getOrAddTrait(ScoreboardTrait.class).getColor() != color) return false;
                        }
                    }else{
                        if (npc.data().get(NPC.Metadata.GLOWING, true) == true) return false;
                        else if (c && npc.data().get(NPC.Metadata.GLOWING, true) == true){
                            ChatColor color = colorExpr.getSingle(event);
                            if (color == null) return false;
                            if (npc.getOrAddTrait(ScoreboardTrait.class).getColor() == color) return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        if (pattern == 0) return "Citizen " + npcExpr.toString(event, debug) + " are glowing";
        else return "Citizen " + npcExpr.toString(event, debug) + " aren't glowing";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.npcExpr = (Expression<NPC>) exprs[0];
        this.colorExpr = (Expression<ChatColor>) exprs[1];
        this.pattern = matchedPattern;
        this.c = parseResult.hasTag("c");
        return true;
    }
}
