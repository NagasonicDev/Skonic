package au.nagasonic.skonic.elements.skins;

import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import org.jetbrains.annotations.Nullable;

@Name("Skin Signature")
@Description("Gets the signature of a skin.")
@Since("1.0.7")
@Examples("broadcast signature of {_skin}")
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
            return skin.getSignature();
        }
        return null;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
