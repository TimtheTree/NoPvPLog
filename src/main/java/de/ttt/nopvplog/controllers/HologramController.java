package de.ttt.nopvplog.controllers;

import de.ttt.nopvplog.NoPvPLogTemplate;
import de.ttt.nopvplog.exceptions.HologramTextColorException;
import de.ttt.nopvplog.models.Hologram;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.UUID;

public class HologramController {

    private static final String KEY = "UUIDKey";

    private final NoPvPLogTemplate template;
    private Hologram hologram;

    public HologramController(NoPvPLogTemplate template) {
        this.template = template;
    }

    public static String getMetadataKey() {
        return KEY;
    }

    public NoPvPLogTemplate getTemplate() {
        return template;
    }

    /**combines the UUIDs of the two armorstands used for the hologram
     *
     * @return an Array of armorstand UUIDS
     */
    private UUID[] getHologramId() {
        UUID[] array = new UUID[2];
        array[0] = hologram.getMainTextASUUID();
        array[1] = hologram.getSecondTextASUUID();
        return array;
    }

    /**
     * Creates flying text above the passed players position
     *
     * @param event the event when a player logs out
     */
    public void createHologram(PlayerQuitEvent event, Block[] blocks) {
        hologram = new Hologram(event);

        String mainText = template.getConfig().getString("HologramMainText");
        String secondText = template.getConfig().getString("HologramSecondText");

        hologram.createHologram(mainTextColor(), secondTextColor(), mainText, secondText);


        for (Block block : blocks) {
            block.setMetadata(KEY, new FixedMetadataValue(template, getHologramId()));
        }
    }

    /**
     * removes a hologram with the given uuids of the armorstand (usually taken out of hologramId)
     *
     * @param uuids the uuids of the armorstands making up the hologram
     * @param event event that triggers when a block is broken
     */
    public void removeHologram(UUID[] uuids, BlockBreakEvent event) {
        UUID mASID = uuids[0];
        UUID sASID = uuids[1];
        Hologram.removeHologram(mASID, sASID, event.getBlock().getWorld());
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
