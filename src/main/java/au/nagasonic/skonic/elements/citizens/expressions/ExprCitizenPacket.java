package au.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;

@Name("Citizen Packet Update Delay")
@Description("The packet update delay of a Citizens NPC.")
@RequiredPlugins("Citizens")
@Since("1.3")
@Examples("set packet update delay of {_npc} to 10")
public class ExprCitizenPacket extends SimplePropertyExpression<NPC, Number> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprCitizenPacket.class, Number.class)
                .addPatterns(
                    "(citizen|npc) packet [update] delay of %npcs%",
                    "%npcs%'[s] (citizen|npc) packet [update] delay"
                )
                .build()
        );
    }
    @Override
    public @Nullable Number convert(NPC npc) {
        return npc.data().get(NPC.Metadata.PACKET_UPDATE_DELAY);
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
            Number delay = (Number) delta[0];
            for (NPC npc : getExpr().getArray(event)) {
                npc.data().setPersistent(NPC.Metadata.PACKET_UPDATE_DELAY, delay);
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
        return "citizen packet update delay";
    }
}
