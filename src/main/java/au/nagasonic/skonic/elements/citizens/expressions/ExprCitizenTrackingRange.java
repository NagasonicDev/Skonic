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
@Name("Citizen Tracking Range")
@Description("The tracking range of a Citizens NPC.")
@RequiredPlugins("Citizens")
@Since("1.3")
@Examples("set tracking range of {_npc} to 32")
public class ExprCitizenTrackingRange extends SimplePropertyExpression<NPC, Number> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprCitizenTrackingRange.class, Number.class)
                .addPatterns(
                    "(citizen|npc) tracking range of %npcs%",
                    "%npcs%'[s] (citizen|npc) tracking range"
                )
                .build()
        );
    }
    @Override
    public @Nullable Number convert(NPC npc) {
        return npc.data().get(NPC.Metadata.TRACKING_RANGE);
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
            Number range = (Number) delta[0];
            for (NPC npc : getExpr().getArray(event)) {
                npc.data().setPersistent(NPC.Metadata.TRACKING_RANGE, range);
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
        return "citizen tracking range";
    }
}
