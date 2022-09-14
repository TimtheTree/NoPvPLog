package de.ttt.nopvplog;

import de.ttt.nopvplog.controllers.CombatTimerController;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class NoPvPLogTemplate extends JavaPlugin {
    public abstract CombatTimerController getCTController();
}
