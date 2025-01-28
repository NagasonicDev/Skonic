package au.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Target")
@Description("The target of a Citizens NPC. Setting is done via the Citizen Attack effect.")
@Since("1.2.2")
@Examples({"make {_npc} attack player", "broadcast npc target of {_npc}"})
@RequiredPlugins("Citizens")
public class ExprCitizenTarget extends SimplePropertyExpression<NPC, Entity> {
    static {
        register(ExprCitizenTarget.class, Entity.class, "(citizen|npc) target", "npc");
    }
    @Override
    public @Nullable Entity convert(NPC npc) {
        return npc.getNavigator().getEntityTarget().getTarget();
    }

    @Override
    protected String getPropertyName() {
        return "citizen target";
    }

    @Override
    public Class<? extends Entity> getReturnType() {
        return Entity.class;
    }
}
