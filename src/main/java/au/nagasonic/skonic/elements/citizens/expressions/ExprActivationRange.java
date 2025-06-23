package au.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Activation Range")
@Description("The activation range of a citizen.")
@RequiredPlugins("Citizens")
@Since("1.2.3-b1")
@Examples("set npc activation range of {_npc} to 7")
public class ExprActivationRange extends SimplePropertyExpression<NPC, Number> {
    static {
        register(ExprActivationRange.class, Number.class, "(citizen|npc) activation range", "npcs");
    }
    @Override
    public @Nullable Number convert(NPC npc) {
        return npc.data().get(NPC.Metadata.ACTIVATION_RANGE);
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
                npc.data().setPersistent(NPC.Metadata.ACTIVATION_RANGE, range);
            }
        }
        if (delta == null){
            for (NPC npc : getExpr().getArray(event)) {
                npc.data().remove(NPC.Metadata.ACTIVATION_RANGE);
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
        return "citizen activation range";
    }
}
