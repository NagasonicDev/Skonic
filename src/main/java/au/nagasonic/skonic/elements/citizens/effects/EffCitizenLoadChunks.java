package au.nagasonic.skonic.elements.citizens.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Make Citizen Load Chunks")
@Description({"Changes whether a citizen will load chunks.", "Temporarily means that it won't continue after server is restarted."})
@Since("1.2.1")
@RequiredPlugins("Citizens")
@Examples("make npc with id 4 able to load chunks")
public class EffCitizenLoadChunks extends Effect {
    static {
        Skript.registerEffect(EffCitizenLoadChunks.class,
                "make %npc% [not:un]able to load chunks [t:temporarily]");
    }
    private Expression<NPC> npcExpr;
    private boolean not;
    private boolean t;
    @Override
    protected void execute(Event event) {
        if (npcExpr != null){
            NPC npc = npcExpr.getSingle(event);
            if (npc != null){
                if (t == true){
                    npc.data().set(NPC.Metadata.KEEP_CHUNK_LOADED, !not);
                }else{
                    npc.data().setPersistent(NPC.Metadata.KEEP_CHUNK_LOADED, !not);
                }
            }
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        String n = this.not == true ? " un": " ";
        String t = this.t == true ? " temporarily": "";
        return "make " + npcExpr.toString(event, debug) + n + "able to load chunks" + t;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.npcExpr = (Expression<NPC>) exprs[0];
        this.not = parseResult.hasTag("not");
        this.t = parseResult.hasTag("t");
        return true;
    }
}
