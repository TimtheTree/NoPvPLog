package de.ttt.nopvplog.models;

import de.ttt.nopvplog.controllers.TimerController;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.UUID;

public class CombatTimerPvp extends Timer<EntityDamageByEntityEvent> {

    private UUID enemyReference;

    public CombatTimerPvp(UUID playerReference, TimerController<? extends EntityDamageEvent> timerController) {
        super(playerReference, timerController);
    }

    public UUID getEnemyReference() {
        return enemyReference;
    }

    public void setEnemyReference(UUID enemyReference) {
        this.enemyReference = enemyReference;
    }

    /**
     * @return true if the combat timer is over AND the enemy players distance is greater than the minimum deactivation distance
     */
    public boolean isOutOfCombat() {
        return timePassed() > this.getTimerDuration() && playerEnemyDistance() > this.getMinimumDeactivationDistance();
    }

    /**
     * Calculates the distance of the player this timer is assigned to from the person who last hit them
     *
     * @return the distance
     */
    public long playerEnemyDistance() {

        if (playerReference == null
                || enemyReference == null) return Long.MAX_VALUE;

        Player player = Bukkit.getPlayer(playerReference);
        Player enemy = Bukkit.getPlayer(enemyReference);

        if (player == null
                || enemy == null) return Long.MAX_VALUE;

        return (long) player.getLocation().distance(enemy.getLocation());
    }

    /**
     * updates this objects last damage time and Enemy who cause the damage
     *
     * @param event the event to read from
     */
    public void update(EntityDamageByEntityEvent event) {
        setLastDamage(System.currentTimeMillis());
        setEnemyReference(event.getDamager().getUniqueId());
    }
}
