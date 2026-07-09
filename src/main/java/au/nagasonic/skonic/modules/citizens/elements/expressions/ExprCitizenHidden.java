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

@Name("Citizen Hidden")
@Description({"Whether a Citizens NPC is hidden from players.", "Uses the 'hidden' metadata."})
@RequiredPlugins("Citizens")
@Since("1.3")
@Examples("set hidden state of {_npc} to true")
public class ExprCitizenHidden extends SimplePropertyExpression<NPC, Boolean> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprCitizenHidden.class, Boolean.class)
                .addPatterns(
                    "(citizen|npc) hidden [state] of %npcs%",
                    "%npcs%'[s] (citizen|npc) hidden [state]"
                )
                .build()
        );
    }
    @Override
    public @Nullable Boolean convert(NPC npc) {
        return npc.data().get("hidden", false);
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
            boolean hidden = (Boolean) delta[0];
            for (NPC npc : getExpr().getArray(event)) {
                npc.data().setPersistent("hidden", hidden);
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
        return "citizen hidden state";
    }
}
