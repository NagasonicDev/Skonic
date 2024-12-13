package au.nagasonic.skonic.elements.skins;

import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import org.jetbrains.annotations.Nullable;

@Name("Skin Value")
@Description("Gets the texture value of a skin")
@Since("1.0.7")
@Examples("broadcast value of player's skin")
@DocumentationId("12510")
public class ExprSkinValue extends SimplePropertyExpression<Skin, String> {
    static {
        register(ExprSkinValue.class, String.class, "[skin] value", "skin");
    }

    @Override
    protected String getPropertyName() {
        return "skin value";
    }

    @Override
    public @Nullable String convert(Skin skin) {
        if (skin != null){
            return skin.value;
        }
        return null;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
