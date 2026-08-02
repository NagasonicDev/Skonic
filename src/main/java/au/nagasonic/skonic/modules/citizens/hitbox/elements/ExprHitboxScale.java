package au.nagasonic.skonic.modules.citizens.hitbox.elements;

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

@Name("Citizen Hitbox Scale")
@Description("The scale/multiplier of a Citizen Hitbox.")
@Since("1.2.2-b1")
@Examples({"set {_box} to a hitbox:", "\tset hitbox width to 1", "\tset hitbox height to 2", "set hitbox scale of {_box} to 2   #Effective size becomes width: 2, height: 4"})
@RequiredPlugins("Citizens")
public class ExprHitboxScale extends SimplePropertyExpression<NPCHitbox, Number> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
                SyntaxRegistry.EXPRESSION,
                DefaultSyntaxInfos.Expression.builder(ExprHitboxScale.class, Number.class)
                        .addPatterns("hitbox scale[ of %npchitbox%]", "%npchitbox%'[s] hitbox scale")
                        .build()
        );
    }
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (exprs.length == 0) {
            EventValueExpression<NPCHitbox> eventValue = new EventValueExpression<>(NPCHitbox.class);
            if (!eventValue.init()) return false;
            setExpr(eventValue);
            return true;
        }
        setExpr((Expression<? extends NPCHitbox>) exprs[0]);
        return true;
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
