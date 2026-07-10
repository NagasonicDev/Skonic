package au.nagasonic.skonic.modules.skins.elements;

import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;
import org.skriptlang.skript.registration.SyntaxRegistry;

@Name("Player's Skin")
@Description("Gets the player's skin." +
        "Cannot be set, to set, use 'Change Player Skin' effect.")
@Since("1.0.4")
@Examples({"on join:", "\tset {skins::%player's uuid%} to player's skin"})
public class ExprPlayerSkin extends SimplePropertyExpression<Player, Skin> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
                SyntaxRegistry.EXPRESSION,
                DefaultSyntaxInfos.Expression.builder(ExprPlayerSkin.class, Skin.class)
                        .addPatterns(
                                "skin of %player%",
                                "%player%'[s] skin"
                        )
                        .build()
        );
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
        if (skin == null) return null;
        return skin;
    }
}
