package de.ttt.nopvplog.controllers;

import de.ttt.nopvplog.NoPvPLogTemplate;
import de.ttt.nopvplog.exceptions.DeathCrateCreationException;
import de.ttt.nopvplog.models.DeathCrate;
import de.ttt.nopvplog.wrappers.LocationWrapper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DeathCrateController {

    private final Material containerType;
    private final NoPvPLogTemplate template;

    public DeathCrateController(NoPvPLogTemplate template) {
        this.template = template;

        String containerName = template.getConfig().getString("DeathCrateType");

        try {

            this.containerType = Material.valueOf(containerName.toUpperCase());

        } catch (IllegalArgumentException e) {
            throw new DeathCrateCreationException("Could not get a proper container type from config. Make sure it exists and is a container with 3x9 inventory space!");
        }
    }

    public Material getContainerType() {
        return containerType;
    }

    public NoPvPLogTemplate getTemplate() {
        return template;
    }

    /**
     * Builds and fills the crates for a player
     *
     * @param ownerId the player to rob for the crates
     */
    private void makeCrates(UUID ownerId, Location location) {

        DeathCrate crate = new DeathCrate(ownerId, this);

        crate.createCrates(location);
        crate.fillCrates(ownerId);

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
     * @param loggedOutPlayer
     */
    public void executePvPLogLogic(Player loggedOutPlayer) {
        makeCrates(loggedOutPlayer.getUniqueId(), loggedOutPlayer.getLocation());
        postCoordsAndInfo(loggedOutPlayer);
    }

}
