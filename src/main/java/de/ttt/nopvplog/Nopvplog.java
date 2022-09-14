package de.ttt.nopvplog;

import de.ttt.nopvplog.controller.CombatTimerController;
import de.ttt.nopvplog.listeners.CombatDetector;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.File;

public final class Nopvplog extends NoPvPLogTemplate {

    private CombatTimerController cTController;
    @Override
    public void onEnable() {
        makeFiles();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Enabled NoPvPLog");
        this.cTController = new CombatTimerController(this);
        Bukkit.getPluginManager().registerEvents(new CombatDetector(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void makeFiles() {

        File configF = new File(getDataFolder(), "config.yml");

        if (!configF.exists()) {
            configF.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }
    }

    @Override
    public CombatTimerController getCTController() {
        return this.cTController;
    }
}
