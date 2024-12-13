package au.nagasonic.skonic.elements.skins;

import au.nagasonic.skonic.elements.util.HeadUtils;
import au.nagasonic.skonic.elements.util.Util;
import au.nagasonic.skonic.Skonic;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.apache.commons.io.FileUtils;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

@Name("Download Player Skin")
@Description("Downloads a skin")
@Since("1.0.4")
@Examples({"on join:", "\tdownload player's skin"})
@DocumentationId("12488")
public class EffDownloadPlayerSkin extends Effect {
    static {
        Skript.registerEffect(EffDownloadPlayerSkin.class,
                "(download|save) %skin%");
    }

    private Expression<Skin> skinExpr;

    @Override
    protected void execute(Event e) {
        Skin skin = skinExpr.getSingle(e);
        if (skinExpr == null || skin == null){
            Skonic.log(Level.SEVERE, "The given skin is null, please retry.");
            return;
        }
        String value = skin.value;
        if (value == null) {
            Skonic.log(Level.SEVERE, "The skin does not have a value, please check if the skin entered is correct.");
            return;
        }
        URL url = null;
        try {
            url = HeadUtils.getUrlFromBase64(value);
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
        if (url == null) {
            Skonic.log(Level.SEVERE, "The skin does not have a skin url. Aborting...");
            return;
        }
        File file = new File(Skonic.getPath() + "/skins/"  + Util.fromDate(Util.getDate()).replaceAll(" ", "") + ".png");
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Skonic.log(Level.SEVERE, "There was an error when retrieving the skin from the skin's url.");
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "download skin from " + skinExpr.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        skinExpr = (Expression<Skin>) exprs[0];
        return true;
    }
}
