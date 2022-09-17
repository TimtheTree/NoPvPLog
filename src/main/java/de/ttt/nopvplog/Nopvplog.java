package de.ttt.nopvplog;

import de.ttt.nopvplog.controllers.CombatTimerController;
import de.ttt.nopvplog.controllers.DamageTimerController;
import de.ttt.nopvplog.controllers.DeathCrateController;
import de.ttt.nopvplog.controllers.HologramController;
import de.ttt.nopvplog.listeners.CombatDetector;
import de.ttt.nopvplog.listeners.PvPLogDetector;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.File;

public final class Nopvplog extends NoPvPLogTemplate {

    private CombatTimerController combatTimerController;
    private DamageTimerController damageTimerController;
    private DeathCrateController deathCrateController;
    private HologramController hologramController;

    @Override
    public void onEnable() {
        //create config file
        makeFiles();

        //create CTController for management and passing onto other dependants
        this.combatTimerController = new CombatTimerController(this);
        this.damageTimerController = new DamageTimerController(this);
        this.deathCrateController = new DeathCrateController(this);
        this.hologramController = new HologramController(this);
        //register Listener for Combat detection
        Bukkit.getPluginManager().registerEvents(new CombatDetector(this), this);
        Bukkit.getPluginManager().registerEvents(new PvPLogDetector(this.deathCrateController, this.combatTimerController, this.damageTimerController, this.hologramController), this);


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
        return this.combatTimerController;
    }

    @Override
    public DamageTimerController getDTController() {
        return this.damageTimerController;
    }

    @Override
    public DeathCrateController getDeathCrateController() {
        return this.deathCrateController;
    }

    @Override
    public HologramController getHologramController() {
        return this.hologramController;
    }
}
