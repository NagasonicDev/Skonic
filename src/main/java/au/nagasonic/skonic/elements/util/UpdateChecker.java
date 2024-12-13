package au.nagasonic.skonic.elements.util;

import au.nagasonic.skonic.Skonic;
import ch.njol.skript.util.Version;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class UpdateChecker implements Listener {
    private final Skonic plugin;
    private final Version pluginVersion;
    private Version currentUpdateVersion;

    public UpdateChecker(Skonic plugin) {
        this.plugin = plugin;
        this.pluginVersion = new Version(plugin.getDescription().getVersion());
            setupJoinListener();
            checkUpdate(true);
    }

    private void setupJoinListener() {
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            private void onJoin(PlayerJoinEvent event) {
                Player player = event.getPlayer();
                if (!player.hasPermission("skonic.update.check")) return;

                Bukkit.getScheduler().runTaskLater(UpdateChecker.this.plugin, () -> getUpdateVersion(true).thenApply(version -> {
                    Util.sendColMsg(player, "&7[&9Skonic&7] Update available: &a" + version);
                    Util.sendColMsg(player, "&7[&9Skonic&7] Download at &bhttps://github.com/NagasonicDev/Skonic/releases");
                    return true;
                }), 30);
            }
        }, this.plugin);
    }

    private void checkUpdate(boolean async) {
        Util.log("Checking for update...");
        getUpdateVersion(async).thenApply(version -> {
            Util.logLoading("&cPlugin is not up to date!");
            Util.logLoading(" - Current version: &cv%s", this.pluginVersion);
            Util.logLoading(" - Available update: &av%s", version);
            Util.logLoading(" - Download available at: https://github.com/NagasonicDev/Skonic/releases");
            return true;
        }).exceptionally(throwable -> {
            Util.logLoading("&aPlugin is up to date!");
            return true;
        });
    }

    private CompletableFuture<Version> getUpdateVersion(boolean async) {
        CompletableFuture<Version> future = new CompletableFuture<>();
        if (this.currentUpdateVersion != null) {
            future.complete(this.currentUpdateVersion);
        } else {
            getLatestReleaseVersion(async).thenApply(version -> {
                if (version.compareTo(this.pluginVersion) <= 0) {
                    future.cancel(true);
                } else {
                    this.currentUpdateVersion = version;
                    future.complete(this.currentUpdateVersion);
                }
                return true;
            });
        }
        return future;
    }

    private CompletableFuture<Version> getLatestReleaseVersion(boolean async) {
        CompletableFuture<Version> future = new CompletableFuture<>();
        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
                Version lastest = getLastestVersionFromGitHub();
                if (lastest == null) future.cancel(true);
                future.complete(lastest);
            });
        } else {
            Version latest = getLastestVersionFromGitHub();
            if (latest == null) future.cancel(true);
            future.complete(latest);
        }
        return future;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    private @Nullable Version getLastestVersionFromGitHub() {
        try {
            URL url = new URL("https://api.github.com/repos/NagasonicDev/Skonic/releases/latest");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            JsonObject jsonObject = new Gson().fromJson(reader, JsonObject.class);
            String tag_name = jsonObject.get("tag_name").getAsString();
            return new Version(tag_name);
        } catch (IOException e) {
            e.printStackTrace();
            Util.logLoading("&cChecking for update failed!");
        }
        return null;
    }

}
