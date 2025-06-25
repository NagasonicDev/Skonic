package au.nagasonic.skonic.elements.citizens.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.AsyncEffect;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Make Citizen Use Minecraft AI")
@Description("Changes whether a Citizens NPC uses Minecraft's built-in AI or Citizens AI.")
@RequiredPlugins("Citizens")
@Since("1.2.3")
@Examples("")
public class EffCitizenMinecraftAI extends AsyncEffect {
    static {
        Skript.registerEffect(EffCitizenMinecraftAI.class,
                "(make|set) %npcs% [to] use (minecraft|normal) (ai|AI)",
                "(make|set) %npcs% [to] not use (minecraft|normal) (ai|AI)");
    }
    private Expression<NPC> npcsExpr;
    private int pattern;
    @Override
    protected void execute(Event event) {
        if (npcsExpr != null){
            NPC[] npcs = npcsExpr.getArray(event);
            if (npcs != null){
                for (NPC npc : npcs){
                    if (pattern == 1){
                        npc.setUseMinecraftAI(false);
                    }else{
                        npc.setUseMinecraftAI(true);
                    }
                }
            }
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "make " + npcsExpr.toString(event, debug) + (pattern == 0 ? " " : " not ") + "use minecraft ai";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.npcsExpr = (Expression<NPC>) exprs[0];
        this.pattern = matchedPattern;
        return true;
    }
}
