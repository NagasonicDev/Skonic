package au.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import net.citizensnpcs.api.npc.NPC;
import org.jetbrains.annotations.Nullable;

@Name("Citizen ID")
@Description("Get the ID of a citizen")
@RequiredPlugins("Citizens")
@Since("1.0.5")
@Examples({"broadcast all citizens", "loop all citizens:", "\tbroadcast id of loop-value"})
@DocumentationId("12495")
public class ExprCitizenID extends SimplePropertyExpression<NPC, Number> {
    static {
        register(ExprCitizenID.class, Number.class,
                "id",
                "npc");
    }
    @Override
    protected String getPropertyName() {
        return "id";
    }

    @Override
    public @Nullable Number convert(NPC npc) {
        if (npc == null) return null;
        return npc.getId();
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }
}
