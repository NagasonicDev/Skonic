package au.nagasonic.skonic.elements.skins;

import au.nagasonic.skonic.Skonic;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import org.jetbrains.annotations.Nullable;
import ch.njol.skript.Skript;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;
import org.skriptlang.skript.registration.SyntaxRegistry;

@Name("Skin Signature")
@Description("Gets the signature of a skin.")
@Since("1.0.7")
@Examples("broadcast signature of {_skin}")
public class ExprSkinSignature extends SimplePropertyExpression<Skin, String> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
                SyntaxRegistry.EXPRESSION,
                DefaultSyntaxInfos.Expression.builder(ExprSkinSignature.class, String.class)
                        .addPatterns("[skin] signature", "skin")
                        .build()
        );
    }

    @Override
    protected String getPropertyName() {
        return "skin signature";
    }

    @Override
    public @Nullable String convert(Skin skin) {
        if (skin != null){
            return skin.getSignature();
        }
        return null;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
