package ca.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprCitizenID extends SimpleExpression<Number> {
    static {
        Skript.registerExpression(ExprCitizenID.class,
                Number.class,
                ExpressionType.COMBINED,
                "%npc%['s] id");
    }
    private Expression<NPC> npc;

    @Override
    protected @Nullable Number[] get(Event e) {
        if (npc != null && npc.getSingle(e) != null){
            if ((Integer) npc.getSingle(e).getId() == null) return null;
            return new Number[]{npc.getSingle(e).getId()};
        }else return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "id of " + npc.getSingle(e).toString();
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        npc = (Expression<NPC>) exprs[0];
        return true;
    }
}
