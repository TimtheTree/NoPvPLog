package de.ttt.nopvplog;

import de.ttt.nopvplog.controllers.CombatTimerController;
import de.ttt.nopvplog.controllers.DeathCrateController;
import de.ttt.nopvplog.listeners.CombatDetector;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.File;

public final class Nopvplog extends NoPvPLogTemplate {

    private CombatTimerController cTController;
    private DeathCrateController deathCrateController;

    @Override
    public void onEnable() {
        //create config file
        makeFiles();

        //create CTController for management and passing onto other dependants
        this.cTController = new CombatTimerController(this);
        this.deathCrateController = new DeathCrateController(this);
        //register Listened for Combat detection
        Bukkit.getPluginManager().registerEvents(new CombatDetector(this), this);

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Enabled NoPvPLog");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void makeFiles() {

        File configF = new File(getDataFolder(), "config.yml");

        if (!configF.exists()) {
            configF.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }
    }

    @Override
    public CombatTimerController getCTController() {
        return this.cTController;
    }

    @Override
    public DeathCrateController getDeathCrateController() {
        return null;
    }
}
