package au.nagasonic.skonic.modules.items.heads.elements;

import au.nagasonic.skonic.util.HeadUtils;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;
import org.skriptlang.skript.registration.SyntaxRegistry;

@Name("Value of Head")
@Description("Gets the value of a head")
@Since("1.0.7")
@Examples("broadcast value of {_melon}")
public class ExprValueOfHead extends SimplePropertyExpression<ItemStack, String> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
                SyntaxRegistry.EXPRESSION,
                DefaultSyntaxInfos.Expression.builder(ExprValueOfHead.class, String.class)
                        .addPatterns(
                                "value of %itemstack%",
                                "%itemstack%'[s] value"
                        )
                        .build()
        );
    }
    @Override
    public @Nullable String convert(ItemStack from) {
        if (from != null && from.getType() == Material.PLAYER_HEAD){
            return HeadUtils.getValue(from);
        }
        return null;
    }

    @Override
    protected String getPropertyName() {
        return "value of head";
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
