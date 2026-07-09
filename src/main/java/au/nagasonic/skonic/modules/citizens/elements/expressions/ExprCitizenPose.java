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

@Name("Citizen Pose")
@Description("The pose of a Citizens NPC")
@Since("1.2.1")
@RequiredPlugins("Citizens")
@Examples("set npc entity pose of last spawned citizen to crouching")
public class ExprCitizenPose extends SimplePropertyExpression<NPC, EntityPoseTrait.EntityPose> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprCitizenPose.class, EntityPoseTrait.EntityPose.class)
                .addPatterns(
                    "(citizen|npc) [entity] pose of %npcs%",
                    "%npcs%'[s] (citizen|npc) [entity] pose"
                )
                .build()
        );
    }
    @Override
    public @Nullable EntityPoseTrait.EntityPose convert(NPC npc) {
        return npc.getOrAddTrait(EntityPoseTrait.class).getPose();
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) return CollectionUtils.array(EntityPoseTrait.EntityPose.class);
        return null;
    }

    @SuppressWarnings({"NullableProblems", "ConstantValue"})
    @Override
    public void change(Event event, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta != null && delta[0] instanceof EntityPoseTrait.EntityPose) {
            EntityPoseTrait.EntityPose pose = (EntityPoseTrait.EntityPose) delta[0];
            for (NPC npc : getExpr().getArray(event)) {
                npc.getOrAddTrait(EntityPoseTrait.class).setPose(pose);
            }
        }
    }

    @Override
    @NotNull
    public Class<? extends EntityPoseTrait.EntityPose> getReturnType() {
        return EntityPoseTrait.EntityPose.class;
    }

    @Override
    @NotNull
    protected String getPropertyName() {
        return "citizen entity pose";
    }
}
