package au.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;

@Name("Citizen Rotation")
@Description("The rotation of a Citizens NPC (yaw).")
@RequiredPlugins("Citizens")
@Since("1.3")
@Examples("set rotation of {_npc} to 90")
public class ExprCitizenRotate extends SimplePropertyExpression<NPC, Float> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprCitizenRotate.class, Float.class)
                .addPatterns(
                    "(citizen|npc) rotation of %npcs%",
                    "%npcs%'[s] (citizen|npc) rotation"
                )
                .build()
        );
    }
    @Override
    public @Nullable Float convert(NPC npc) {
        if (npc.isSpawned() && npc.getEntity() != null) {
            return npc.getEntity().getLocation().getYaw();
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
            float yaw = ((Number) delta[0]).floatValue();
            for (NPC npc : getExpr().getArray(event)) {
                if (npc.isSpawned() && npc.getEntity() != null) {
                    npc.getEntity().setRotation(yaw, npc.getEntity().getLocation().getPitch());
                }
            }
        }
    }

    @Override
    @NotNull
    public Class<? extends Float> getReturnType() {
        return Float.class;
    }

    @Override
    @NotNull
    protected String getPropertyName() {
        return "citizen rotation";
    }
}
