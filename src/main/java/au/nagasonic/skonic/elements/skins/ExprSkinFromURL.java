package au.nagasonic.skonic.elements.skins;

import au.nagasonic.skonic.elements.util.SkinUtils;
import au.nagasonic.skonic.Skonic;
import au.nagasonic.skonic.exceptions.SkinGenerationException;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.google.gson.JsonObject;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

@Name("Skin from URL")
@Description("Gets a skin from a url." +
        "URL must link directly to an image.")
@Since("1.0.3")
@Examples("set {_cacti} to skin from url \"http://textures.minecraft.net/texture/2f585b41ca5a1b4ac26f556760ed11307c94f8f8a1ade615bd12ce074f4793\"")
public class ExprSkinFromURL extends SimpleExpression<Skin> {
    static {
        Skript.registerExpression(ExprSkinFromURL.class, Skin.class, ExpressionType.COMBINED,
                "skin from url %string%");
    }
    Expression<String> urlExpr;
    @Override
    protected @Nullable Skin[] get(Event e) {
        String url = urlExpr.getSingle(e);

        if (url == null) {
            Skonic.logger().warn("(skin from url) : The provided URL returned null.");
            return null;
        }

        try {
            Skin skin = SkinUtils.getSkinFromMineskinUrl(url, false);
            return new Skin[]{skin};
        } catch (SkinGenerationException exception) {
            Skonic.logger().severe("(skin from url) : Failed to retrieve skin for URL: " + url, exception);
            return null;
        }
    }
//    @Override
//    protected @Nullable Skin[] get(Event e) {
//        String url = urlExpr.getSingle(e);
//        if (url != null){
//            JsonObject data = null;
//            try {
//                data = SkinUtils.generateFromURL(url, false);
//            } catch (ExecutionException ex) {
//                if (ex.getCause() instanceof IOException && ex.getCause().getMessage().contains("400")) {
//                    Skonic.log(Level.WARNING, "MineSkin API returned error 400 for URL: " + url +
//                            ". This may be due to MineSkin API v2 requiring authentication or the URL being invalid.");
//                    return null;
//                }else if (ex.getCause() instanceof IOException && ex.getCause().getMessage().contains("429")){
//                    Skonic.log(Level.WARNING, "MineSkin API returned response code 429 for URL: " + url +
//                            ". This is due to the server receiving too many requests, so it is recommended that you slow down in your application of the expression.");
//                    return null;
//                }
//                throw new RuntimeException(ex);
//            } catch (InterruptedException ex) {
//                throw new RuntimeException(ex);
//            }
//
//            if (data == null) {
//                Skonic.log(Level.SEVERE, "Failed to generate skin data from URL: " + url);
//                return null;
//            }
//
//            UUID uuid = UUID.fromString(data.get("uuid").getAsString());
//            JsonObject texture = data.get("texture").getAsJsonObject();
//            String value = texture.get("value").getAsString();
//            String signature = texture.get("signature").getAsString();
//            return new Skin[]{new Skin(value, signature)};
//        }else{
//            Skonic.log(Level.SEVERE, "URL is null.");
//            return null;
//        }
//    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Skin> getReturnType() {
        return Skin.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "Skin from url " + urlExpr.toString(e,b);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        urlExpr = (Expression<String>) exprs[0];
        return true;
    }
}
