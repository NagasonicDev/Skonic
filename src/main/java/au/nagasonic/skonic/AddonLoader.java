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

        if (!Skript.isAcceptRegistrations()) {
            Util.log("&cSkript is no longer accepting registrations, addons can no longer be loaded!");
            Util.log("&cNo clue how this could happen.");
            Util.log("&cSeems a plugin is delaying &9Skonic loading, which is after Skript stops accepting registrations.");
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
        this.addon.setLanguageFileDirectory("lang");
        int[] elementCountBefore = Util.getElementCount();
        loadCitizenElements();
        loadHeadElements();
        loadSkinElements();
        loadClasses();
        int[] elementCountAfter = Util.getElementCount();
        int[] finish = new int[elementCountBefore.length];
        int total = 0;
        for (int i = 0; i < elementCountBefore.length; i++) {
            finish[i] = elementCountAfter[i] - elementCountBefore[i];
            total += finish[i];
        }
        String[] elementNames = new String[]{"event", "effect", "expression", "condition", "section"};

        Util.log("Loaded (%s) elements:", total);
        for (int i = 0; i < finish.length; i++) {
            Util.log(" - %s %s%s", finish[i], elementNames[i], finish[i] == 1 ? "" : "s");
        }
    }

    private void loadCitizenElements() {
        if (pluginManager.isPluginEnabled("Citizens")){
            try {
                this.addon.loadClasses("au.nagasonic.skonic.elements.citizens");
                Util.logLoading("&6Citizen elements &ahave successfully loaded");
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            loadForcefieldElements();
        }else{
            Util.logLoading("&6Citizen elements have been disabled: &cMissing Citizen Plugin");
        }
    }

    private void loadForcefieldElements() {
        Version skriptVersion = Skript.getVersion();
        if (skriptVersion.isSmallerThan(new Version(2, 10, 0))){
            Util.logLoading("&6Citizen Forcefield elements have been disabled: &cOutdated Skript Version: Requires at least 2.10.0");
        }else{
            try {
                this.addon.loadClasses("au.nagasonic.skonic.elements.forcefield");
                Util.logLoading("&6Citizen Forcefield elements &ahave successfully loaded");
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

    }

    private void loadHeadElements(){
        try {
            this.addon.loadClasses("au.nagasonic.skonic.elements.items.heads");
            Util.logLoading("&6Head elements &ahave successfully loaded");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSkinElements(){
        try {
            this.addon.loadClasses("au.nagasonic.skonic.elements.skins");
            Util.logLoading("&6Skin elements &ahave successfully loaded");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadClasses(){
        try {
            this.addon.loadClasses("au.nagasonic.skonic.classes");
            Util.logLoading("&aLoaded all Class Types successfully");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
