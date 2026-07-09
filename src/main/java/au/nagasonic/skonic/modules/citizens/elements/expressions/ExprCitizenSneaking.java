package au.nagasonic.skonic.modules.citizens.elements.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.EntityPoseTrait;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;
@Name("Citizen Sneaking")
@Description("Whether a Citizens NPC is sneaking.")
@RequiredPlugins("Citizens")
@Since("1.3")
@Examples("set sneaking state of {_npc} to true")
public class ExprCitizenSneaking extends SimplePropertyExpression<NPC, Boolean> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprCitizenSneaking.class, Boolean.class)
                .addPatterns(
                    "(citizen|npc) sneaking of %npcs%",
                    "%npcs%'[s] (citizen|npc) sneaking"
                )
                .build()
        );
    }
    @Override
    public @Nullable Boolean convert(NPC npc) {
        return npc.getOrAddTrait(EntityPoseTrait.class).getPose() == EntityPoseTrait.EntityPose.CROUCHING;
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
            boolean sneak = (Boolean) delta[0];
            for (NPC npc : getExpr().getArray(event)) {
                npc.getOrAddTrait(EntityPoseTrait.class).setPose(sneak ? EntityPoseTrait.EntityPose.CROUCHING : EntityPoseTrait.EntityPose.STANDING);
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
        return "citizen sneaking";
    }
}
