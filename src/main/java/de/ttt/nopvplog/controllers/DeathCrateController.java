package de.ttt.nopvplog.controllers;

import de.ttt.nopvplog.NoPvPLogTemplate;
import de.ttt.nopvplog.exceptions.DeathCrateCreationException;
import de.ttt.nopvplog.models.DeathCrate;
import de.ttt.nopvplog.wrappers.LocationWrapper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DeathCrateController {

    private final Material containerType;
    private final NoPvPLogTemplate template;

    /**
     * Creates a new DeathCrateController.
     *
     * @param template the plugin instance
     */
    public DeathCrateController(NoPvPLogTemplate template) {

        this.template = template;

        String containerName = template.getConfig().getString("DeathCrateType");

        if (containerName == null || containerName.equalsIgnoreCase("")) {
            containerName = "BARREL";
        }

        try {

            this.containerType = Material.valueOf(containerName.toUpperCase());

        } catch (IllegalArgumentException e) {
            throw new DeathCrateCreationException("Could not get a proper container type from config. Make sure it exists and is a container with 3x9 inventory space!");
        }
    }

    /**
     * @return The container type specified by the config file
     */
    public Material getContainerType() {
        return containerType;
    }

    /**
     * @return the javaplugin instance used to create this Controller
     */
    public NoPvPLogTemplate getTemplate() {
        return template;
    }

    /**
     * Builds and fills the crates for a player
     *
     * @param ownerId the player to rob for the crates
     */
    private Block[] makeCrates(UUID ownerId, Location location) {

        Block[] array = new Block[2];

        DeathCrate crate = new DeathCrate(ownerId, this);

        crate.createCrates(location, containerType);
        crate.fillCrates(ownerId);

        array[0] = crate.getBlockBottom();
        array[1] = crate.getBlockTop();

        return array;
    }

    /**
     * Puts the name and Location of the player passed in into the chat for everyone to see.
     *
     * @param loggedOutPlayer the player whose location should be posted
     */
    private void postCoordsAndInfo(Player loggedOutPlayer) {

        String name = loggedOutPlayer.getName();
        Location location = loggedOutPlayer.getLocation();

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(ChatColor.GOLD + "NoPvPLog: " + ChatColor.WHITE + name + " disconnected in combat! Their Items are at " + new LocationWrapper(location));
        }

    }


    /**
     * When executed the players inventory will be put into containers at his location <br>
     * Additionally the players location information will be broadcast to all players on the server
     *
     * @param loggedOutPlayer the player who logged out
     * @return returns an Array of the crates which are created by this
     */
    public Block[] executePvPLogLogic(Player loggedOutPlayer) {

        postCoordsAndInfo(loggedOutPlayer);

        return makeCrates(loggedOutPlayer.getUniqueId(), loggedOutPlayer.getLocation());
    }

}
