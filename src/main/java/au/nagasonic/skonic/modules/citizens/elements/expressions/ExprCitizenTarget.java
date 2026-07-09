package au.nagasonic.skonic.modules.citizens.elements.expressions;

import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;


import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;
@Name("Citizen Target")
@Description("The target of a Citizens NPC. Setting is done via the Citizen Attack effect.")
@Since("1.2.2")
@Examples({"make {_npc} attack player", "broadcast npc target of {_npc}"})
@RequiredPlugins("Citizens")
public class ExprCitizenTarget extends SimplePropertyExpression<NPC, Entity> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprCitizenTarget.class, Entity.class)
                .addPatterns(
                    "(citizen|npc) target of %npc%",
                    "%npc%'[s] (citizen|npc) target"
                )
                .build()
        );
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
