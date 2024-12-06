package ca.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Name")
@Description("Get the name of a citizen")
@RequiredPlugins("Citizens")
@Since("1.0.5")
@Examples("")
@DocumentationId("citizen.name")
public class ExprNameOfCitizen extends SimpleExpression<String> {
    static {
        Skript.registerExpression(ExprNameOfCitizen.class,
                String.class,
                ExpressionType.COMBINED,
                "[the] name of %npc%");
    }

    private Expression<NPC> npcExpr;

    @Override
    protected @Nullable String[] get(Event e) {
        NPC npc = npcExpr.getSingle(e);
        if (npcExpr != null){
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
        return "name of citizen with id " + npcExpr.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        return true;
    }
}
