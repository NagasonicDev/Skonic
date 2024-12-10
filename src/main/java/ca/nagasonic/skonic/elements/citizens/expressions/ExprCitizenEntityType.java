package ca.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Entity Type")
@Description({"Get the entity type of a citizen", "For some entitytypes, you have to put \'minecraft:\' in front of it for it to work."})
@RequiredPlugins("Citizens")
@Since("1.0.5")
@Examples("")
@DocumentationId("citizen.entitytype")
public class ExprCitizenEntityType extends SimplePropertyExpression<NPC, EntityType> {
    static {
        register(ExprCitizenEntityType.class, EntityType.class, "(citizen|npc) entity type", "npcs");
    }
    @Override
    public @Nullable EntityType convert(NPC npc) {
        return npc.getEntity().getType();
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) return CollectionUtils.array(EntityType.class);
        return null;
    }

    @SuppressWarnings({"NullableProblems", "ConstantValue"})
    @Override
    public void change(Event event, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta != null && delta[0] instanceof EntityType) {
            EntityType type = (EntityType) delta[0];
            for (NPC npc : getExpr().getArray(event)) {
                npc.setBukkitEntityType(type);
            }
        }
    }

    @Override
    @NotNull
    public Class<? extends EntityType> getReturnType() {
        return EntityType.class;
    }

    @Override
    @NotNull
    protected String getPropertyName() {
        return "citizen entity type";
    }
}
