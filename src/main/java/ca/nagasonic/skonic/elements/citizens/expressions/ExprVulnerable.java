package ca.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Vulnerability")
@Description({"Whether a Citizen can be attacked or not.", "Being Vulnerable also makes them collidable."})
@Since("1.1")
@RequiredPlugins("Citizens")
@Examples("")
@DocumentationId("12498")
public class ExprVulnerable extends SimplePropertyExpression<NPC, Boolean> {
    static {
        register(ExprVulnerable.class, Boolean.class, "vulnerability", "npcs");
    }
    @Override
    public @Nullable Boolean convert(NPC npc) {
        return npc.isProtected();
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
            Boolean vulnerable = (Boolean) delta[0];
            for (NPC npc : getExpr().getArray(event)) {
                npc.setProtected(!vulnerable);
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
        return "citizen vulnerability";
    }
}
