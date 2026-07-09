package au.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.MirrorTrait;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;

@Name("Citizen Cosmetic Entity")
@Description("Whether the cosmetic entity (mirror) trait is enabled on a Citizens NPC.")
@RequiredPlugins("Citizens")
@Since("1.3")
@Examples("set cosmetic entity of {_npc} to true")
public class ExprCitizenCosmeticEntity extends SimplePropertyExpression<NPC, Boolean> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprCitizenCosmeticEntity.class, Boolean.class)
                .addPatterns(
                    "(citizen|npc) cosmetic entity of %npcs%",
                    "%npcs%'[s] (citizen|npc) cosmetic entity"
                )
                .build()
        );
    }
    @Override
    public @Nullable Boolean convert(NPC npc) {
        if (npc.hasTrait(MirrorTrait.class)) {
            return npc.getOrAddTrait(MirrorTrait.class).isEnabled();
        }
        return false;
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
            boolean enabled = (Boolean) delta[0];
            for (NPC npc : getExpr().getArray(event)) {
                npc.getOrAddTrait(MirrorTrait.class).setEnabled(enabled);
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
        return "citizen cosmetic entity";
    }
}
