package au.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.EntityPoseTrait;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Pose")
@Description("The pose of a Citizens NPC")
@Since("1.2.1")
@RequiredPlugins("Citizens")
@Examples("set npc entity pose of last spawned citizen to crouching")
public class ExprCitizenPose extends SimplePropertyExpression<NPC, EntityPoseTrait.EntityPose> {
    static {
        register(ExprCitizenPose.class, EntityPoseTrait.EntityPose.class, "(citizen|npc) [entity] pose", "npcs");
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
