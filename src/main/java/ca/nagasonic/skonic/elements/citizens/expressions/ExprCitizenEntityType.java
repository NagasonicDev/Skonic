package ca.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Entity Type")
@Description("Get the entity type of a citizen")
@RequiredPlugins("Citizens")
@Since("1.0.5")
@Examples("")
@DocumentationId("citizen.entitytype")
public class ExprCitizenEntityType extends SimplePropertyExpression<NPC, EntityType> {
    static {
        register(ExprCitizenEntityType.class,
                EntityType.class,
                "entity type",
                "npc");
    }


    @Override
    protected String getPropertyName() {
        return "entity type";
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
