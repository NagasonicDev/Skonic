package ca.nagasonic.skonic.elements.skins;

import ca.nagasonic.skonic.Skonic;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.apache.commons.io.FileUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.profile.PlayerProfile;
import org.jetbrains.annotations.Nullable;
import java.io.*;
import java.net.URL;
import java.util.logging.Level;

import static ca.nagasonic.skonic.elements.util.Util.fromDate;
import static ca.nagasonic.skonic.elements.util.Util.getDate;

public class EffDownloadPlayerSkin extends Effect {
    static {
        Skript.registerEffect(EffDownloadPlayerSkin.class,
                "(download|save) skin from %player%",
                "(download|save) %player%['s] skin");
    }

    private Expression<Player> playerExpr;

    @Override
    protected void execute(Event e) {
        Player player = playerExpr.getSingle(e);
        if (playerExpr == null || player == null){
            Skonic.log(Level.SEVERE, "The given player is null, please retry.");
            return;
        }
        PlayerProfile profile = player.getPlayerProfile();
        if (profile == null) {
            Skonic.log(Level.SEVERE, "The player does not have a profile, please check if the player entered is correct.");
            return;
        }
        URL url = profile.getTextures().getSkin();
        if (url == null) {
            Skonic.log(Level.SEVERE, "The player does not have a skin url. Aborting...");
            return;
        }
        File file = new File(Skonic.getPath() + "/skins/" + player.getName() + "/", "skins_" + player.getName() + "_" + fromDate(getDate()).replaceAll(" ", "") + ".png");
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Skonic.log(Level.SEVERE, "There was an error when retrieving the skin from the player's url.");
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "download skin from " + playerExpr.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        playerExpr = (Expression<Player>) exprs[0];
        return true;
    }
}
