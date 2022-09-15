package de.ttt.nopvplog.models;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.UUID;

public class CombatTimerPvp extends Timer<EntityDamageByEntityEvent> {

    private UUID enemyReference;

    public CombatTimerPvp(UUID playerReference) {
        super(playerReference);
    }

    public void setEnemyReference(UUID enemyReference) {
        this.enemyReference = enemyReference;
    }

    /**
     *
     * @param timerDuration The time a player has to wait before being out of combat
     * @param minimumDeactivationDistance The distance the player has to be away from their enemy in order to be out of combat
     * @return true if the combat timer is over AND the enemy players distance is greater than the minimum deactivation distance
     */
    public boolean isOutOfCombat(long timerDuration, long minimumDeactivationDistance) {
        return timePassed() > timerDuration && playerEnemyDistance() > minimumDeactivationDistance;
    }

    /**
     * Calculates the distance of the player this timer is assigned to from the person who last hit them
     * @return the distance
     */
    public long playerEnemyDistance() {
        Player player = Bukkit.getPlayer(playerReference);
        Player enemy = Bukkit.getPlayer(enemyReference);
        return (long) player.getLocation().distance(enemy.getLocation());
    }

    /**
     * updates this objects last damage time and Enemy who cause the damage
     * @param event the event to read from
     */
    public void update(EntityDamageByEntityEvent event) {
        setLastDamage(System.currentTimeMillis());
        setEnemyReference(event.getDamager().getUniqueId());
    }
}
