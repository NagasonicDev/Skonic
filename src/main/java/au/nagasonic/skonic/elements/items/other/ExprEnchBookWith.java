package au.nagasonic.skonic.elements.items.other;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.EnchantmentType;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.jetbrains.annotations.Nullable;

public class ExprEnchBookWith extends SimpleExpression<ItemStack> {
    static {
        Skript.registerExpression(ExprEnchBookWith.class, ItemStack.class, ExpressionType.COMBINED,
                "%itemstack% (with|containing) %enchantmenttypes%");
    }
    Expression<ItemStack> bookExpr;
    Expression<EnchantmentType> enchantsExpr;
    @Override
    protected ItemStack @Nullable [] get(Event event) {
        ItemStack input = bookExpr.getSingle(event);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) input.getItemMeta();
        final EnchantmentType[] enchs = enchantsExpr.getArray(event);
        for (final EnchantmentType ench : enchs) {
            if (meta.hasStoredEnchant(ench.getType())) {
                meta.removeEnchant(ench.getType());
            }
            meta.addStoredEnchant(ench.getType(), ench.getLevel(), true);
        }
        ItemStack result = bookExpr.getSingle(event);
        result.setItemMeta(meta);
        return new ItemStack[]{result};
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
    public String toString(@Nullable Event event, boolean debug) {
        return bookExpr.toString(event, debug) + " with " + enchantsExpr.toString(event, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.bookExpr = (Expression<ItemStack>) exprs[0];
        this.enchantsExpr = (Expression<EnchantmentType>) exprs[1];
        return true;
    }
}
