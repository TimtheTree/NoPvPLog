package de.ttt.nopvplog.listeners;

import de.ttt.nopvplog.NoPvPLogTemplate;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CombatDetector implements Listener {

    private NoPvPLogTemplate template;

    public CombatDetector(NoPvPLogTemplate template) {
        this.template = template;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.PLAYER && event.getDamager().getType() == EntityType.PLAYER) {
            template.getCTController().updateEntry(event);
        } //TODO If player is damaged by anything other than a different player
    }
}
