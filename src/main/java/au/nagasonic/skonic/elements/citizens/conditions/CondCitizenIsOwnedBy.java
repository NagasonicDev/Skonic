package au.nagasonic.skonic.elements.citizens.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Owner;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Citizen is Owned By")
@Description("Returns if a Citizens NPC is owned by a specific player.")
@Since("1.2.8")
@RequiredPlugins("Citizens")
public class CondCitizenIsOwnedBy extends Condition {
    static {
        Skript.registerCondition(CondCitizenIsOwnedBy.class,
                "(citizen|npc)[s] %npcs% is owned by %player%",
                "(citizen|npc)[s] %npcs% (is(n't| not)) owned by %player%");
    }
    private Expression<NPC> npcExpr;
    private Expression<Player> playerExpr;
    private int pattern;
    @Override
    public boolean check(Event event) {
        NPC[] npcs = npcExpr.getArray(event);
        Player player = playerExpr.getSingle(event);
        if (player == null) return false;
        if (npcs != null){
            for (NPC npc : npcs){
                if (npc != null){
                    Owner trait = npc.getOrAddTrait(Owner.class);
                    if (pattern == 0){
                        if (trait.isOwnedBy(player.getUniqueId()) == false) return false;
                    }else{
                        if (trait.isOwnedBy(player.getUniqueId()) == true) return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        if (pattern == 0) return "Citizen " + npcExpr.toString(event, debug) + " is owned by " + playerExpr.toString(event, debug);
        else return "Citizen " + npcExpr.toString(event, debug) + " is not owned by " + playerExpr.toString(event, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.npcExpr = (Expression<NPC>) exprs[0];
        this.playerExpr = (Expression<Player>) exprs[1];
        this.pattern = matchedPattern;
        return true;
    }
}
