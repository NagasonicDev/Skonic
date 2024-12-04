package ca.nagasonic.skonic.elements.items.heads;

import ca.nagasonic.skonic.elements.util.HeadUtils;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Name("Head from UUID")
@Description("Gets a head by player UUID")
@Since("1.0.4")
@Examples("")
@DocumentationId("head.uuid")
public class ExprHeadFromUUID extends SimpleExpression<ItemStack> {
    static {
        Skript.registerExpression(ExprHeadFromUUID.class,
                ItemStack.class,
                ExpressionType.COMBINED,
                "(head|skull) from uuid %string%");
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
        UUID uuid = UUID.fromString(this.uuid.getSingle(event));
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
