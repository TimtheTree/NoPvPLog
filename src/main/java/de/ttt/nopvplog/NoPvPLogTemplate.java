package de.ttt.nopvplog;

import de.ttt.nopvplog.controllers.CombatTimerController;
import de.ttt.nopvplog.controllers.DamageTimerController;
import de.ttt.nopvplog.controllers.DeathCrateController;
import de.ttt.nopvplog.controllers.HologramController;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class NoPvPLogTemplate extends JavaPlugin {
    public abstract CombatTimerController getCTController();

    public abstract DamageTimerController getDTController();

    public abstract DeathCrateController getDeathCrateController();

    public abstract HologramController getHologramController();
}
