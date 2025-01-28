package au.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.util.Color;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Gamemode")
@Description("The gamemode of a Citizens NPC. Only applicable if the Citizen's entity type is a Player.")
@Since("1.2.2-b1")
@Examples("set gamemode of npc with id 3 to creative")
@RequiredPlugins("Citizens")
public class ExprCitizenGamemode extends SimplePropertyExpression<NPC, GameMode> {
    static {
        register(ExprCitizenGamemode.class, GameMode.class, "(citizen|npc) game[ ]mode", "npcs");
    }
    @Override
    public @Nullable GameMode convert(NPC npc) {
        return ((Player) npc.getEntity()).getGameMode();
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) return CollectionUtils.array(GameMode.class);
        return null;
    }

    @SuppressWarnings({"NullableProblems", "ConstantValue"})
    @Override
    public void change(Event event, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta != null && delta[0] instanceof GameMode) {
            GameMode gamemode = (GameMode) delta[0];
            for (NPC npc : getExpr().getArray(event)) {
                if (npc.getEntity() instanceof Player){
                    Player npcPlayer = (Player) npc.getEntity();
                    npcPlayer.setGameMode(gamemode);
                }
            }
        }
    }

    @Override
    @NotNull
    public Class<? extends GameMode> getReturnType() {
        return GameMode.class;
    }

    @Override
    @NotNull
    protected String getPropertyName() {
        return "citizen gamemode";
    }
}
