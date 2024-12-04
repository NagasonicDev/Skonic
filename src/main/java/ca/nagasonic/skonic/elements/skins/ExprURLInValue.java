package ca.nagasonic.skonic.elements.skins;

import ca.nagasonic.skonic.elements.util.HeadUtils;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.net.MalformedURLException;
import java.net.URL;

public class ExprURLInValue extends SimpleExpression<URL> {
    static {
        Skript.registerExpression(ExprURLInValue.class, URL.class, ExpressionType.SIMPLE,
                "[skin] url within value %string%");
    }
    private Expression<String> stringExpr;
    @Override
    protected @Nullable URL[] get(Event e) {
        String urlString = stringExpr.getSingle(e);
        if (urlString == null) return null;
        URL url = null;
        try {
            url = HeadUtils.getUrlFromBase64(urlString);
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
        return new URL[]{url};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends URL> getReturnType() {
        return URL.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "Url within " + stringExpr.toString(e, b);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int pattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return false;
    }
}
