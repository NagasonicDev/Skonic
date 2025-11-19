package au.nagasonic.skonic.elements.skins;

import au.nagasonic.skonic.Skonic;
import au.nagasonic.skonic.elements.util.SkinUtils;
import au.nagasonic.skonic.exceptions.SkinGenerationException;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.io.File;

@Name("Skin from File")
@Description("Creates a skin instance from an image file stored in the skins/ folder within the plugin folder.")
@Since("1.2.5")
@Examples("set {skin} to skin from file \"skin.png\"")
public class ExprSkinFromFile extends SimpleExpression<Skin> {
    static {
        Skript.registerExpression(ExprSkinFromFile.class, Skin.class, ExpressionType.COMBINED,
                "[s:slim] skin from file %string%");
    }
    private Expression<String> fileExpr;
    private boolean s;
    @Override
    protected Skin @Nullable [] get(Event event) {
        final String fileName = fileExpr.getSingle(event);

        if (fileName == null) {
            Skript.error("Specified File is null.", ErrorQuality.SEMANTIC_ERROR);
            return null;
        }

        if (!Skonic.getInstance().isEnabled()) {
            Skonic.logger().warn("Plugin is disabled. Skipping skin retrieval task for File: %s", fileName);
            return null;
        }
        File skinsFolder = new File(Skonic.getDataDirectory(), "skins");
        File file = new File(skinsFolder, fileName);
        if (!file.exists() || !file.isFile() || file.isHidden()) {
            Skript.error("Couldn't retrieve file, does it exist? File name: " + fileName);
            return null;
        }

        Skin skin;
        try {
            skin = SkinUtils.getSkinFromMineskinFile(fileName, s);
        } catch (SkinGenerationException exception) {
            Skript.error(
                    "Failed to generate skin from File: "
                            + fileName
                            + ". Details: "
                            + exception.getMessage(),
                    ErrorQuality.SEMANTIC_ERROR);
            return null;
        }
        return new Skin[]{skin};
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
    public String toString(@Nullable Event event, boolean b) {
        return "skin from file " + fileExpr.toString(event, b);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.fileExpr = (Expression<String>) exprs[0];
        this.s = parseResult.hasTag("s");
        return true;
    }
}
