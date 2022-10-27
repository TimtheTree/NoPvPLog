package de.ttt.nopvplog;

import de.ttt.nopvplog.commands.CombatRemoveCommand;
import de.ttt.nopvplog.controllers.*;
import de.ttt.nopvplog.listeners.*;
import de.ttt.nopvplog.models.CombatTimerPvp;
import de.ttt.nopvplog.models.DamageTimer;
import de.ttt.nopvplog.models.Timer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.io.File;
import java.util.UUID;

public final class NoPvPLog extends NoPvPLogTemplate {

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
        Bukkit.getPluginManager().registerEvents(new PlayerDeathDetector(this), this);
        Bukkit.getPluginManager().registerEvents(new ProjectileHitDetector(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinDetector(this), this);

        if (!blockModificationDuringCombat) {
            Bukkit.getPluginManager().registerEvents(new BlockModificationDetector(this), this);
        }

        this.getCommand("combatremove").setExecutor(new CombatRemoveCommand(this));

        this.actionBarController.runUpdateTimer();

        leaveCombatOnStartUp();

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Enabled NoPvPLog");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * gets the file config.yml or gets it if it doesn't exist
     */
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

    /**
     * makes sure that all players aren't in combat as the plugin is started
     */
    private void leaveCombatOnStartUp() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                UUID playerId = player.getUniqueId();
                getCTController().getTimer(playerId).leaveCombat();
                getDTController().getTimer(playerId).leaveCombat();
            }
        }, 5);
    }
}
