package de.ttt.nopvplog;

import de.ttt.nopvplog.controllers.*;
import de.ttt.nopvplog.listeners.BlockModificationDetector;
import de.ttt.nopvplog.listeners.CombatDetector;
import de.ttt.nopvplog.listeners.CrateBreakDetector;
import de.ttt.nopvplog.listeners.PvPLogDetector;
import de.ttt.nopvplog.models.CombatTimerPvp;
import de.ttt.nopvplog.models.DamageTimer;
import de.ttt.nopvplog.models.Timer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.entity.EntityDamageEvent;

import java.io.File;
import java.util.UUID;

public final class Nopvplog extends NoPvPLogTemplate {

    private CombatTimerController combatTimerController;
    private DamageTimerController damageTimerController;
    private DeathCrateController deathCrateController;
    private HologramController hologramController;
    private ActionBarController actionBarController;

    @Override
    public void onEnable() {
        //create config file
        makeFiles();

        //create Controllers for management and passing onto other dependants
        this.combatTimerController = new CombatTimerController(this);
        this.damageTimerController = new DamageTimerController(this);
        this.deathCrateController = new DeathCrateController(this);
        this.hologramController = new HologramController(this);
        this.actionBarController = new ActionBarController(20, this);

        boolean blockModificationDuringCombat = this.getConfig().getBoolean("BlockModificationWhileInCombat");

        //register Listener for Combat detection
        Bukkit.getPluginManager().registerEvents(new CombatDetector(this), this);
        Bukkit.getPluginManager().registerEvents(new PvPLogDetector(this), this);
        Bukkit.getPluginManager().registerEvents(new CrateBreakDetector(this), this);

        if (!blockModificationDuringCombat) {
            Bukkit.getPluginManager().registerEvents(new BlockModificationDetector(this), this);
        }

        this.actionBarController.runUpdateTimer();

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

    /**
     * @param playerId the player to check
     * @return the timer related to this player which has more time left on the clock
     */
    @Override
    public Timer<? extends EntityDamageEvent> getLongerTimer(UUID playerId) {

        DamageTimer damageTimer = (DamageTimer) this.getDTController().getTimer(playerId);
        CombatTimerPvp combatTimer = (CombatTimerPvp) this.getCTController().getTimer(playerId);

        if (damageTimer.timeLeftOnTimer() < combatTimer.timeLeftOnTimer()) {
            return combatTimer;
        } else {
            return damageTimer;
        }
    }
}
