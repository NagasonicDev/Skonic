package au.nagasonic.skonic.modules.items.heads.elements;

import au.nagasonic.skonic.util.HeadUtils;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;
import org.skriptlang.skript.registration.SyntaxRegistry;

@Name("Head from Player")
@Description("Gets a head by player")
@Since("1.0.4")
@Examples("set helmet of player to head from player")
public class ExprHeadFromPlayer extends SimpleExpression<ItemStack> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprHeadFromPlayer.class, ItemStack.class)
                .addPatterns(
                    "(head|skull) from %player%",
                    "%player%['s] (head|skull)"
                )
                .build()
        );
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
        return true;
    }

    @Override
    public Class<? extends ItemStack> getReturnType() {
        return ItemStack.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "head of " + player.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        player = (Expression<Player>) exprs[0];
        return true;
    }
}
