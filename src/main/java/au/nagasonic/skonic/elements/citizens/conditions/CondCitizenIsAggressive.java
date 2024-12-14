package au.nagasonic.skonic.elements.citizens.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.util.NMS;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Is Aggressive")
@Description("Returns the bool of whether the citizen is aggressive.")
@RequiredPlugins("Citizens")
@Examples({"if npc with id 3 is aggressive:",
        "\tbroadcast \"Npc is Aggressive\""})
@Since("1.2")
@DocumentationId("")
public class CondCitizenIsAggressive extends Condition {
    static {
        Skript.registerCondition(CondCitizenIsAggressive.class,
                "%npcs% (is|are) aggressive",
                "%npcs% (is(n't| not)|are(n't| not)) aggressive");
    }
    private Expression<NPC> npcExpr;
    private int pattern;
    @Override
    public boolean check(Event e) {
        NPC[] npcs = npcExpr.getArray(e);
        for (NPC npc : npcs){
            if (npc != null){
                if (pattern == 0){
                    if (npc.data().has(NPC.Metadata.AGGRESSIVE) == false) return false;
                    return true;
                }else{
                    if (npc.data().has(NPC.Metadata.AGGRESSIVE) == true) return false;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "Citizen " + npcExpr.toString(e, b) + " aren't aggressive.";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        this.pattern = pattern;
        return true;
    }
}
