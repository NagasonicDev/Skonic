package ca.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.Nullable;

public class ExprCitizenEntityType extends SimplePropertyExpression<NPC, EntityType> {
    static {
        register(ExprCitizenEntityType.class,
                EntityType.class,
                "(citizen|npc) entity type",
                "npc");
    }


    @Override
    protected String getPropertyName() {
        return "citizen entity type";
    }

    @Override
    public @Nullable EntityType convert(NPC npc) {
        if (npc != null){
            return npc.getEntity().getType();
        }else{
            return null;
        }
    }

    @Override
    public Class<? extends EntityType> getReturnType() {
        return EntityType.class;
    }
}
