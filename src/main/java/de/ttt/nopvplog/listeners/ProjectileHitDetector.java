package de.ttt.nopvplog.listeners;

import de.ttt.nopvplog.NoPvPLogTemplate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ProjectileHitDetector implements Listener {

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
