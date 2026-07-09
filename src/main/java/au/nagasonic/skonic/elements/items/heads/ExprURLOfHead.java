package au.nagasonic.skonic.elements.items.heads;

import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.Nullable;
import ch.njol.skript.Skript;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;
import org.skriptlang.skript.registration.SyntaxRegistry;

@Name("URL of Head")
@Description("Gets the URL of a head")
@Since("1.0.7")
@Examples("broadcast url of {_head}")
public class ExprURLOfHead extends SimplePropertyExpression<ItemStack, String> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
                SyntaxRegistry.EXPRESSION,
                DefaultSyntaxInfos.Expression.builder(ExprURLOfHead.class, String.class)
                        .addPatterns("url", "itemstack")
                        .build()
        );
    }

    @Override
    public @Nullable String convert(ItemStack from) {
        if (from != null && from.getType() == Material.PLAYER_HEAD){
            SkullMeta meta = (SkullMeta) from.getItemMeta();
            String url = meta.getOwnerProfile().getTextures().getSkin().toString();
            return url;
        }
        return null;
    }

    @Override
    protected String getPropertyName() {
        return "url of head";
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
