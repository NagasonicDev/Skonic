package ca.nagasonic.skonic.elements.util;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NMSUtils {
    public static final String NMS_VERSION = Bukkit.getServer().getClass().getPackage().getName().substring(23);
    private static final Method DEDICATED_SERVER = getMethod("org.bukkit.craftbukkit." + NMS_VERSION + ".CraftServer", "getServer");
    private static final Method SERVER_LEVEL = getMethod("org.bukkit.craftbukkit." + NMS_VERSION + ".CraftWorld", "getHandle");
    private static final Method SERVER_PLAYER = getMethod("org.bukkit.craftbukkit." + NMS_VERSION + ".entity.CraftPlayer", "getHandle");

    /**
     * Obtain a Method via reflection.
     *
     * @param clazz		a Class to access.
     * @param method	a Method to find.
     * @return Method	the Method, or null if not found.
     */
    @Nullable
    private static Method getMethod(@NotNull String clazz, @NotNull String method) {

        try {
            return Class.forName(clazz).getMethod(method);

        } catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Obtain the DedicatedServer without requiring
     * version dependent imports from CraftBukkit.
     *
     * @return Object	an NMS DedicatedServer instance.
     */
    @Nullable
    public static final Object getDedicatedServer() {

        try {
            return DEDICATED_SERVER.invoke(Bukkit.getServer());

        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Obtain the ServerLevel without requiring
     * version dependent imports from CraftBukkit.
     *
     * @param world		a Bukkit World
     * @return Object	an NMS ServerLevel instance.
     */
    @Nullable
    public static final Object getServerLevel(@NotNull World world) {

        try {
            return SERVER_LEVEL.invoke(world);

        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Obtain the ServerPlayer without requiring
     * version dependent imports from CraftBukkit.
     *
     * @param player	a Bukkit Player
     * @return Object	an NMS ServerPlayer instance.
     */
    @Nullable
    public static final Object getServerPlayer(@NotNull Player player) {

        try {
            return SERVER_PLAYER.invoke(player);

        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
            e.printStackTrace();
            return null;
        }
    }
}
