package au.nagasonic.skonic.elements.citizens.expressions;

import au.nagasonic.skonic.Skonic;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

@Name("Citizen With ID")
@Description("Gets the citizen with the given ID")
@Examples("set {_npc} to citizen with id 2")
@Since("1.0.7")
@RequiredPlugins("Citizens")
@DocumentationId("12499")
public class ExprCitizenWith extends SimpleExpression<NPC> {
    static {
        Skript.registerExpression(ExprCitizenWith.class, NPC.class, ExpressionType.COMBINED,
                "(citizen|npc) with id %number%");
    }
    private Expression<Number> idExpr;
    @Override
    protected @Nullable NPC[] get(Event e) {
        Integer id = idExpr.getSingle(e).intValue();
        if (id != null){
            NPC npc = CitizensAPI.getNPCRegistry().getById(id);
            if (npc != null){
                return new NPC[]{npc};
            }else{
                Skonic.log(Level.SEVERE, "There is no Citizen with ID " + id);
            }
        }else {
            Skonic.log(Level.SEVERE, "ID cannot be null.");
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends NPC> getReturnType() {
        return NPC.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "citizen with id " + idExpr.toString(e, b);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        idExpr = (Expression<Number>) exprs[0];
        return true;
    }
}
