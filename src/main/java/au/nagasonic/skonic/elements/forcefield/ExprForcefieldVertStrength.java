package au.nagasonic.skonic.elements.forcefield;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Forcefield Vertical Strength")
@Description("The vertical strength of a Citizen NPC's forcefield.")
@Since("1.2.1-b1")
@RequiredPlugins("Citizens")
@Examples("set citizen forcefield vertical strength of all npcs to 10")
public class ExprForcefieldVertStrength extends SimplePropertyExpression<Forcefield, Number> {
    static {
        registerDefault(ExprForcefieldVertStrength.class, Number.class, "forcefield vertical strength", "npcforcefield");
    }
    @Override
    public @Nullable Number convert(Forcefield forcefield) {
        return forcefield.vertStrength;
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
            Number vertStrength = (Number) delta[0];
            Forcefield forcefield = getExpr().getSingle(event);
            if (forcefield != null){
                forcefield.setVertStrength(vertStrength.doubleValue());
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
        return "citizen forcefield vertical strength";
    }
}
