package au.nagasonic.skonic.modules.items.heads.elements;

import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;
import org.skriptlang.skript.registration.SyntaxRegistry;

@Name("Owner of Head")
@Description("Gets the owner of a player head." +
        "Will only work if the head is from a player.")
@Since("1.0.4")
@Examples("broadcast owner of player's tool")
public class ExprOwnerOfHead extends SimplePropertyExpression<ItemStack, Player> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
                SyntaxRegistry.EXPRESSION,
                DefaultSyntaxInfos.Expression.builder(ExprOwnerOfHead.class, Player.class)
                        .addPatterns("owner of %itemstacks%", "%itemstacks%'[s] owner")
                        .build()
        );
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
    @NotNull
    protected String getPropertyName() {
        return "owner";
    }

    @Override
    @NotNull
    public Class<? extends Player> getReturnType() {
        return Player.class;
    }
}
