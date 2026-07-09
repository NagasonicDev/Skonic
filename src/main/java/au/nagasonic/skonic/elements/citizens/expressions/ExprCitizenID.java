package au.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import net.citizensnpcs.api.npc.NPC;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;

@Name("Citizen ID")
@Description("Get the ID of a citizen")
@RequiredPlugins("Citizens")
@Since("1.0.5")
@Examples({"broadcast all citizens", "loop all citizens:", "\tbroadcast id of loop-value"})
public class ExprCitizenID extends SimplePropertyExpression<NPC, Number> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprCitizenID.class, Number.class)
                .addPatterns(
                    "id of %npc%",
                    "%npc%'[s] id"
                )
                .build()
        );
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
