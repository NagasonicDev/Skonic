package au.nagasonic.skonic.elements.items.heads;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.Material;
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
public class ExprOwnerOfHead extends SimplePropertyExpression<ItemStack, Player> {
    static {
        register(ExprOwnerOfHead.class, Player.class,
                "owner",
                "itemstack");
    }
    @Override
    public @Nullable Player convert(ItemStack from) {
        if (from != null && from.getType() == Material.PLAYER_HEAD){
            SkullMeta meta = (SkullMeta) from.getItemMeta();
            Player player = (Player) meta.getOwningPlayer();
            if (player != null){
                return player;
            }
            return null;
        }
        return null;
    }

    @Override
    protected String getPropertyName() {
        return null;
    }

    @Override
    public Class<? extends Player> getReturnType() {
        return null;
    }
}
