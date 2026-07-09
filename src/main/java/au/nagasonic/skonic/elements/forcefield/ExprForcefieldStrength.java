package au.nagasonic.skonic.elements.forcefield;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ch.njol.skript.Skript;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;
import org.skriptlang.skript.registration.SyntaxRegistry;

@Name("Citizen Forcefield Strength")
@Description("The strength of a forcefield")
@Since("1.2.1-b1")
@RequiredPlugins("Citizens")
@Examples("set forcefield strength of {_forcefield} to 4")
public class ExprForcefieldStrength extends SimplePropertyExpression<NPCForcefield, Number> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
                SyntaxRegistry.EXPRESSION,
                DefaultSyntaxInfos.Expression.builder(ExprForcefieldStrength.class, Number.class)
                        .addPatterns("forcefield strength", "npcforcefield")
                        .build()
        );
    }
    @Override
    public @Nullable Number convert(NPCForcefield forcefield) {
        return forcefield.getStrength();
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
            Number strength = (Number) delta[0];
            NPCForcefield field = getExpr().getSingle(event);
            if (field != null){
                field.setStrength(strength.doubleValue());
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
        return "forcefield strength";
    }
}
