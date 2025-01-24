package au.nagasonic.skonic.elements.hitbox;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExprHitboxWidth extends SimplePropertyExpression<NPCHitbox, Number> {
    static {
        registerDefault(ExprHitboxWidth.class, Number.class, "hitbox width", "npchitbox");
    }
    @Override
    public @Nullable Number convert(NPCHitbox hitbox) {
        return hitbox.getWidth();
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
            NPCHitbox box = getExpr().getSingle(event);
            if (box != null){
                box.setWidth(width.floatValue());
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
        return "hitbox width";
    }
}
