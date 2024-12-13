package au.nagasonic.skonic.elements.skins;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Skin With")
@Description("Used to create a skin.")
@Since("1.0.4")
@Examples("set {_skin} to skin with value {_value} and signature {_sig}")
@DocumentationId("12511")
public class ExprSkinWith extends SimpleExpression<Skin> {
    static {
        Skript.registerExpression(ExprSkinWith.class, Skin.class, ExpressionType.COMBINED,
                "skin with value %string% and signature %string%");
    }

    private Expression<String> valueExpr;
    private Expression<String> signatureExpr;

    @Override
    protected @Nullable Skin[] get(Event e) {
        String value = valueExpr.getSingle(e);
        String signature = signatureExpr.getSingle(e);
        if (value == null || signature == null) return null;
        return new Skin[]{new Skin(value, signature)};
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
        return "skin with value " + valueExpr.toString(e, debug) + "and signature " + signatureExpr.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        valueExpr = (Expression<String>) exprs[0];
        signatureExpr = (Expression<String>) exprs[1];
        return true;
    }
}
