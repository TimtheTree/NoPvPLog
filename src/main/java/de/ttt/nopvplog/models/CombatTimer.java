package de.ttt.nopvplog.models;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.UUID;

public class CombatTimer {

    static long timerDuration;

    static long minimumDeactivationDistance;

    private long lastDamage;

    private final UUID playerReference;

    private UUID enemyReference;

    public CombatTimer(UUID playerReference) {
        this.playerReference = playerReference;
    }

    public void setLastDamage(long lastDamage) {
        this.lastDamage = lastDamage;
    }

    public void setEnemyReference(UUID enemyReference) {
        this.enemyReference = enemyReference;
    }

    /**
     * @return time passed in seconds
     */
    public long timePassed() {
        return (this.lastDamage - System.currentTimeMillis()) / 1000;
    }

    public boolean isOutOfCombat() {
        return timePassed() > timerDuration && playerEnemyDistance() > minimumDeactivationDistance;
    }

    public long playerEnemyDistance() {
        Player player = Bukkit.getPlayer(playerReference);
        Player enemy = Bukkit.getPlayer(enemyReference);
        return (long) player.getLocation().distance(enemy.getLocation());
    }

    public void update(EntityDamageByEntityEvent event) {
        setLastDamage(System.currentTimeMillis());
        setEnemyReference(event.getDamager().getUniqueId());
    }
}
