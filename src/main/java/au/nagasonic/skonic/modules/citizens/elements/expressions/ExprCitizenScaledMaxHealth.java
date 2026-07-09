package au.nagasonic.skonic.modules.citizens.elements.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;
@Name("Citizen Scaled Max Health")
@Description("The scaled max health of a Citizens NPC.")
@RequiredPlugins("Citizens")
@Since("1.3")
@Examples("set scaled max health of {_npc} to 40")
public class ExprCitizenScaledMaxHealth extends SimplePropertyExpression<NPC, Double> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprCitizenScaledMaxHealth.class, Double.class)
                .addPatterns(
                    "(citizen|npc) scaled max health of %npcs%",
                    "%npcs%'[s] (citizen|npc) scaled max health"
                )
                .build()
        );
    }
    @Override
    public @Nullable Double convert(NPC npc) {
        if (npc.isSpawned() && npc.getEntity() instanceof LivingEntity) {
            return ((LivingEntity) npc.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        }
        return null;
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
            double health = ((Number) delta[0]).doubleValue();
            for (NPC npc : getExpr().getArray(event)) {
                if (npc.isSpawned() && npc.getEntity() instanceof LivingEntity) {
                    ((LivingEntity) npc.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
                }
            }
        }
    }

    @Override
    @NotNull
    public Class<? extends Double> getReturnType() {
        return Double.class;
    }

    @Override
    @NotNull
    protected String getPropertyName() {
        return "citizen scaled max health";
    }
}
