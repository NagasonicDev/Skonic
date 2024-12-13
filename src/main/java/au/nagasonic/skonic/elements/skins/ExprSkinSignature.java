package au.nagasonic.skonic.elements.skins;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Skin Signature")
@Description("Gets the signature of a skin.")
@Since("1.0.7")
@Examples("broadcast signature of {_skin}")
@DocumentationId("12509")
public class ExprSkinSignature extends SimplePropertyExpression<Skin, String> {
    static {
        register(ExprSkinSignature.class, String.class, "[skin] signature", "skin");
    }

    @Override
    protected String getPropertyName() {
        return "skin signature";
    }

    @Override
    public @Nullable String convert(Skin skin) {
        if (skin != null){
            return skin.signature;
        }
        return null;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
