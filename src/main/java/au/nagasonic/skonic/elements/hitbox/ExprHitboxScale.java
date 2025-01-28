package au.nagasonic.skonic.elements.hitbox;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Hitbox Scale")
@Description("The scale/multiplier of a Citizen Hitbox.")
@Since("1.2.2-b1")
@Examples({"set {_box} to a hitbox:", "\tset hitbox width to 1", "\tset hitbox height to 2", "set hitbox scale of {_box} to 2   #Effective size becomes width: 2, height: 4"})
@RequiredPlugins("Citizens")
public class ExprHitboxScale extends SimplePropertyExpression<NPCHitbox, Number> {
    static {
        registerDefault(ExprHitboxScale.class, Number.class, "hitbox scale", "npchitbox");
    }
    @Override
    public @Nullable Number convert(NPCHitbox hitbox) {
        return hitbox.getScale();
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
            Number scale = (Number) delta[0];
            NPCHitbox box = getExpr().getSingle(event);
            if (box != null){
                box.setScale(scale.floatValue());
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
        return "hitbox scale";
    }
}
