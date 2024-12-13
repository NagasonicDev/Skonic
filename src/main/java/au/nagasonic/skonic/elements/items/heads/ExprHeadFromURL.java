package au.nagasonic.skonic.elements.items.heads;

import au.nagasonic.skonic.elements.util.HeadUtils;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Head from URL")
@Description("Gets a head by URL")
@Since("1.0.4")
@Examples("set {_melon} to head from url \"http://textures.minecraft.net/texture/9636dee806ba47a2c40e95b57a12f37de6c2e677f2160132a07e24eeffa6\"")
@DocumentationId("12503")
public class ExprHeadFromURL extends SimpleExpression<ItemStack> {
    static {
        Skript.registerExpression(ExprHeadFromURL.class,
                ItemStack.class,
                ExpressionType.COMBINED,
                "(head|skull) from url %string%");
    }
    private Expression<String> url;

    @SuppressWarnings({"NullableProblems", "unchecked"})
    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.url = (Expression<String>) exprs[0];
        return true;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected @Nullable ItemStack[] get(Event event) {
        String url = this.url.getSingle(event);
        if (url != null) {
            ItemStack item = HeadUtils.headFromUrl(url);
            if (item != null){
                return new ItemStack[]{item};
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
    @NotNull
    public Class<? extends ItemStack> getReturnType() {
        return ItemStack.class;
    }

    @Override
    @NotNull
    public String toString(@Nullable Event event, boolean debug) {
        return "head from url " + url.toString(event, debug);
    }
}
