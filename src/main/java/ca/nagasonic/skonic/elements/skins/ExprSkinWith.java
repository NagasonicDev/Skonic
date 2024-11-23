package ca.nagasonic.skonic.elements.skins;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprSkinWith extends SimpleExpression<Skin> {
    static {
        Skript.registerExpression(ExprSkinWith.class, Skin.class, ExpressionType.COMBINED,
                "skin with value %string% and signature %string%");
    }

    private Expression<String> value;
    private Expression<String> signature;

    @Override
    protected @Nullable Skin[] get(Event e) {
        if (value == null || value.getSingle(e) == null || signature == null || signature.getSingle(e) == null) return null;
        return new Skin[]{new Skin(value.getSingle(e), signature.getSingle(e))};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Skin> getReturnType() {
        return Skin.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "skin with value " + value.getSingle(e) + "and signature " + signature.getSingle(e);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        value = (Expression<String>) exprs[0];
        signature = (Expression<String>) exprs[1];
        return true;
    }
}
