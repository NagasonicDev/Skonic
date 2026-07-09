package au.nagasonic.skonic.modules.items.heads.elements;

import au.nagasonic.skonic.util.HeadUtils;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;
import org.skriptlang.skript.registration.SyntaxRegistry;

@Name("Head from Value")
@Description("Gets a head by value.")
@Since("1.0.4")
@Examples("set {_cacti} to head from value \"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmY1ODViNDFjYTVhMWI0YWMyNmY1NTY3NjBlZDExMzA3Yzk0ZjhmOGExYWRlNjE1YmQxMmNlMDc0ZjQ3OTMifX19\"")
public class ExprHeadFromValue extends SimpleExpression<ItemStack> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprHeadFromValue.class, ItemStack.class)
                .addPatterns(
                    "(head|skull) from value %string%"
                )
                .build()
        );
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
        return "head from value " + value.toString(event, debug);
    }
}
