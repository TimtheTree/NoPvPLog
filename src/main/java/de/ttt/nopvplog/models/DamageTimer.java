package de.ttt.nopvplog.models;

import de.ttt.nopvplog.controllers.TimerController;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.UUID;

public class DamageTimer extends Timer<EntityDamageEvent> {

    public DamageTimer(UUID playerReference, TimerController<? extends EntityDamageEvent> timerController) {
        super(playerReference, timerController);
    }

    @Override
    public boolean isOutOfCombat(long timerDuration, long minimumDeactivationDistance) {
        return (timePassed() > timerDuration);
    }

    /**
     * Sets the time of last damage to the current time
     * @param event the event to verify that the update is required
     */
    public void update(EntityDamageEvent event) {
        setLastDamage(System.currentTimeMillis());
    }
}
