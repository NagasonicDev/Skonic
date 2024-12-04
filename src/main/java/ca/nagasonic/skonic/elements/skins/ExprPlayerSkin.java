package ca.nagasonic.skonic.elements.skins;

import ca.nagasonic.skonic.elements.citizens.expressions.ExprCitizenEntityType;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Player's Skin")
@Description("Gets the player's skin." +
        "Cannot be set, to set, use 'Change Player Skin' effect.")
@Since("1.0.4")
@Examples("")
@DocumentationId("skin.player")
public class ExprPlayerSkin extends SimplePropertyExpression<Player, Skin> {
    static {
        register(ExprPlayerSkin.class,
                Skin.class,
                "skin",
                "player");
    }

    @Override
    public Class<? extends Skin> getReturnType() {
        return Skin.class;
    }

    @Override
    protected String getPropertyName() {
        return "skin of player";
    }

    @Override
    public @Nullable Skin convert(Player player) {
        if (player == null) return null;
        Skin skin = Skin.fromURL("https://sessionserver.mojang.com/session/minecraft/profile/" + player.getUniqueId() + "?unsigned=false");
        return skin;
    }
}
