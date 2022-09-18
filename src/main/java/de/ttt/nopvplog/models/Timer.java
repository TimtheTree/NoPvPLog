package de.ttt.nopvplog.models;

import de.ttt.nopvplog.controllers.TimerController;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.UUID;

public abstract class Timer<T extends EntityDamageEvent> {

    protected final UUID playerReference;
    protected long lastDamage;
    private TimerController<? extends EntityDamageEvent> timerController;

    protected Timer(UUID playerReference, TimerController<? extends EntityDamageEvent> timerController) {
        this.playerReference = playerReference;
        this.timerController = timerController;
    }

    public void setLastDamage(long lastDamage) {
        this.lastDamage = lastDamage;
    }

    public long getLastDamage() {
        return this.lastDamage;
    }

    /**
     * @return time passed in seconds
     */
    public long timePassed() {
        return (this.lastDamage - System.currentTimeMillis()) / 1000;
    }

    public long timeLeftOnTimer(){
        return this.timerController.getTimeLeft(this.playerReference);
    }

    public abstract boolean isOutOfCombat(long timerDuration, long minimumDeactivationDistance);

    public abstract void update(T event);



}
