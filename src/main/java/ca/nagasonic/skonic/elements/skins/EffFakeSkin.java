package ca.nagasonic.skonic.elements.skins;

import ca.nagasonic.skonic.elements.util.SkinUtils;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EffFakeSkin extends Effect {
    private Expression<Player> playerExpression;

    //private Expression<Player> players;

    private Expression<String> skinExpression;

    static {
        Skript.registerEffect(EffFakeSkin.class, "fake %player%'s skin to %string%");
    }

    protected void execute(Event e) {
        /*Skin skin = (Skin)this.skinExpression.getSingle(e);
        String value = skin.value;
        String signature = skin.signature;
        for(Player p : (Player[])this.players.getArray(e)){
            if(p ==) continue;
            //REMOVES THE PLAYER
            ((CraftPlayer)p).getHandle().connection.send(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, ((CraftPlayer)p).getHandle()));
            //CHANGES THE PLAYER'S GAME PROFILE
            GameProfile gp = ((CraftPlayer)p).getProfile();
            gp.getProperties().removeAll("textures");
            gp.getProperties().put("textures", new Property("textures", value, signature));
            //ADDS THE PLAYER
            ((CraftPlayer)p).getHandle().connection.send(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer)p).getHandle()));
            ((CraftPlayer)p).getHandle().connection.send(new PacketPlayOutEntityDestroy(p.getEntityId()));
            ((CraftPlayer)p).getHandle().connection.send(new PacketPlayOutNamedEntitySpawn(((CraftPlayer)p).getHandle()));
        }*/
        String name = this.skinExpression.getSingle(e);
        SkinUtils.nick(this.playerExpression.getSingle(e), name);
    }

    public String toString(@Nullable Event e, boolean debug) {
        return null;
    }

    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.playerExpression = (Expression)exprs[0];
        //this.players = (Expression)exprs[2];
        this.skinExpression = (Expression)exprs[1];
        return true;
    }
}
