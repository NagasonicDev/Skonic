package ca.nagasonic.skonic.elements.items.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExprGlowingItem extends SimpleExpression<ItemStack> {
    static {
        Skript.registerExpression(ExprGlowingItem.class, ItemStack.class, ExpressionType.COMBINED,
                "(glowing|shiny) %itemstack%");
    }
    private Expression<ItemStack> item;

    @SuppressWarnings({"NullableProblems", "unchecked"})
    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        item = (Expression<ItemStack>) exprs[0];
        return true;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected @Nullable ItemStack[] get(Event event) {
        ItemStack item = this.item.getSingle(event);
        if (item != null) {
             if (item.getData().getItemType() != null){
                 if (item.getData().getItemType() == Material.FISHING_ROD) {
                     item.addUnsafeEnchantment(Enchantment.LOYALTY, 1);
                 }else{
                     item.addUnsafeEnchantment(Enchantment.LUCK, 1);
                 }
                 return new ItemStack[]{item};
             }else return null;
        }else return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    @NotNull
    public Class<? extends ItemStack> getReturnType() {
        return ItemStack.class;
    }

    @Override
    @NotNull
    public String toString(@Nullable Event event, boolean debug) {
        return "glowing " + item.getSingle(event).toString();
    }
}
