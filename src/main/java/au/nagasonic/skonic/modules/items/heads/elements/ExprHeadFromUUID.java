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

@Name("Head from UUID")
@Description("Gets a head by player UUID")
@Since("1.0.4")
@Examples("set player's helmet to head from uuid \"%player's uuid%\"")
public class ExprHeadFromUUID extends SimpleExpression<ItemStack> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprHeadFromUUID.class, ItemStack.class)
                .addPatterns(
                    "(head|skull) from uuid %string%"
                )
                .build()
        );
    }
    private Expression<String> uuid;

    @SuppressWarnings({"NullableProblems", "unchecked"})
    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.uuid = (Expression<String>) exprs[0];
        return true;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected @Nullable ItemStack[] get(Event event) {
        String uuid = this.uuid.getSingle(event);
        if (uuid != null) {
            ItemStack item = HeadUtils.headFromUuid(uuid);
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
        return "head from uuid " + uuid.toString(event, debug);
    }
}
