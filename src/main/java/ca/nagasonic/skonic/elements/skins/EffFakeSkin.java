package ca.nagasonic.skonic.elements.skins;

import ca.nagasonic.skonic.Skonic;
import ca.nagasonic.skonic.elements.util.NMSUtils;
import ca.nagasonic.skonic.elements.util.SkinUtils;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.logging.Level;


public class EffFakeSkin extends Effect {
    static {
        Skript.registerEffect(EffFakeSkin.class,
                "fake %player%'s skin to %skin% for %players%",
                "fake skin of %player% to %skin% for %players%");
    }
    private Expression<Player> playerExpression;
    private Expression<Player> players;
    private Expression<Skin> skinExpression;

    @Override
    protected void execute(Event e) {
        if (playerExpression == null || playerExpression.getSingle(e) == null){
            Skonic.log(Level.SEVERE, "Player is null");
        }
        if (skinExpression == null || skinExpression.getSingle(e) == null){
            Skonic.log(Level.SEVERE, "Skin is null");
        }
        if (players == null || players.getSingle(e) == null){
            Skonic.log(Level.SEVERE, "Player's to fake the skin for are null");
        }
        Skin skin = skinExpression.getSingle(e);
        try {
            Player player = playerExpression.getSingle(e);
            Location location = player.getLocation();
            ServerPlayer serverPlayer = (ServerPlayer) NMSUtils.getServerPlayer(player);
            ServerLevel nmsLevel = serverPlayer.serverLevel();
            SkinUtils.setSkin(serverPlayer, new String[]{skin.value, skin.signature});

            ClientboundPlayerInfoUpdatePacket playerInfoAdd = new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, serverPlayer);
            ClientboundPlayerInfoRemovePacket playerInfoRemove = new ClientboundPlayerInfoRemovePacket(List.of(serverPlayer.getUUID()));
            ClientboundSetEntityDataPacket setEntityData = new ClientboundSetEntityDataPacket(serverPlayer.getId(), serverPlayer.getEntityData().getNonDefaultValues());
            CommonPlayerSpawnInfo info = serverPlayer.createCommonSpawnInfo(nmsLevel);
            ClientboundRespawnPacket respawnPacket = new ClientboundRespawnPacket(info, (byte) 1);

            for (Player p : players.getArray(e)){
                ServerGamePacketListenerImpl connection = ((ServerPlayer) NMSUtils.getServerPlayer(p)).connection;

                connection.send(playerInfoRemove);
                connection.send(playerInfoAdd);

                if (player != p) {
                    connection.send(new ClientboundRemoveEntitiesPacket(serverPlayer.getId()));
                    connection.send(new ClientboundAddEntityPacket(serverPlayer));
                }
                connection.send(setEntityData);                // dual layered skin.


                serverPlayer.connection.send(respawnPacket);    // Inform own client to re-spawn (updates skin).
                player.teleport(location);                        // Reset original location as the re-spawn packet messes with us.
                player.updateInventory(); // refresh Inventory or it will be empty.
                if (player.isOp()) { // Reset Op as the client will have forgotten.
                    player.setOp(false);
                    player.setOp(true);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "Fake player's skin to " + skinExpression.getSingle(e).toString() + " for " + players.getArray(e).toString();
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        playerExpression = (Expression<Player>) exprs[0];
        players = (Expression<Player>) exprs[2];
        skinExpression = (Expression<Skin>) exprs[1];
        return true;
    }
}
