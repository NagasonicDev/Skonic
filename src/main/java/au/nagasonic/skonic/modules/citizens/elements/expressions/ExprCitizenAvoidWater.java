package au.nagasonic.skonic.modules.citizens.elements.expressions;

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

@Name("Citizen Avoid Water")
@Description("Whether a Citizens NPC avoids water when pathfinding.")
@RequiredPlugins("Citizens")
@Since("1.3")
@Examples("set avoid water of {_npc} to true")
public class ExprCitizenAvoidWater extends SimplePropertyExpression<NPC, Boolean> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprCitizenAvoidWater.class, Boolean.class)
                .addPatterns(
                    "(citizen|npc) avoid water of %npcs%",
                    "%npcs%'[s] (citizen|npc) avoid water"
                )
                .build()
        );
    }
    @Override
    public @Nullable Boolean convert(NPC npc) {
        return npc.getNavigator().getDefaultParameters().avoidWater();
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) return CollectionUtils.array(Boolean.class);
        return null;
    }

    @SuppressWarnings({"NullableProblems", "ConstantValue"})
    @Override
    public void change(Event event, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta != null && delta[0] instanceof Boolean) {
            boolean avoid = (Boolean) delta[0];
            for (NPC npc : getExpr().getArray(event)) {
                npc.getNavigator().getDefaultParameters().avoidWater(avoid);
            }
        }
    }

    @Override
    @NotNull
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    @NotNull
    protected String getPropertyName() {
        return "citizen avoid water";
    }
}
