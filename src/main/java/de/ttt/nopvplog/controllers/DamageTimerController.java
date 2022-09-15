package de.ttt.nopvplog.controllers;

import de.ttt.nopvplog.NoPvPLogTemplate;
import de.ttt.nopvplog.models.DamageTimer;

import java.util.UUID;

public class DamageTimerController extends TimerController {

    public DamageTimerController(NoPvPLogTemplate template) {
        super(template);
    }

    @Override
    public void addEntry(UUID playerId) {
        this.timerHashMap.put(playerId, new DamageTimer(playerId));
    }

    public boolean detectDamage(UUID playerId) {

        DamageTimer damageTimer = (DamageTimer) this.getTimer(playerId);

        if (damageTimer == null) addEntry(playerId);
        damageTimer = (DamageTimer) this.getTimer(playerId);
        return !damageTimer.isOutOfCombat(this.timerDuration, 0);
    }
}
