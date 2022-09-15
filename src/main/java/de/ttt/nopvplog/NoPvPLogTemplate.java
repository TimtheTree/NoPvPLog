package de.ttt.nopvplog;

import de.ttt.nopvplog.controllers.CombatTimerController;
import de.ttt.nopvplog.controllers.DeathCrateController;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class NoPvPLogTemplate extends JavaPlugin {
    public abstract CombatTimerController getCTController();
    public abstract DeathCrateController getDeathCrateController();
}
