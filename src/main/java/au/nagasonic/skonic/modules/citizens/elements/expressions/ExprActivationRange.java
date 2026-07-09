package au.nagasonic.skonic.modules.citizens.elements.expressions;

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

@Name("Citizen Activation Range")
@Description("The activation range of a citizen.")
@RequiredPlugins("Citizens")
@Since("1.2.3-b1")
@Examples("set npc activation range of {_npc} to 7")
public class ExprActivationRange extends SimplePropertyExpression<NPC, Number> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprActivationRange.class, Number.class)
                .addPatterns(
                    "(citizen|npc) activation range of %npcs%",
                    "%npcs%'[s] (citizen|npc) activation range"
                )
                .build()
        );
    }
    @Override
    public @Nullable Number convert(NPC npc) {
        return npc.data().get(NPC.Metadata.ACTIVATION_RANGE);
    }

    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) return CollectionUtils.array(Number.class);
        return null;
    }

    @Override
    public void change(Event event, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta != null && delta[0] instanceof Number range) {
            for (NPC npc : getExpr().getArray(event)) {
                npc.data().setPersistent(NPC.Metadata.ACTIVATION_RANGE, range);
            }
        }
        if (delta == null){
            for (NPC npc : getExpr().getArray(event)) {
                npc.data().remove(NPC.Metadata.ACTIVATION_RANGE);
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
        return "citizen activation range";
    }
}
