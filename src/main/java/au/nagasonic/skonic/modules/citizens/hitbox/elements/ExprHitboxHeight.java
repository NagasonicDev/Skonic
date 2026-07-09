package au.nagasonic.skonic.modules.citizens.hitbox.elements;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;
import org.skriptlang.skript.registration.SyntaxRegistry;

@Name("Citizen Hitbox Height")
@Description("The height of a Citizen hitbox.")
@Examples("set hitbox height of {_box} to 3")
@Since("1.2.2-b1")
@RequiredPlugins("Citizens")
public class ExprHitboxHeight extends SimplePropertyExpression<NPCHitbox, Number> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
                SyntaxRegistry.EXPRESSION,
                DefaultSyntaxInfos.Expression.builder(ExprHitboxHeight.class, Number.class)
                        .addPatterns("hitbox height", "npchitbox")
                        .build()
        );
    }
    @Override
    public @Nullable Number convert(NPCHitbox hitbox) {
        return hitbox.getHeight();
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
            NPCHitbox box = getExpr().getSingle(event);
            if (box != null){
                box.setHeight(height.floatValue());
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
        return "hitbox height";
    }
}
