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

    private static String TAGGED_MESSAGE;
    private final HashMap<UUID, CombatTimerMessage> actionBarMap = new HashMap<>();
    private final int updateDelay;
    private final NoPvPLogTemplate template;

    public ActionBarController(int updateDelay, NoPvPLogTemplate template) {
        this.updateDelay = updateDelay;
        this.template = template;
        TAGGED_MESSAGE = this.template.getConfig().getString("ActionBarMessage");
    }

    private void updateBar(UUID playerID) {

        CombatTimerMessage message = this.getMessage(playerID);

        Timer<? extends EntityDamageEvent> longerTimer = this.template.getLongerTimer(playerID);

        message.display(longerTimer.timeLeftOnTimer());

    }

    private void updateAllBars() {

        for (Player player : Bukkit.getOnlinePlayers()) {

            CombatTimerMessage message = this.getMessage(player.getUniqueId());

            Timer<? extends EntityDamageEvent> timer = this.template.getLongerTimer(player.getUniqueId());

            if (timer.isOutOfCombat(timer.getTimerDuration(), timer.getMinimumDeactivationDistance())) {
                continue;
            }

            message.display(timer.timeLeftOnTimer());
        }
    }

    /**
     * Gets the entry related to the player ID passed in, creates a new CombatTimerMessage if none were found
     *
     * @param playerId
     */
    private CombatTimerMessage getMessage(UUID playerId) {

        if (this.actionBarMap.get(playerId) == null) {
            this.actionBarMap.put(playerId, new CombatTimerMessage(TAGGED_MESSAGE, playerId));
        }

        return this.actionBarMap.get(playerId);

    }

    public void runUpdateTimer() {

        Bukkit.getScheduler().scheduleSyncRepeatingTask(template, this::updateAllBars, 10, this.updateDelay);

    }

}
