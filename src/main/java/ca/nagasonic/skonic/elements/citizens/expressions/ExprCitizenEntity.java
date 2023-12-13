package ca.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class ExprCitizenEntity extends SimplePropertyExpression<NPC, Entity> {
    static {
        register(ExprCitizenEntity.class, Entity.class, "(citizen|npc) entity", "npc");
    }


    @Override
    protected String getPropertyName() {
        return "citizen entity";
    }

    @Override
    public @Nullable Entity convert(NPC npc) {
        if (npc != null){
            return npc.getEntity();
        }else{
            return null;
        }
    }

    @Override
    public Class<? extends Entity> getReturnType() {
        return Entity.class;
    }
}
