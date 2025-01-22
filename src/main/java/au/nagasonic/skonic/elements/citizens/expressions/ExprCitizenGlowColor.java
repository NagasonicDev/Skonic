package au.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.ScoreboardTrait;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Glow Color")
@Description("The glow color of a Citizens NPC, in the Chat Color class.")
@Since("1.2.2")
@RequiredPlugins("Citizens")
@Examples("set the npc glow colour of all citizens to red")
public class ExprCitizenGlowColor extends SimplePropertyExpression<NPC, ChatColor> {
    static {
        register(ExprCitizenGlowColor.class, ChatColor.class, "(citizen|npc) glow colo[u]r", "npcs");
    }
    @Override
    public @Nullable ChatColor convert(NPC npc) {
        return npc.getOrAddTrait(ScoreboardTrait.class).getColor();
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) return CollectionUtils.array(ChatColor.class);
        return null;
    }

    @SuppressWarnings({"NullableProblems", "ConstantValue"})
    @Override
    public void change(Event event, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta != null && delta[0] instanceof ChatColor) {
            ChatColor color = (ChatColor) delta[0];
            for (NPC npc : getExpr().getArray(event)) {
                npc.getOrAddTrait(ScoreboardTrait.class).setColor(color);
            }
        }
    }

    @Override
    @NotNull
    public Class<? extends ChatColor> getReturnType() {
        return ChatColor.class;
    }

    @Override
    @NotNull
    protected String getPropertyName() {
        return "citizen glow color";
    }
}
