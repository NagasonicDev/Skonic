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

@Name("Citizen Pathfinding Range")
@Description("The pathfinding range of a Citizens NPC's navigator.")
@RequiredPlugins("Citizens")
@Since("1.3")
@Examples("set pathfinding range of {_npc} to 30")
public class ExprCitizenPathfindingRange extends SimplePropertyExpression<NPC, Float> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprCitizenPathfindingRange.class, Float.class)
                .addPatterns(
                    "(citizen|npc) pathfind[ing] range of %npcs%",
                    "%npcs%'[s] (citizen|npc) pathfind[ing] range"
                )
                .build()
        );
    }
    @Override
    public @Nullable Float convert(NPC npc) {
        return npc.getNavigator().getDefaultParameters().range();
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
            float range = ((Number) delta[0]).floatValue();
            for (NPC npc : getExpr().getArray(event)) {
                npc.getNavigator().getDefaultParameters().range(range);
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
        return "citizen pathfinding range";
    }
}
