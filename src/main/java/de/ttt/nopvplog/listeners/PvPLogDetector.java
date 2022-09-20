package de.ttt.nopvplog.listeners;

import de.ttt.nopvplog.NoPvPLogTemplate;
import de.ttt.nopvplog.controllers.CombatTimerController;
import de.ttt.nopvplog.controllers.DamageTimerController;
import de.ttt.nopvplog.controllers.DeathCrateController;
import de.ttt.nopvplog.models.CombatTimerPvp;
import de.ttt.nopvplog.models.Timer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Date;
import java.util.UUID;

public class PvPLogDetector implements Listener {

    private final NoPvPLogTemplate template;
    private final DeathCrateController crateController;
    private final CombatTimerController combatTimerController;
    private final DamageTimerController damageTimerController;
    private final int banTimeOnLeave;


    public PvPLogDetector(NoPvPLogTemplate template) {
        this.template = template;
        this.crateController = template.getDeathCrateController();
        this.combatTimerController = template.getCTController();
        this.damageTimerController = template.getDTController();
        this.banTimeOnLeave = template.getConfig().getInt("BanTimeOnLeave");
    }

    public int getBanTimeOnLeave() {
        return banTimeOnLeave;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {

        if (!playerIsInCombat(event.getPlayer().getUniqueId())) return;

        this.crateController.executePvPLogLogic(event.getPlayer());

        event.getPlayer().getInventory().clear();

        event.getPlayer().setHealth(0.0);


        // Bans the player for the configured amount of seconds
        event.getPlayer().banPlayer(renderBanMessage(event),
                new Date(System.currentTimeMillis() + this.banTimeOnLeave * 1000L),
                "NoPvPLog",
                true);

    }

    private boolean playerIsInCombat(UUID playerId) {

        return this.combatTimerController.detectCombat(playerId) || this.damageTimerController.detectCombat(playerId);

    }

    private String renderBanMessage(PlayerQuitEvent event) {

        String banMessage;
        EntityDamageEvent.DamageCause damageCause;
        Player killer;
        Timer<? extends EntityDamageEvent> timer = template.getLongerTimer(event.getPlayer().getUniqueId());

        if (timer instanceof CombatTimerPvp combatTimer) {

            killer = Bukkit.getPlayer(combatTimer.getEnemyReference());
            String killerName = "unknown";

            if (killer != null) {
                killerName = killer.getName();
            }

            banMessage = "Logged out during combat! Killer: " + killerName;

        } else {

            damageCause = timer.getDamageCause();

            banMessage = "Logged out after having taken damage! Cause of damage: " + damageCause.name();

        }

        return banMessage;
    }
}
