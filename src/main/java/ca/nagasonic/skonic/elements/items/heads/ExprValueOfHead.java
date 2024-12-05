package ca.nagasonic.skonic.elements.items.heads;

import ca.nagasonic.skonic.elements.util.HeadUtils;
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
import org.jetbrains.annotations.Nullable;

@Name("Value of Head")
@Description("Gets the value of a head")
@Since("1.0.7")
@Examples("")
@DocumentationId("head.value")
public class ExprValueOfHead extends SimpleExpression<String> {
    static {
        Skript.registerExpression(ExprValueOfHead.class,
                String.class,
                ExpressionType.COMBINED,
                "value of %itemstack%",
                "%itemstack%'s value");
    }
    private Expression<ItemStack> headExpr;
    @Override
    protected @Nullable String[] get(Event e) {
        ItemStack head = headExpr.getSingle(e);
        if (head != null && head.getType() == Material.PLAYER_HEAD){
            return new String[]{HeadUtils.getValue(head)};
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
        return "Value of head " + headExpr.toString(e, b);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPatterns, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        headExpr = (Expression<ItemStack>) exprs[0];
        return true;
    }
}
