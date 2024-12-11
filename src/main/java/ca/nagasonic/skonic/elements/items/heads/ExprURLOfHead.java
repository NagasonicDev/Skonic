package ca.nagasonic.skonic.elements.items.heads;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.jetbrains.annotations.Nullable;

@Name("URL of Head")
@Description("Gets the URL of a head")
@Since("1.0.7")
@Examples("")
@DocumentationId("12515")
public class ExprURLOfHead extends SimpleExpression<String> {
    static {
        Skript.registerExpression(ExprURLOfHead.class,
                String.class,
                ExpressionType.COMBINED,
                "url of %itemstack%",
                "%itemstack%'s url");
    }
    private Expression<ItemStack> headExpr;
    @Override
    protected @Nullable String[] get(Event e) {
        ItemStack head = headExpr.getSingle(e);
        if (head != null && head.getType() == Material.PLAYER_HEAD){
            SkullMeta meta = (SkullMeta) head.getItemMeta();
            PlayerProfile profile = meta.getOwnerProfile();
            String url = profile.getTextures().getSkin().toString();
            return new String[]{url};
        }
        return new String[0];
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "Url of head " + headExpr.toString(e, b);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        headExpr = (Expression<ItemStack>) exprs[0];
        return true;
    }
}
