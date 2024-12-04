package ca.nagasonic.skonic.elements.skins;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Skin from UUID")
@Description("Gets a skin from player's uuid.")
@Since("1.0.4")
@Examples("")
@DocumentationId("skin.uuid")
public class ExprSkinFromUUID extends SimpleExpression<Skin> {
    static {
        Skript.registerExpression(ExprSkinFromUUID.class, Skin.class, ExpressionType.COMBINED,
                "skin from uuid %string%");
    }

    private Expression<String> uuidString;

    @Override
    protected @Nullable Skin[] get(Event e) {
        if (uuidString == null || uuidString.getSingle(e) == null) return null;
        return new Skin[]{Skin.fromURL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuidString.getSingle(e) + "?unsigned=false")};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Skin> getReturnType() {
        return Skin.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "skin from uuid " + uuidString.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        uuidString = (Expression<String>) exprs[0];
        return true;
    }
}
