package de.ttt.nopvplog;

import de.ttt.nopvplog.controllers.CombatTimerController;
import de.ttt.nopvplog.controllers.DamageTimerController;
import de.ttt.nopvplog.controllers.DeathCrateController;
import de.ttt.nopvplog.controllers.HologramController;
import de.ttt.nopvplog.models.Timer;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public abstract class NoPvPLogTemplate extends JavaPlugin {
    public abstract CombatTimerController getCTController();

    public abstract DamageTimerController getDTController();

    public abstract DeathCrateController getDeathCrateController();

    public abstract Timer<? extends EntityDamageEvent> getLongerTimer(UUID playerId);


    public abstract HologramController getHologramController();
}
