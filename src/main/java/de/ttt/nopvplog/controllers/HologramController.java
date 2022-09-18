package de.ttt.nopvplog.controllers;

import de.ttt.nopvplog.NoPvPLogTemplate;
import de.ttt.nopvplog.exceptions.HologramTextColorException;
import de.ttt.nopvplog.models.Hologram;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HologramController {

    private final NoPvPLogTemplate template;
    private Hologram hologram;

    public HologramController(NoPvPLogTemplate template) {
        this.template = template;
    }

    public NoPvPLogTemplate getTemplate() {
        return template;
    }

    public String[] getHologramId() {
        String[] array = new String[2];
        array[0] = hologram.getMainTextASUUID().toString();
        array[1] = hologram.getSecondTextASUUID().toString();
        return array;
    }

    /**
     * Creates flying text above the passed players position
     *
     * @param owner the Player mentioned in the flying text and who's position it's above
     */
    public void createHologram(Player owner) {
        hologram = new Hologram(owner);

        String mainText = template.getConfig().getString("HologramMainText");
        String secondText = template.getConfig().getString("HologramSecondText");

        hologram.createHologram(mainTextColor(), secondTextColor(), mainText, secondText);
    }

    /**
     * returns the color the main text should be
     *
     * @return ChatColor
     */
    private ChatColor mainTextColor() {
        String textColorName = this.template.getConfig().getString("HologramMainTextColor");
        ChatColor color;

        try {

            color = ChatColor.valueOf(textColorName.toUpperCase());

        } catch (IllegalArgumentException e) {
            throw new HologramTextColorException("Could not get a proper color from config. Make sure it exists and look at the comment in the config!");
        }

        return color;
    }

    /**
     * returns the color the second text should be
     *
     * @return ChatColor
     */
    private ChatColor secondTextColor() {
        String textColorName = this.template.getConfig().getString("HologramSecondTextColor");
        ChatColor color;

        try {

            color = ChatColor.valueOf(textColorName.toUpperCase());

        } catch (IllegalArgumentException e) {
            throw new HologramTextColorException("Could not get a proper color from config. Make sure it exists and look at the comment in the config!");
        }

        return color;
    }
}
