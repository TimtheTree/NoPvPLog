package de.ttt.nopvplog.controllers;

import de.ttt.nopvplog.NoPvPLogTemplate;
import de.ttt.nopvplog.models.Timer;
import de.ttt.nopvplog.models.actionbar.ActionBarMessage;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ActionBarController {

    private final HashMap<UUID, ActionBarMessage> actionBarMap = new HashMap<>();
    private final int updateDelay;
    private final CombatTimerController combatTimerController;
    private final DamageTimerController damageTimerController;

    public ActionBarController(int updateDelay, CombatTimerController combatTimerController, DamageTimerController damageTimerController) {
        this.updateDelay = updateDelay;
        this.combatTimerController = combatTimerController;
        this.damageTimerController = damageTimerController;
    }

    private void updateBar(UUID playerID){
        //TODO, update the bar of the player
    }

    private void updateAllBars(){

    }

    private List<Timer<EntityDamageEvent>> getRelevantTimers(){
        return null;
    }

    public void runUpdateTime(long timerTicks) {

    }

}
