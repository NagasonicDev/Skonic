package au.nagasonic.skonic.elements.items.heads;

import au.nagasonic.skonic.elements.skins.Skin;
import au.nagasonic.skonic.elements.util.HeadUtils;
import au.nagasonic.skonic.Skonic;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

@Name("Head from Skin")
@Description("Gets a head by skin.")
@Since("1.0.7")
@Examples("set helmet of player to head from player's skin")
public class ExprHeadFromSkin extends SimpleExpression<ItemStack> {
    static {
        Skript.registerExpression(ExprHeadFromSkin.class,
                ItemStack.class,
                ExpressionType.COMBINED,
                "(head|skull) from %skin%");
    }

    private Expression<Skin> skinExpr;

    @Override
    protected @Nullable ItemStack[] get(Event e) {
        Skin skin = skinExpr.getSingle(e);
        if (skin != null){
            String value = skin.getTexture();
            if (value != null){
                ItemStack item = HeadUtils.headFromBase64(value);
                if (item != null){
                    return new ItemStack[]{item};
                }else{
                    Skonic.log(Level.WARNING, "Returned item is null, this could mean that the inputted value does not link to a skin.");
                }
            }else{
                Skonic.log(Level.WARNING, "Skin's value is null, this most likely means that the skin is set up wrong, or is empty.");
            }
        }else{
            Skonic.log(Level.WARNING, "Skin is null, this basically means that it is an empty variable or doesn't exist.");
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends ItemStack> getReturnType() {
        return ItemStack.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "head from skin " + skinExpr.toString(e, b);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        skinExpr = (Expression<Skin>) exprs[0];
        return true;
    }
}
