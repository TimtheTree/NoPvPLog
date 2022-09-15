package de.ttt.nopvplog.controllers;

import de.ttt.nopvplog.NoPvPLogTemplate;
import de.ttt.nopvplog.models.CombatTimerPvp;
import de.ttt.nopvplog.models.Timer;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.UUID;

public class CombatTimerController extends TimerController<EntityDamageByEntityEvent> {

    private final long minimumDeactivationDistance;

    public CombatTimerController(NoPvPLogTemplate template) {
        super(template);
        this.minimumDeactivationDistance = template.getConfig().getLong("MinimumDeactivationDistancePvP");
    }

    @Override
    public void addEntry(UUID playerId) {
        this.timerHashMap.put(playerId, new CombatTimerPvp(playerId));
    }

    public long getMinimumDeactivationDistance() {
        return minimumDeactivationDistance;
    }


    public boolean detectCombat(UUID playerId) {

        CombatTimerPvp combatTimer = (CombatTimerPvp) this.getTimer(playerId);

        if (combatTimer == null) addEntry(playerId);
        combatTimer = (CombatTimerPvp) this.getTimer(playerId);
        return !combatTimer.isOutOfCombat(this.timerDuration, this.minimumDeactivationDistance);
    }


    public void updateEntry(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.PLAYER && event.getDamager().getType() == EntityType.PLAYER) {
            UUID playerId = event.getEntity().getUniqueId();

            Timer timer = getTimer(playerId);

            if (timer == null) {
                addEntry(event);
                timer = getTimer(playerId);
            }

            timer.update(event);
        }
    }

}
