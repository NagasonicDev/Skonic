package au.nagasonic.skonic.elements.skins;

import au.nagasonic.skonic.Skonic;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.AsyncEffect;
import ch.njol.util.Kleenean;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;


@Name("Change Skin")
@Description("Changes the skin of a player.")
@Since("1.2")
@Examples("change player's skin to skin from url \"https://www.minecraftskins.com/uploads/skins/2024/12/12/--*ginger-bread-man*----tcer3--22929673.png?v695\"")
public class EffChangeSkin extends AsyncEffect {
    static {
        Skript.registerEffect(EffChangeSkin.class,
                "(change|set) %player%['s] skin to %skin%",
                "(change|set) skin of %player% to %skin%");
    }
    private Expression<Player> playerExpr;
    private Expression<Skin> skinExpr;

    @Override
    protected void execute(Event event) {
        if (playerExpr != null && skinExpr != null){
            Player player = playerExpr.getSingle(event);
            Skin skin = skinExpr.getSingle(event);
            if (player != null && skin != null){
                PlayerProfile profile = player.getPlayerProfile();
                profile.getProperties().removeIf(property -> property.getName().equals("textures"));
                profile.getProperties().add(
                        new ProfileProperty(
                                "textures",
                                skin.getTexture(),
                                skin.getSignature()
                        )
                );

                Bukkit.getScheduler().runTask(
                        Skonic.getInstance(),
                        () -> {
                            if (player.isOnline()) {
                                player.setPlayerProfile(profile);
                            }
                        }
                );
            }
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "set skin of " + playerExpr.toString(event, debug) + " to " + skinExpr.toString(event, debug);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        playerExpr = (Expression<Player>) exprs[0];
        skinExpr = (Expression<Skin>) exprs[1];
        return true;
    }
}
