package au.nagasonic.skonic.elements.items.heads;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.Nullable;

@Name("Owner of Head")
@Description("Gets the owner of a player head." +
        "Will only work if the head is from a player.")
@Since("1.0.4")
@Examples("broadcast owner of player's tool")
@DocumentationId("12507")
public class ExprOwnerOfHead extends SimpleExpression<Player> {
    static {
        Skript.registerExpression(ExprOwnerOfHead.class,
                Player.class,
                ExpressionType.COMBINED,
                "owner of %itemstack%");
    }

    private Expression<ItemStack> item;

    @Override
    protected @Nullable Player[] get(Event e) {
        if (item != null && item.getSingle(e) != null){
            SkullMeta meta = (SkullMeta) item.getSingle(e).getItemMeta();
            if (meta != null){
                if (meta.getOwningPlayer() == null) return null;
                Player player = meta.getOwningPlayer().getPlayer();
                if (player != null){
                    return new Player[]{player};
                }
                return null;
            }
            return null;
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Player> getReturnType() {
        return Player.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "owner of " + item.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        item = (Expression<ItemStack>) exprs[0];
        return true;
    }
}
