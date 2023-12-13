package ca.nagasonic.skonic.elements.skins;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprPlayerSkin extends SimpleExpression<Skin> {
    static {
        Skript.registerExpression(ExprPlayerSkin.class, Skin.class, ExpressionType.COMBINED,
                "skin of %player%",
                "%player%['s] skin");
    }

    private Expression<Player> player;

    @Override
    protected @Nullable Skin[] get(Event e) {
        if (player != null && player.getSingle(e) != null){
            if (player.getSingle(e).getUniqueId() != null){
                Skin skin = Skin.fromURL("https://sessionserver.mojang.com/session/minecraft/profile/" + player.getSingle(e).getUniqueId() + "?unsigned=false");
                if (skin != null){
                    return new Skin[]{skin};
                }
                return null;
            }
            return null;
        }
        return null;
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
        return "skin of " + player.getSingle(e).toString();
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        player = (Expression<Player>) exprs[0];
        return true;
    }
}
