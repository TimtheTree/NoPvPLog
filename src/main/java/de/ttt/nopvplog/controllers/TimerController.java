package de.ttt.nopvplog.controllers;

import de.ttt.nopvplog.NoPvPLogTemplate;
import de.ttt.nopvplog.models.Timer;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.UUID;

public abstract class TimerController<T extends EntityDamageEvent> {

    protected final long timerDuration;
    protected final HashMap<UUID, Timer<T>> timerHashMap;

    protected TimerController(NoPvPLogTemplate template) {
        this.timerHashMap = new HashMap<>();
        addAllPlayers();

        this.timerDuration = template.getConfig().getLong("TimerDuration");
    }

    public long getTimerDuration() {
        return timerDuration;
    }

    public long getTimeLeft(UUID playerId) {
        Timer<T> timer = this.getTimer(playerId);
        return this.timerDuration - (System.currentTimeMillis() - timer.getLastDamage());
    }

    public void addAllPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            addEntry(player.getUniqueId());
        }
    }

    public void addEntry(PlayerJoinEvent event) {
        addEntry(event.getPlayer().getUniqueId());
    }

    public void addEntry(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player) {
            addEntry(player.getUniqueId());
        }
    }

    protected abstract void addEntry(UUID playerId);

    public Timer<T> getTimer(UUID playerId) {
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

    public void updateTimer(UUID playerId) {

        Timer timer = getTimer(playerId);

        if (timer == null) {
            addEntry(playerId);
            timer = getTimer(playerId);
        }

        timer.setLastDamage(System.currentTimeMillis());

    }


}
