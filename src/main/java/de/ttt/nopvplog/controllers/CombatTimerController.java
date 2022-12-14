package de.ttt.nopvplog.controllers;

import de.ttt.nopvplog.NoPvPLogTemplate;
import de.ttt.nopvplog.models.CombatTimerPvp;
import de.ttt.nopvplog.models.Timer;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.UUID;

public class CombatTimerController extends TimerController<EntityDamageByEntityEvent> {


    public CombatTimerController(NoPvPLogTemplate template) {
        super(template, "CombatTimerDuration");
    }

    /**adds a player to the timerHashMap
     *
     * @param playerId the player to add
     */
    @Override
    public void addEntry(UUID playerId) {
        this.timerHashMap.put(playerId, new CombatTimerPvp(playerId, this));
    }

    @Override
    public long getMinimumDeactivationDistance() {
        return minimumDeactivationDistance;
    }

    /**
     * Checks whether the Timer connected to the player UUID indicates the player is in combat
     *
     * @param playerId the player to check for combat
     * @return true if the player is in combat, false otherwise
     */
    public boolean detectCombat(UUID playerId) {

        CombatTimerPvp combatTimer = (CombatTimerPvp) this.getTimer(playerId);

        return !combatTimer.isOutOfCombat();
    }

    /**
     * Updates the timer connected to the player given in the event.
     *
     * @param event the event to gather the date for the update from
     */
    public void updateEntry(EntityDamageByEntityEvent event) {

        if (event.getEntityType() == EntityType.PLAYER && event.getDamager().getType() == EntityType.PLAYER) {

            UUID[] players = new UUID[2];
            players[0] = event.getEntity().getUniqueId();
            players[1] = event.getDamager().getUniqueId();

            for (UUID id : players) {

                Timer<EntityDamageByEntityEvent> timer = getTimer(id);

                timer.update(event);

                timer.setDamageCause(event.getCause());
            }
        }
    }
}
