package de.ttt.nopvplog.listeners;

import de.ttt.nopvplog.NoPvPLogTemplate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ProjectileHitDetector implements Listener {

    /**checks if a projectile hit a player and puts him into combat and if the shooter was a player as well aslo puts him into combat
     *
     * @param event EntityDamageByEntityEvent
     */
    @EventHandler
    public void onProjectileShoot(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player
                && event.getDamager() instanceof Projectile projectile
                && projectile.getShooter() instanceof Player damager) {
            NoPvPLogTemplate template = (NoPvPLogTemplate) Bukkit.getPluginManager().getPlugin("NoPvPLog");
            template.getCTController().updateTimer(player.getUniqueId());
            template.getCTController().updateTimer(damager.getUniqueId());
        }
    }

}
