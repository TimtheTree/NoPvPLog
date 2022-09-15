package de.ttt.nopvplog.models;

import org.bukkit.event.entity.EntityDamageEvent;

import java.util.UUID;

public class DamageTimer extends Timer<EntityDamageEvent>{

    public DamageTimer(UUID playerReference) {
        super(playerReference);
    }

    @Override
    public boolean isOutOfCombat(long timerDuration, long minimumDeactivationDistance) {
        return this.lastDamage > timerDuration;
    }

    public void update(EntityDamageEvent event) {
        setLastDamage(System.currentTimeMillis());
    }
}
