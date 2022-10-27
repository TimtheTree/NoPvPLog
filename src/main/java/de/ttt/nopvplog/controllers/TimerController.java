package de.ttt.nopvplog.controllers;

import de.ttt.nopvplog.NoPvPLogTemplate;
import de.ttt.nopvplog.models.Timer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.annotation.Nonnull;
import java.util.*;

public abstract class TimerController<T extends EntityDamageEvent> {

    protected final long minimumDeactivationDistance;
    protected final long timerDuration;
    protected final HashMap<UUID, Timer<T>> timerHashMap;

    /**
     * Gets values for itself from the config gotten from the NoPvPLogTemplate passed in
     *
     * @param template  the template used to get values
     * @param timerPath the path needed to get the time for this controller
     */
    protected TimerController(NoPvPLogTemplate template, String timerPath) {
        this.timerHashMap = new HashMap<>();
        this.timerDuration = template.getConfig().getInt(timerPath);
        this.minimumDeactivationDistance = template.getConfig().getInt("MinimumDeactivationDistancePvP");
    }

    /**
     * @return the duration of the timers used for combat log logic
     */
    public long getTimerDuration() {
        return timerDuration;
    }

    public abstract long getMinimumDeactivationDistance();

    /**
     * @param playerId the player to calculate for
     * @return The time left on the timer. Calculates using the timers duration and the time that has already passed
     */
    public long getTimeLeft(UUID playerId) {
        Timer<T> timer = this.getTimer(playerId);
        return this.timerDuration - (timer.timePassed());
    }

    /**
     * Adds all players to the controller
     */
    public void addAllPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            addEntry(player.getUniqueId());
        }
    }

    /**
     * Adds the player that joined to the controller
     *
     * @param event the event to add the player from
     */
    public void addEntry(PlayerJoinEvent event) {
        addEntry(event.getPlayer().getUniqueId());
    }

    /**
     * Adds the player that was damaged to the controller
     *
     * @param event the event to add the player from
     */
    public void addEntry(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player && this.timerHashMap.containsKey(player.getUniqueId())) {
            addEntry(player.getUniqueId());
        }
    }

    /**
     * Adds the specified player to the controller
     *
     * @param playerId the player to add
     */
    protected abstract void addEntry(UUID playerId);

    /**
     * Gets the timer associated with the player id passed in. <br>
     * If the timer does not exist, creates one before returning the newly created timer
     *
     * @param playerId the player to get the timer for
     * @return the timer found or created, never null
     */
    public @Nonnull Timer<T> getTimer(UUID playerId) {
        Timer<T> timer = this.timerHashMap.get(playerId);

        if (timer == null) {
            this.addEntry(playerId);
            timer = this.timerHashMap.get(playerId);
        }

        return timer;
    }

    public void deleteEntry(UUID playerId) {
        this.timerHashMap.remove(playerId);
    }

    public abstract void updateEntry(T event);

    /**updates the timer and if nonexistent adds a timer for the given playerId
     *
     * @param playerId the UUID of the Player for which the Timer should be updated
     */
    public void updateTimer(UUID playerId) {

        Timer timer = getTimer(playerId);

        if (timer == null) {
            addEntry(playerId);
            timer = getTimer(playerId);
        }

        timer.setLastDamage(System.currentTimeMillis());

    }

    /**gets all timers in timerHashmap
     *
     * @return an ArrayList with all values of timerHashMap
     */
    public List<Timer<T>> getAllTimers() {
        return new ArrayList<>(this.timerHashMap.values());
    }

}
