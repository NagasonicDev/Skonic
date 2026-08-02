package au.nagasonic.skonic.modules.citizens.forcefield.elements;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;
import org.skriptlang.skript.registration.SyntaxRegistry;

@Name("Citizen Forcefield Height")
@Description("The height of a forcefield.")
@Since("1.2.1-b1")
@RequiredPlugins("Citizens")
@Examples("set npc with id 2's npc forcefield height to 3")
public class ExprForcefieldHeight extends SimplePropertyExpression<NPCForcefield, Number> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
                SyntaxRegistry.EXPRESSION,
                DefaultSyntaxInfos.Expression.builder(ExprForcefieldHeight.class, Number.class)
                        .addPatterns("forcefield height[ of %npcforcefield%]", "%npcforcefield%'[s] forcefield height")
                        .build()
        );
    }
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (exprs.length == 0) {
            EventValueExpression<NPCForcefield> eventValue = new EventValueExpression<>(NPCForcefield.class);
            if (!eventValue.init()) return false;
            setExpr(eventValue);
            return true;
        }
        setExpr((Expression<? extends NPCForcefield>) exprs[0]);
        return true;
    }
    @Override
    public @Nullable Number convert(NPCForcefield forcefield) {
        return forcefield.getHeight();
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) return CollectionUtils.array(Number.class);
        return null;
    }

    @SuppressWarnings({"NullableProblems", "ConstantValue"})
    @Override
    public void change(Event event, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta != null && delta[0] instanceof Number) {
            Number height = (Number) delta[0];
            NPCForcefield field = getExpr().getSingle(event);
            if (field != null){
                field.setHeight(height.doubleValue());
            }
        }
    }

    @Override
    @NotNull
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    @NotNull
    protected String getPropertyName() {
        return "forcefield height";
    }
}
