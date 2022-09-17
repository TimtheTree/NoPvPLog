package de.ttt.nopvplog.controllers;

import de.ttt.nopvplog.NoPvPLogTemplate;
import de.ttt.nopvplog.exceptions.HologramTextColorException;
import de.ttt.nopvplog.models.Hologram;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HologramController {

    private final NoPvPLogTemplate template;

    public HologramController(NoPvPLogTemplate template) {
        this.template = template;
    }

    public void createHologram(Player owner) {
        Hologram hologram = new Hologram(owner);

        String mainTextColorName = this.template.getConfig().getString("HologramMainTextColor");
        String secondTextColorName = this.template.getConfig().getString("HologramSecondTextColor");

        ChatColor mainTextColor;
        try {

            mainTextColor = ChatColor.valueOf(mainTextColorName.toUpperCase());

        } catch (IllegalArgumentException e) {

            throw new HologramTextColorException("Could not get a proper color from config. Make sure it exists and look at the comment in the config!");

        }

        ChatColor secondTextColor;
        try {

            secondTextColor = ChatColor.valueOf(secondTextColorName.toUpperCase());

        } catch (IllegalArgumentException e) {

            throw new HologramTextColorException("Could not get a proper color from config. Make sure it exists and look at the comment in the config!");

        }

        String mainText = template.getConfig().getString("HologramMainText");
        String secondText = template.getConfig().getString("HologramSecondText");

        hologram.createHologram(mainTextColor, secondTextColor, mainText, secondText);
    }

    public NoPvPLogTemplate getTemplate() {
        return template;
    }
}
