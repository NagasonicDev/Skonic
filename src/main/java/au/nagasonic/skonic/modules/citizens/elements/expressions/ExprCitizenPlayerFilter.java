package au.nagasonic.skonic.modules.citizens.elements.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.PlayerFilter;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;

@Name("Citizen Player Filter")
@Description("Whether a Citizens NPC has a PlayerFilter trait.")
@RequiredPlugins("Citizens")
@Since("1.3")
@Examples("set player filter of {_npc} to true")
public class ExprCitizenPlayerFilter extends SimplePropertyExpression<NPC, Boolean> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprCitizenPlayerFilter.class, Boolean.class)
                .addPatterns(
                    "(citizen|npc) player filter of %npcs%",
                    "%npcs%'[s] (citizen|npc) player filter"
                )
                .build()
        );
    }
    @Override
    public @Nullable Boolean convert(NPC npc) {
        return npc.hasTrait(PlayerFilter.class);
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
            boolean hasFilter = (Boolean) delta[0];
            for (NPC npc : getExpr().getArray(event)) {
                if (hasFilter) {
                    npc.addTrait(PlayerFilter.class);
                } else {
                    npc.removeTrait(PlayerFilter.class);
                }
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
        return "citizen player filter";
    }
}
