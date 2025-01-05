package au.nagasonic.skonic.elements.forcefield;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Forcefield Height")
@Description("The height of a forcefield.")
@Since("1.2.1")
@RequiredPlugins("Citizens")
@Examples("set npc with id 2's npc forcefield height to 3")
public class ExprForcefieldHeight extends SimplePropertyExpression<Forcefield, Number> {
    static {
        registerDefault(ExprForcefieldHeight.class, Number.class, "forcefield height", "npcforcefield");
    }
    @Override
    public @Nullable Number convert(Forcefield forcefield) {
        return forcefield.height;
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
            Forcefield field = getExpr().getSingle(event);
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
