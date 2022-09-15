package de.ttt.nopvplog.models;

import org.bukkit.event.entity.EntityDamageEvent;

import java.util.UUID;

public abstract class Timer<T extends EntityDamageEvent> {

    protected final UUID playerReference;
    protected long lastDamage;

    protected Timer(UUID playerReference) {
        this.playerReference = playerReference;
    }

    public void setLastDamage(long lastDamage) {
        this.lastDamage = lastDamage;
    }

    /**
     * @return time passed in seconds
     */
    public long timePassed() {
        return (this.lastDamage - System.currentTimeMillis()) / 1000;
    }

    public abstract boolean isOutOfCombat(long timerDuration, long minimumDeactivationDistance);

    public abstract void update(T event);

}
