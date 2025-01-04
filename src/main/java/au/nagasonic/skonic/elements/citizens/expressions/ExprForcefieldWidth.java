package au.nagasonic.skonic.elements.citizens.expressions;

import au.nagasonic.skonic.elements.citizens.Forcefield;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Forcefield Width")
@Description("The width of a Citizen NPC's forcefield.")
@Since("1.2.1")
@RequiredPlugins("Citizens")
@Examples("broadcast npc forcefield width of npc with id 3")
public class ExprForcefieldWidth extends SimplePropertyExpression<Forcefield, Number> {
    static {
        registerDefault(ExprForcefieldWidth.class, Number.class, "forcefield width", "npcforcefield");
    }
    @Override
    public @Nullable Number convert(Forcefield forcefield) {
        return forcefield.width;
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
            Number width = (Number) delta[0];
            Forcefield forcefield = getExpr().getSingle(event);
            if (forcefield != null){
                forcefield.setWidth(width.doubleValue());
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
        return "citizen forcefield width";
    }
}
