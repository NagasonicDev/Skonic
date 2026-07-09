package au.nagasonic.skonic.modules.skins.elements;

import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;
import org.skriptlang.skript.registration.SyntaxRegistry;

@Name("Skin Value")
@Description("Gets the texture value of a skin")
@Since("1.0.7")
@Examples("broadcast value of player's skin")
public class ExprSkinValue extends SimplePropertyExpression<Skin, String> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
                SyntaxRegistry.EXPRESSION,
                DefaultSyntaxInfos.Expression.builder(ExprSkinValue.class, String.class)
                        .addPatterns("texture value", "skin")
                        .build()
        );
    }

    @Override
    protected String getPropertyName() {
        return "skin value";
    }

    @Override
    public @Nullable String convert(Skin skin) {
        if (skin != null){
            return skin.getTexture();
        }
        return null;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
