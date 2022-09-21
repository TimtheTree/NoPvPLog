package de.ttt.nopvplog.models;

import de.ttt.nopvplog.controllers.TimerController;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.List;
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
        boolean isOutOfCombat = timePassed() > this.getTimerDuration() && playerEnemyDistance() > this.getMinimumDeactivationDistance();

        if (isOutOfCombat) {
            this.setEnemyReference(null);
        }

        return isOutOfCombat;
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

        if (!event.getDamager().getUniqueId().equals(this.playerReference)) {
            setEnemyReference(event.getDamager().getUniqueId());
        } else {
            setEnemyReference(event.getEntity().getUniqueId());
        }
    }

    /**
     * @return A list of timers which have the owner of this timer as an enemy reference
     */
    public List<UUID> getRelatedTimers() {

        ArrayList<UUID> result = new ArrayList<>();

        for(Timer<? extends EntityDamageEvent> timer : this.getTimerController().getAllTimers()) {

            if(timer instanceof CombatTimerPvp combatTimer
            && combatTimer.getEnemyReference().equals(this.getPlayerReference())) {
                result.add(combatTimer.getPlayerReference());
            }
        }
        return result;
    }
}
