package ca.nagasonic.skonic.elements.items.heads;

import ca.nagasonic.skonic.elements.util.HeadUtils;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ExprHeadFromPlayer extends SimpleExpression<ItemStack> {
    static {
        Skript.registerExpression(ExprHeadFromPlayer.class, ItemStack.class, ExpressionType.COMBINED,
                "(head|skull) from %player%",
                "%player%['s] (head|skull)");
    }

    private Expression<Player> player;

    @Override
    protected @Nullable ItemStack[] get(Event e) {
        if (player.getSingle(e) != null && player != null){
            ItemStack item = HeadUtils.headFromName(player.getSingle(e).getName());
            if (item != null){
                return new ItemStack[]{item};
            }
            return null;
        }else return null;
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends ItemStack> getReturnType() {
        return null;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "head of " + player.getSingle(e).toString();
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        player = (Expression<Player>) exprs[0];
        return true;
    }
}
