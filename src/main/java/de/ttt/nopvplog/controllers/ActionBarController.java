package de.ttt.nopvplog.controllers;

import de.ttt.nopvplog.NoPvPLogTemplate;
import de.ttt.nopvplog.models.CombatTimerPvp;
import de.ttt.nopvplog.models.DamageTimer;
import de.ttt.nopvplog.models.Timer;
import de.ttt.nopvplog.models.actionbar.ActionBarMessage;
import org.bukkit.Bukkit;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ActionBarController {

    private final HashMap<UUID, ActionBarMessage> actionBarMap = new HashMap<>();
    private final int updateDelay;
    private final NoPvPLogTemplate template;

    public ActionBarController(int updateDelay, NoPvPLogTemplate template) {
        this.updateDelay = updateDelay;
        this.template = template;
    }

    private void updateBar(UUID playerID) {

        DamageTimer damageTimer = (DamageTimer) this.template.getDTController().getTimer(playerID);
        CombatTimerPvp combatTimer = (CombatTimerPvp) this.template.getCTController().getTimer(playerID);

        Timer<EntityDamageEvent> longerTimer;

        if(damageTimer.getLastDamage() <= combatTimer.getLastDamage()) {
            //TODO
        }



    }

    private void updateAllBars() {

    }

    private List<Timer<EntityDamageEvent>> getRelevantTimers() {
        return null;
    }

    public void runUpdateTime(long timerTicks) {

        Bukkit.getScheduler().scheduleSyncRepeatingTask(template, this::updateAllBars, 10, this.updateDelay);

    }

}
