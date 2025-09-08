package au.nagasonic.skonic.elements.citizens.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Can Load Chunks")
@Description("Whether a Citizens NPC can load chunks.")
@Since("1.2.1-b1")
@Examples("")
@RequiredPlugins("Citizens")
public class CondCitizenCanLoadChunks extends Condition {
    static {
        Skript.registerCondition(CondCitizenCanLoadChunks.class,
                "(citizen|npc)[s] %npcs% (can|is able to) load chunk[s]",
                "(citizen|npc)[s] %npcs% (can('t|not| not)|is('nt| not) able to|is unable to) load chunk[s]");
    }
    private Expression<NPC> npcExpr;
    private int pattern;
    @Override
    public boolean check(Event event) {
        NPC[] npcs = npcExpr.getArray(event);
        if (npcs != null){
            for (NPC npc : npcs){
                if (npc != null){
                    if (pattern == 0){
                        if (npc.data().get(NPC.Metadata.KEEP_CHUNK_LOADED, false) == false) return false;
                    }else{
                        if (npc.data().get(NPC.Metadata.KEEP_CHUNK_LOADED, false) == true) return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        if (pattern == 0) return "Citizen " + npcExpr.toString(event, debug) + " can load chunks";
        else return "Citizen " + npcExpr.toString(event, debug) + " can't load chunks";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.npcExpr = (Expression<NPC>) exprs[0];
        this.pattern = matchedPattern;
        return true;
    }
}
