package au.nagasonic.skonic;

import au.nagasonic.skonic.elements.util.Util;
import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.util.Version;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.io.IOException;

/**
 * @hidden
 */
@SuppressWarnings("CallToPrintStackTrace")
public class AddonLoader {
    private final Skonic plugin;
    private final PluginManager pluginManager;
    private final Plugin skriptPlugin;
    private SkriptAddon addon;

    public AddonLoader(Skonic plugin) {
        this.plugin = plugin;
        this.pluginManager = plugin.getServer().getPluginManager();
        this.skriptPlugin = pluginManager.getPlugin("Skript");
    }

    boolean canLoadPlugin() {
        if (skriptPlugin == null) {
            Util.log("&cDependency Skript was not found, plugin disabling.");
            return false;
        }
        if (!skriptPlugin.isEnabled()) {
            Util.log("&cDependency Skript is not enabled, plugin disabling.");
            Util.log("&cThis could mean SkBee is being forced to load before Skript.");
            return false;
        }
        Version skriptVersion = Skript.getVersion();
        if (skriptVersion.isSmallerThan(new Version(2, 7))) {
            Util.log("&cDependency Skript outdated, plugin disabling.");
            Util.log("&9Skonic &erequires Skript 2.7+ but found Skript " + skriptVersion);
            return false;
        }
        if (!Skript.isAcceptRegistrations()) {
            Util.log("&cSkript is no longer accepting registrations, addons can no longer be loaded!");
            Plugin plugMan = Bukkit.getPluginManager().getPlugin("PlugMan");
            if (plugMan != null && plugMan.isEnabled()) {
                Util.log("&cIt appears you're running PlugMan. (Why....)");
                Util.log("&cIf you're trying to reload/enable &9Skonic &cwith PlugMan.... yeah, you can't.... I guess that's too bad");
                Util.log("&ePlease restart your server!");
            } else {
                Util.log("&cNo clue how this could happen.");
                Util.log("&cSeems a plugin is delaying &9Skonic loading, which is after Skript stops accepting registrations.");
            }
            return false;
        }
        Version version = new Version(Skonic.EARLIEST_VERSION);
        if (!Skript.isRunningMinecraft(version)) {
            Util.log("&cYour server version &7'&bMC %s&7'&c is not supported, only &7'&bMC %s+&7'&c is supported!", Skript.getMinecraftVersion(), version);
            return false;
        }
        loadSkriptElements();
        return true;
    }

    private void loadSkriptElements() {
        this.addon = Skript.registerAddon(this.plugin);
        addon.setLanguageFileDirectory("lang");
        loadCitizenElements();
        loadHeadElements();
        loadSkinElements();
        loadClasses();
    }

    private void loadCitizenElements() {
        if (pluginManager.isPluginEnabled("Citizens")){
            try {
                this.addon.loadClasses("ca.nagasonic.skonic.elements.citizens");
                Util.logLoading("&6Citizen elements &ahave successfully loaded");
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }else{
            Util.logLoading("&6Citizen elements have been disabled: &cMissing Citizen Plugin");
        }
    }

    private void loadHeadElements(){
        try {
            this.addon.loadClasses("ca.nagasonic.skonic.elements.items.heads");
            Util.logLoading("&6Head elements &ahave successfully loaded");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSkinElements(){
        try {
            this.addon.loadClasses("ca.nagasonic.skonic.elements.skins");
            Util.logLoading("&6Skin elements &ahave successfully loaded");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadClasses(){
        try {
            this.addon.loadClasses("ca.nagasonic.skonic.classes");
            Util.logLoading("&aLoaded all Class Types successfully");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
