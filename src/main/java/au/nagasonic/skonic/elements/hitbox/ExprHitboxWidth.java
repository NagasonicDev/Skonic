package au.nagasonic.skonic.elements.hitbox;

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

@Name("Citizen Hitbox Width")
@Description("The width of a Citizen Hitbox.")
@Since("1.2.2-b1")
@Examples("set hitbox width of {_hitbox} to 5")
@RequiredPlugins("Citizens")
public class ExprHitboxWidth extends SimplePropertyExpression<NPCHitbox, Number> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
                SyntaxRegistry.EXPRESSION,
                DefaultSyntaxInfos.Expression.builder(ExprHitboxWidth.class, Number.class)
                        .addPatterns("hitbox width", "npchitbox")
                        .build()
        );
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
