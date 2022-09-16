package de.ttt.nopvplog.controllers;

import de.ttt.nopvplog.NoPvPLogTemplate;
import de.ttt.nopvplog.models.DamageTimer;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.UUID;

public class DamageTimerController extends TimerController<EntityDamageEvent> {

    public DamageTimerController(NoPvPLogTemplate template) {
        super(template);
    }

    @Override
    public void addEntry(UUID playerId) {
        this.timerHashMap.put(playerId, new DamageTimer(playerId));
    }

    public boolean detectCombat(UUID playerId) {

        DamageTimer damageTimer = (DamageTimer) this.getTimer(playerId);

        if (damageTimer == null) addEntry(playerId);
        damageTimer = (DamageTimer) this.getTimer(playerId);
        return !damageTimer.isOutOfCombat(this.timerDuration, 0);
    }


    public void updateEntry(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            UUID playerId = event.getEntity().getUniqueId();

            DamageTimer timer = (DamageTimer) this.getTimer(playerId);

            if (timer == null) {
                addEntry(playerId);
                timer = (DamageTimer) this.getTimer(playerId);
            }

            timer.update(event);
        }
    }

}
