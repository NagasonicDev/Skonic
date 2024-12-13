package au.nagasonic.skonic.elements.items.heads;

import au.nagasonic.skonic.elements.util.HeadUtils;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

@Name("Value of Head")
@Description("Gets the value of a head")
@Since("1.0.7")
@Examples("broadcast value of {_melon}")
@DocumentationId("12516")
public class ExprValueOfHead extends SimplePropertyExpression<ItemStack, String> {
    static {
        register(ExprValueOfHead.class, String.class,
                "value",
                "itemstack");
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
