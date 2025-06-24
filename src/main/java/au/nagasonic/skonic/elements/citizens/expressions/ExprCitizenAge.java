package au.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.Age;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Age")
@Description({"The age of a citizen.", "- Ordered between -24000 and 0. Any negative integer being a baby, and 0 being an adult."})
@RequiredPlugins("Citizens")
@Since("1.2.3-b1")
@Examples({"set {_age} to npc age of last created citizen", "# BABY", "set npc age of npc with id 2 to -24000"})
public class ExprCitizenAge extends SimplePropertyExpression<NPC, Number> {
    static {
        register(ExprCitizenAge.class, Number.class, "(citizen|npc) age", "npcs");
    }
    @Override
    public @Nullable Number convert(NPC npc) {
        return npc.getOrAddTrait(Age.class).getAge();
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
            Number age = (Number) delta[0];
            for (NPC npc : getExpr().getArray(event)) {
                Age trait = npc.getOrAddTrait(Age.class);
                trait.setAge(age.intValue());
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
        return "citizen age";
    }
}
