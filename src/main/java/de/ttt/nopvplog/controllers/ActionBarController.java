package de.ttt.nopvplog.controllers;

import de.ttt.nopvplog.NoPvPLogTemplate;
import de.ttt.nopvplog.models.Timer;
import de.ttt.nopvplog.models.actionbar.CombatTimerMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;
import java.util.UUID;

public class ActionBarController {

    private final String timerMessage;
    private final String enemyMessage;
    private final HashMap<UUID, CombatTimerMessage> actionBarMap = new HashMap<>();
    private final int updateDelay;
    private final NoPvPLogTemplate template;

    public ActionBarController(int updateDelay, NoPvPLogTemplate template) {
        this.updateDelay = updateDelay;
        this.template = template;
        timerMessage = this.template.getConfig().getString("ActionBarTimerMessage");
        enemyMessage = this.template.getConfig().getString("ActionBarEnemyMessage");
    }

    private void updateAllBars() {

        for (Player player : Bukkit.getOnlinePlayers()) {

            CombatTimerMessage message = this.getMessage(player.getUniqueId());

            Timer<? extends EntityDamageEvent> timer = this.template.getLongerTimer(player.getUniqueId());

            if (timer.isOutOfCombat()) {
                continue;
            }

            message.display(timer.timeLeftOnTimer(), !timer.isOutOfCombat(), timer.getMinimumDeactivationDistance());
        }
    }

    /**
     * Gets the entry related to the player ID passed in, creates a new CombatTimerMessage if none were found
     *
     * @param playerId the player to get the message for
     */
    private CombatTimerMessage getMessage(UUID playerId) {

        if (this.actionBarMap.get(playerId) == null) {
            this.actionBarMap.put(playerId, new CombatTimerMessage(this.timerMessage, enemyMessage,playerId));
        }

        return this.actionBarMap.get(playerId);

    }

    /**
     * Runs a timer to update the action bar for each player.
     */
    public void runUpdateTimer() {

        Bukkit.getScheduler().scheduleSyncRepeatingTask(template, this::updateAllBars, 10, this.updateDelay);

    }

}
