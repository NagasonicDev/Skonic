package au.nagasonic.skonic.modules.citizens.elements.expressions;

import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;

@Name("Citizen Attributes")
@Description("The attributes of a Citizens NPC as a string.")
@RequiredPlugins("Citizens")
@Since("1.3")
@Examples("set {_attr} to attributes of {_npc}")
public class ExprCitizenAttributes extends SimplePropertyExpression<NPC, String> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprCitizenAttributes.class, String.class)
                .addPatterns(
                    "(citizen|npc) attributes of %npcs%",
                    "%npcs%'[s] (citizen|npc) attributes"
                )
                .build()
        );
    }
    @Override
    public @Nullable String convert(NPC npc) {
        if (npc.isSpawned() && npc.getEntity() instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) npc.getEntity();
            StringBuilder sb = new StringBuilder();
            for (Attribute attr : Attribute.values()) {
                if (living.getAttribute(attr) != null) {
                    sb.append(attr.name()).append("=").append(living.getAttribute(attr).getBaseValue()).append(" ");
                }
            }
            return sb.toString().trim();
        }
        return null;
    }

    @Override
    @NotNull
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    @NotNull
    protected String getPropertyName() {
        return "citizen attributes";
    }
}
