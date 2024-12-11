package ca.nagasonic.skonic.elements.skins;

import ca.nagasonic.skonic.elements.util.HeadUtils;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.net.MalformedURLException;
import java.net.URL;

@Name("URL In Value")
@Description("Retrieves the url within a skin value")
@Since("1.0.7")
@Examples("")
@DocumentationId("12514")

public class ExprURLInValue extends SimpleExpression<String> {
    static {
        Skript.registerExpression(ExprURLInValue.class, String.class, ExpressionType.COMBINED,
                "[skin] url within value %string%");
    }
    private Expression<String> stringExpr;
    @Override
    protected @Nullable String[] get(Event e) {
        String urlString = stringExpr.getSingle(e);
        if (urlString == null) return null;
        URL url = null;
        try {
            url = HeadUtils.getUrlFromBase64(urlString);
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
        return new String[]{url.toString()};
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
    public String toString(@Nullable Event e, boolean b) {
        return "Url within " + stringExpr.toString(e, b);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        stringExpr = (Expression<String>) exprs[0];
        return true;
    }
}
