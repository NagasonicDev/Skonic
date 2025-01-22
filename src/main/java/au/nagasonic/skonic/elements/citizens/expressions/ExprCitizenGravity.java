package au.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.BoundingBoxTrait;
import net.citizensnpcs.trait.Gravity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Gravity")
@Description("Whether the Citizens NPC is affected by gravity")
@RequiredPlugins("Citizens")
@Since("1.2.2")
@Examples("set the npc gravity of all npcs to true")
public class ExprCitizenGravity extends SimplePropertyExpression<NPC, Boolean> {
    static {
        register(ExprCitizenGravity.class, Boolean.class, "(citizen|npc) gravity", "npcs");
    }
    @Override
    public @Nullable Boolean convert(NPC npc) {
        return npc.getOrAddTrait(Gravity.class).hasGravity();
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
            Boolean hasGravity = (Boolean) delta[0];
            for (NPC npc : getExpr().getArray(event)) {
                npc.getOrAddTrait(Gravity.class).setHasGravity(hasGravity);
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
        return "citizen gravity";
    }
}
