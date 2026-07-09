package au.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;
@Name("Citizen Speed")
@Description("The navigation speed of a Citizens NPC.")
@RequiredPlugins("Citizens")
@Since("1.3")
@Examples("set npc speed of {_npc} to 0.5")
public class ExprCitizenSpeed extends SimplePropertyExpression<NPC, Float> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprCitizenSpeed.class, Float.class)
                .addPatterns(
                    "(citizen|npc) speed of %npcs%",
                    "%npcs%'[s] (citizen|npc) speed"
                )
                .build()
        );
    }
    @Override
    public @Nullable Float convert(NPC npc) {
        return npc.getNavigator().getDefaultParameters().speed();
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
            float speed = ((Number) delta[0]).floatValue();
            for (NPC npc : getExpr().getArray(event)) {
                npc.getNavigator().getDefaultParameters().speed(speed);
            }
        }
    }

    @Override
    @NotNull
    public Class<? extends Float> getReturnType() {
        return Float.class;
    }

    @Override
    @NotNull
    protected String getPropertyName() {
        return "citizen speed";
    }
}
