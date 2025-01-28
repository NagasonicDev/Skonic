package au.nagasonic.skonic.elements.citizens.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.ScoreboardTrait;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Start/Stop Glowing")
@Description("Makes a Citizens NPC start or stop glowing, with color.")
@Since("1.2.2-b1")
@RequiredPlugins("Citizens")
@Examples("make all npcs start glowing with red")
public class EffCitizenGlow extends Effect {
    static {
        Skript.registerEffect(EffCitizenGlow.class,
                "make %npcs% (start|:stop) glowing [c:with colo[u]r %-chatcolor%]",
                "(start|:stop) %npcs% [from] glowing [c:with colo[u]r %-chatcolor%]");
    }
    private Expression<NPC> npcsExpr;
    private Expression<ChatColor> colorExpr;
    private boolean not;
    private boolean c;
    @Override
    protected void execute(Event event) {
        NPC[] npcs = npcsExpr.getArray(event);
        if (npcs != null){
            for (NPC npc : npcs){
                if (npc != null){
                    if (c == true){
                        ChatColor color = colorExpr.getSingle(event);
                        if (color != null){
                            npc.getOrAddTrait(ScoreboardTrait.class).setColor(colorExpr.getSingle(event));
                        }
                    }
                    npc.data().setPersistent(NPC.Metadata.GLOWING, not);
                }
            }
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        String messsage = not == false ? "make " + npcsExpr.toString(event, debug) + " stop glowing" : "make " + npcsExpr.toString(event, debug) + " start glowing";
        return messsage;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.npcsExpr = (Expression<NPC>) exprs[0];
        this.colorExpr = (Expression<ChatColor>) exprs[1];
        this.not = !parseResult.hasTag("stop");
        this.c = parseResult.hasTag("c");
        return true;
    }
}
