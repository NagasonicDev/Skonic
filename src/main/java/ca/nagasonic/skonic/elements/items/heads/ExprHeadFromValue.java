package ca.nagasonic.skonic.elements.items.heads;

import ca.nagasonic.skonic.elements.util.HeadUtils;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExprHeadFromValue extends SimpleExpression<ItemStack> {
    static {
        Skript.registerExpression(ExprHeadFromValue.class, ItemStack.class, ExpressionType.COMBINED,
                "(head|skull) from value %string%");
    }
    private Expression<String> value;

    @SuppressWarnings({"NullableProblems", "unchecked"})
    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.value = (Expression<String>) exprs[0];
        return true;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected @Nullable ItemStack[] get(Event event) {
        String ValueString = this.value.getSingle(event);
        if (ValueString != null) {
            ItemStack item = HeadUtils.headFromBase64(ValueString);
            if (item != null){
                return new ItemStack[]{item};
            }
            return null;
        }
        return null;
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
        return "head from value " + value.getSingle(event);
    }
}
