package ca.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.LookClose;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Look Close")
@Description({"The look close trait of the citizen.", "Whether the citizen should or should not look at the closest player."})
@Examples("")
@Since("1.1")
@DocumentationId("12496")
@RequiredPlugins("Citizens")
public class ExprLookClose extends SimplePropertyExpression<NPC, Boolean> {
    static {
        register(ExprLookClose.class, Boolean.class, "look close [trait]", "npcs");
    }
    @SuppressWarnings("NullableProblems")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        return super.init(exprs, matchedPattern, isDelayed, parseResult);
    }

    @Override
    public @Nullable Boolean convert(NPC npc) {
        return npc.hasTrait(LookClose.class);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public @Nullable Class<?>[] acceptChange(ChangeMode mode) {
        if (mode == ChangeMode.SET) return CollectionUtils.array(Boolean.class);
        return null;
    }

    @SuppressWarnings({"NullableProblems", "ConstantValue"})
    @Override
    public void change(Event event, @Nullable Object[] delta, ChangeMode mode) {
        if (delta != null && delta[0] instanceof Boolean) {
            Boolean look = (Boolean) delta[0];
            for (NPC npc : getExpr().getArray(event)) {
                LookClose close = npc.getOrAddTrait(LookClose.class);
                close.lookClose(look);
            }
        }
    }

    @Override
    @NotNull
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    @NotNull
    protected String getPropertyName() {
        return "citizen look close trait";
    }
}
