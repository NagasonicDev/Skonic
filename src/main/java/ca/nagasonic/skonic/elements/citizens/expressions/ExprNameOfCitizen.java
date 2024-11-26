package ca.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprNameOfCitizen extends SimpleExpression<String> {
    static {
        Skript.registerExpression(ExprNameOfCitizen.class,
                String.class,
                ExpressionType.COMBINED,
                "[the] name of (citizen|npc) %number%");
    }

    private Expression<Number> id;

    @Override
    protected @Nullable String[] get(Event e) {
        if (id != null && id.getSingle(e) != null){
            NPC npc = CitizensAPI.getNPCRegistry().getById(id.getSingle(e).intValue());
            if (npc != null){
                return new String[]{npc.getName()};
            }else return null;
        }else return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "name of citizen with id " + id.getSingle(e).toString();
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        id = (Expression<Number>) exprs[0];
        return true;
    }
}
