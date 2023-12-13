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

    private Expression<Player> player;

    @Override
    protected void execute(Event e) {
        if (player == null || player.getSingle(e) == null) Skonic.getInstance().getLogger().log(Level.SEVERE, "The specified player is null.");
        PlayerProfile profile = player.getSingle(e).getPlayerProfile();
        if (profile == null) Skonic.getInstance().getLogger().log(Level.SEVERE, "The player does not have a profile, please check if the player entered is correct.");
        URL url = profile.getTextures().getSkin();
        if (url == null) Skonic.getInstance().getLogger().log(Level.SEVERE, "The player does not have a skin url. Aborting...");
        File file = new File(Skonic.getInstance().getDataFolder().getPath() + "/skins/" + player.getSingle(e).getName() + "/", "skins_" + player.getSingle(e).getName() + "_" + fromDate(getDate()).replaceAll(" ", "") + ".png");
        if (!file.exists()){
            file.mkdirs();
        }
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Skonic.getInstance().getLogger().log(Level.SEVERE, "There was an error when retrieving the skin from the player's url.");
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "download skin from " + player.getSingle(e).toString();
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        player = (Expression<Player>) exprs[0];
        return true;
    }
}
