package ca.nagasonic.skonic.elements.skins;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Skin Value")
@Description("Gets the texture value of a skin")
@Since("1.0.7")
@Examples("")
@DocumentationId("12510")
public class ExprSkinValue extends SimpleExpression<String> {
    static {
        Skript.registerExpression(ExprSkinValue.class, String.class, ExpressionType.COMBINED,
                " value of %skin%",
                "%skin%['s] value");
    }

    private Expression<Skin> skinExpr;

    @Override
    protected @Nullable String[] get(Event e) {
        Skin skin = skinExpr.getSingle(e);
        if (skin == null || skin.value == null) return null;
        return new String[]{skin.value};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "skin value of " + skinExpr.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        skinExpr = (Expression<Skin>) exprs[0];
        return true;
    }
}
