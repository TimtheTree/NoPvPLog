package de.ttt.nopvplog.models;

import de.ttt.nopvplog.controllers.TimerController;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.*;

public class CombatTimerPvp extends Timer<EntityDamageByEntityEvent> {

    private final Set<UUID> enemyReferences = new HashSet<>();
    private final HashMap<UUID, Long> lastDamageHashmap = new HashMap<>();

    public CombatTimerPvp(UUID playerReference, TimerController<? extends EntityDamageEvent> timerController) {
        super(playerReference, timerController);
    }

    public Set<UUID> getEnemyReferences() {
        return enemyReferences;
    }

    /**adds the given UUID to enemyReferences and also puts it into the lastDamageHashmap with the current Time in milli seconds
     * @param enemyReference UUID of the enemy
     */
    public void addEnemyReference(UUID enemyReference) {
        this.enemyReferences.add(enemyReference);
        this.lastDamageHashmap.put(enemyReference, System.currentTimeMillis());
    }

    public void clearEnemyReferences() {
        this.enemyReferences.clear();
    }

    /**
     * @return true if the combat timer is over AND the enemy players distance is greater than the minimum deactivation distance
     */
    public boolean isOutOfCombat() {
        boolean isOutOfCombat = timePassed() > this.getTimerDuration() && playerEnemyDistance() > this.getMinimumDeactivationDistance();

        if (isOutOfCombat) {
            clearEnemyReferences();
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
                || enemyReferences == null) return Long.MAX_VALUE;

        Player player = Bukkit.getPlayer(playerReference);
        List<Player> enemyList = new ArrayList<>();

        for (UUID uuid : enemyReferences) {
            Player enemy = Bukkit.getPlayer(uuid);
            if (enemy != null) {
                enemyList.add(enemy);
            }
        }

        if (player == null
                || enemyList.isEmpty()) return Long.MAX_VALUE;

        long minDistance = Long.MAX_VALUE;

        for (Player enemy : enemyList) {
            long tempDistance = (long) player.getLocation().distance(enemy.getLocation());

            if (tempDistance <= minDistance) {
                minDistance = tempDistance;
            }
        }

        return minDistance;
    }

    /**
     * updates this objects last damage time and Enemy who cause the damage
     *
     * @param event the event to read from
     */
    public void update(EntityDamageByEntityEvent event) {
        setLastDamage(System.currentTimeMillis());

        if (!event.getDamager().getUniqueId().equals(this.playerReference)) {
            addEnemyReference(event.getDamager().getUniqueId());
        } else {
            addEnemyReference(event.getEntity().getUniqueId());
        }
    }

    /**
     * @return A list of timers which have the owner of this timer as an enemy reference
     */
    public List<UUID> getRelatedTimers() {

        ArrayList<UUID> result = new ArrayList<>();

        for (Timer<? extends EntityDamageEvent> timer : this.getTimerController().getAllTimers()) {

            if (timer instanceof CombatTimerPvp combatTimer
                    && combatTimer.getEnemyReferences().contains(this.getPlayerReference())) {
                result.add(combatTimer.getPlayerReference());
            }
        }
        return result;
    }

    /**
     * @return A list of timers which have the owner of this timer as an enemy reference
     */
    public List<UUID> getRelatedTimers(int minimumDeactivationDistance) {

        ArrayList<UUID> result = new ArrayList<>();
        List<? extends Timer<? extends EntityDamageEvent>> input = this.getTimerController().getAllTimers();
        input.remove(this);


        for (Timer<? extends EntityDamageEvent> timer : input) {

            if (timer instanceof CombatTimerPvp combatTimer
                    && combatTimer.getEnemyReferences().contains(this.getPlayerReference())) {

                Player otherPlayer = Bukkit.getPlayer(combatTimer.getPlayerReference());
                Player player = Bukkit.getPlayer(this.getPlayerReference());

                if (otherPlayer != null && player != null && (otherPlayer.getLocation().distance(player.getLocation()) < minimumDeactivationDistance)) {
                    result.add(combatTimer.getPlayerReference());
                }
            }
        }
        return result;
    }

    /**
     * @return the person whon last damaged the player owning this timer
     */
    public UUID lastDamager() {

        UUID lastDamager = null;

        for (UUID damager : enemyReferences) {
            long tempDamage = lastDamageHashmap.get(damager);
            if (tempDamage == lastDamage) {
                lastDamager = damager;
            }
        }

        return lastDamager;
    }

    @Override
    public void leaveCombat() {
        this.setLastDamage(0);
        clearEnemyReferences();
    }
}
