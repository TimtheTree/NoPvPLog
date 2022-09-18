package de.ttt.nopvplog.models;

import de.ttt.nopvplog.controllers.TimerController;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.UUID;

public abstract class Timer<T extends EntityDamageEvent> {

    protected final UUID playerReference;
    protected long lastDamage;
    private final TimerController<? extends EntityDamageEvent> timerController;
    private final long timerDuration;
    private final long minimumDeactivationDistance;

    protected Timer(UUID playerReference, TimerController<? extends EntityDamageEvent> timerController) {
        this.playerReference = playerReference;
        this.timerController = timerController;
        this.timerDuration = this.timerController.getTimerDuration();
        this.minimumDeactivationDistance = this.timerController.getMinimumDeactivationDistance();
    }

    public void setLastDamage(long lastDamage) {
        this.lastDamage = lastDamage;
    }

    public long getLastDamage() {
        return this.lastDamage;
    }

    public UUID getPlayerReference() {
        return playerReference;
    }

    public TimerController<? extends EntityDamageEvent> getTimerController() {
        return timerController;
    }

    public long getTimerDuration() {
        return timerDuration;
    }

    public long getMinimumDeactivationDistance() {
        return minimumDeactivationDistance;
    }

    /**
     * @return time passed in seconds
     */
    public long timePassed() {
        return (System.currentTimeMillis() - this.lastDamage) / 1000;
    }

    public long timeLeftOnTimer(){
        return this.timerController.getTimeLeft(this.playerReference);
    }

    public abstract boolean isOutOfCombat(long timerDuration, long minimumDeactivationDistance);

    public abstract void update(T event);



}
