package de.ttt.nopvplog.controllers;

import de.ttt.nopvplog.NoPvPLogTemplate;
import de.ttt.nopvplog.models.DamageTimer;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.UUID;

public class DamageTimerController extends TimerController<EntityDamageEvent> {

    public DamageTimerController(NoPvPLogTemplate template) {
        super(template, "GeneralTimerDuration");
    }

    /**
     * Gets the minimum deactivation distance.
     * @return -1, always.
     */
    @Override
    public long getMinimumDeactivationDistance() {
        return -1;
    }

    /**
     * Adds a new entry for the player specified by the passed in UUID, only if there was no entry before
     * @param playerId UUID of the player to create the entry for
     */
    @Override
    public void addEntry(UUID playerId) {
        if(this.timerHashMap.get(playerId) != null) return;
        this.timerHashMap.put(playerId, new DamageTimer(playerId, this));
    }

    /**
     * Checks whether the Timer connected to the player UUID indicates the player is in combat
     *
     * @param playerId the player to check for combat
     * @return true if the player is in combat, false otherwise
     */
    public boolean detectCombat(UUID playerId) {

        DamageTimer damageTimer = (DamageTimer) this.getTimer(playerId);

        return !damageTimer.isOutOfCombat();
    }

    /**
     * Updates the timer connected to the player given in the event.
     *
     * @param event the event to gather the date for the update from
     */
    public void updateEntry(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            UUID playerId = event.getEntity().getUniqueId();

            DamageTimer timer = (DamageTimer) this.getTimer(playerId);

            timer.update(event);

            timer.setDamageCause(event.getCause());
        }
    }

}
