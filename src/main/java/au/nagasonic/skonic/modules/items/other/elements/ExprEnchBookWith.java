package au.nagasonic.skonic.modules.items.other.elements;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.EnchantmentType;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;
import org.skriptlang.skript.registration.SyntaxRegistry;

@Name("Enchanted Book With Enchantments")
@Description("Returns an enchanted book with the given enchantments.")
@Since("1.2.1")
@Examples("set {_sharpbook} to enchanted book with sharpness 5")
public class ExprEnchBookWith extends SimpleExpression<ItemStack> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprEnchBookWith.class, ItemStack.class)
                .addPatterns(
                    "%itemstack% (with|containing) %enchantmenttypes%"
                )
                .build()
        );
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
