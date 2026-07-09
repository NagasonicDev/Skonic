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

@Name("Citizen Reset Pitch on Tick")
@Description("Whether a Citizens NPC resets its pitch on tick.")
@RequiredPlugins("Citizens")
@Since("1.3")
@Examples("set reset pitch of {_npc} to true")
public class ExprCitizenRotateSettings extends SimplePropertyExpression<NPC, Boolean> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprCitizenRotateSettings.class, Boolean.class)
                .addPatterns(
                    "(citizen|npc) reset pitch [on tick] of %npcs%",
                    "%npcs%'[s] (citizen|npc) reset pitch [on tick]"
                )
                .build()
        );
    }
    @Override
    public @Nullable Boolean convert(NPC npc) {
        return npc.data().get(NPC.Metadata.RESET_PITCH_ON_TICK, false);
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
            boolean reset = (Boolean) delta[0];
            for (NPC npc : getExpr().getArray(event)) {
                npc.data().setPersistent(NPC.Metadata.RESET_PITCH_ON_TICK, reset);
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
        return "citizen reset pitch on tick";
    }
}
