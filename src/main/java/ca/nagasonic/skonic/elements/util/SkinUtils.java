package ca.nagasonic.skonic.elements.util;

import ca.nagasonic.skonic.Skonic;
import ca.nagasonic.skonic.elements.skins.Skin;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.google.common.io.CharStreams;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.citizensnpcs.api.util.Messaging;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.profile.PlayerProfile;

import javax.net.ssl.HttpsURLConnection;

public class SkinUtils {
    public static JsonObject generateFromURL(String url, boolean slim) throws InterruptedException, ExecutionException {
        return EXECUTOR.<JsonObject>submit(() -> {
            DataOutputStream out = null;
            InputStreamReader reader = null;
            try {
                URL target = new URL("https://api.mineskin.org/generate/url");
                HttpURLConnection con = (HttpURLConnection)target.openConnection();
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                con.setRequestProperty("User-Agent", "Skonic/1.0");
                con.setRequestProperty("Cache-Control", "no-cache");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestProperty("Content-Type", "application/json");
                con.setConnectTimeout(1000);
                con.setReadTimeout(30000);
                out = new DataOutputStream(con.getOutputStream());
                JsonObject req = new JsonObject();
                req.addProperty("url", url);
                req.addProperty("name", "");
                if (slim)
                    req.addProperty("variant", "slim");
                out.writeBytes(req.toString().replace("\\", ""));
                out.close();
                reader = new InputStreamReader(con.getInputStream());
                String str = CharStreams.toString(reader);
                if (Messaging.isDebugging())
                    Messaging.debug(new Object[] { str });
                if (con.getResponseCode() != 200)
                    return null;
                JsonObject output = (new JsonParser()).parse(str).getAsJsonObject();
                JsonObject data = output.get("data").getAsJsonObject();
                con.disconnect();
                return data;
            } finally {
                if (out != null)
                    try {
                        out.close();
                    } catch (IOException iOException) {}
                if (reader != null)
                    try {
                        reader.close();
                    } catch (IOException iOException) {}
            }
        }).get();
    }

    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
    public static PlayerProfile getOfflineProfile(String name) {
        return Bukkit.getServer().createPlayerProfile(UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes()), name);
    }

    private static Map<UUID, String> names = new HashMap<>();
    public static PlayerProfile getEffectiveProfile(Player player) {
        String effectiveName = names.getOrDefault(player.getUniqueId(), player.getName());
        PlayerProfile profile = Bukkit.getServer().createPlayerProfile(effectiveName);
        profile.update();
        if (profile.getUniqueId() == null) {
            return getOfflineProfile(effectiveName);
        }
        return profile;
    }

    public static void nick(Player player, String name) {
        names.put(player.getUniqueId(), name);
        refresh(player, (Player[]) Bukkit.getServer().getOnlinePlayers().toArray());
    }

    private static Map<Player, WrappedGameProfile> playerPreviousIdentities = new HashMap<>();


    public static void refresh(Player player, Player[] players) {
        // REMOVE PLAYER FOR OTHERS
        {
            WrappedGameProfile previous = playerPreviousIdentities.remove(player);
            if (previous == null) {
                previous = new WrappedGameProfile(player.getUniqueId(), player.getName());
            }

            WrappedGameProfile profile = (WrappedGameProfile) getEffectiveProfile(player);
            playerPreviousIdentities.put(player, new WrappedGameProfile(profile.getId(), profile.getName()));

            PacketContainer packet = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
            packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);
            packet.getPlayerInfoDataLists().write(0, List.of(new PlayerInfoData(
                    previous,
                    0,
                    EnumWrappers.NativeGameMode.SURVIVAL,
                    null
            )));

            for (Player p : players) {
                if (!p.equals(player)) {
                    ProtocolLibrary.getProtocolManager().sendServerPacket(p, packet.deepClone(), false);
                }
            }
        }

        // REMOVE PLAYER FOR SELF
        {
            PacketContainer packet = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
            packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);
            packet.getPlayerInfoDataLists().write(0, List.of(new PlayerInfoData(
                    new WrappedGameProfile(player.getUniqueId(), player.getName()),
                    0,
                    EnumWrappers.NativeGameMode.SURVIVAL,
                    null
            )));
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet, false);
        }

        // ADD PLAYER FOR EVERYONE
        {
            PacketContainer packet = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
            packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);
            packet.getPlayerInfoDataLists().write(0, List.of(new PlayerInfoData(
                    new WrappedGameProfile(player.getUniqueId(), player.getName()),
                    player.getPing(),
                    EnumWrappers.NativeGameMode.fromBukkit(player.getGameMode()),
                    WrappedChatComponent.fromText(player.getName())
            )));

            for (Player p : players) {
                ProtocolLibrary.getProtocolManager().sendServerPacket(p, packet.deepClone());
            }
        }

        // RESPAWN FOR SELF
        {
            Optional<World> worldOptional = Bukkit.getServer().getWorlds().stream().filter(it -> it.equals(player.getWorld())).findFirst();;
            Bukkit.getServer().getWorlds().stream().filter(it -> it.equals(player.getWorld())).findFirst();
            worldOptional.ifPresent(it -> {
                Location location = player.getLocation();
                player.teleport(it.getSpawnLocation());
                player.teleport(location);
            });
        }

        for (Player p : players) {
            p.hidePlayer(Skonic.getInstance(), player);
            p.showPlayer(Skonic.getInstance(), player);
        }
    }

    public static boolean setSkin(GameProfile profile, Skin skin) {
        profile.getProperties().put("textures", new Property("textures", skin.value, skin.signature));
        return true;
    }
}
