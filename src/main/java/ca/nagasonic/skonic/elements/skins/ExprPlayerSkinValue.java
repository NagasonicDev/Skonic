package ca.nagasonic.skonic.elements.skins;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.entity.Player;;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class ExprPlayerSkinValue extends SimpleExpression<String> {
    static {
        Skript.registerExpression(ExprPlayerSkinValue.class, String.class, ExpressionType.COMBINED,
                "[skin] value from %player%",
                "%player%['s] [skin] value");
    }

    private Expression<Player> player;

    @Override
    protected @Nullable String[] get(Event e) {
        if (player == null || player.getSingle(e) == null || player.getSingle(e).getUniqueId() == null) return null;
        try {
            URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + player.getSingle(e).getUniqueId());
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            JsonObject textures = new JsonParser().parse(reader).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String value = textures.get("value").getAsString();
            return new String[]{value};
        } catch (IOException ex) {
            Skript.error("Could not retrieve player's skin value from the MojangAPI", ErrorQuality.SEMANTIC_ERROR);
            throw new RuntimeException(ex);
        }
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
        return "skin value of " + player.getSingle(e).toString();
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        player = (Expression<Player>) exprs[0];
        return true;
    }
}
