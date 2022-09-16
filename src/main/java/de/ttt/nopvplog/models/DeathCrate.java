package de.ttt.nopvplog.models;

import de.ttt.nopvplog.controllers.DeathCrateController;
import de.ttt.nopvplog.exceptions.DeathCrateCreationException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class DeathCrate {

    private final UUID owner;
    private final DeathCrateController controller;
    private Inventory mainInv;
    private Inventory equipInv;

    public DeathCrate(UUID owner, DeathCrateController controller) {

        this.owner = owner;
        this.controller = controller;

    }

    public UUID getOwner() {
        return owner;
    }

    public void createCrates(Location location) {

        Block blockBottom = location.getWorld().getBlockAt(location);
        Block blockTop = blockBottom.getRelative(BlockFace.UP);

        blockBottom.setType(Material.BARREL);
        blockTop.setType(Material.BARREL);

        this.mainInv = ((Barrel) blockBottom).getInventory();
        this.equipInv = ((Barrel) blockTop).getInventory();

    }

    /**
     * Fills both crates of a player who just combat logged
     *
     * @param playerId the Id of the player to fill the crates from
     */
    public void fillCrates(UUID playerId) {

        Player player = Bukkit.getServer().getPlayer(playerId);

        if (player == null) {
            throw new DeathCrateCreationException("Could not find player");
        }

        fillMain(player);
        fillEquip(player);
    }

    /**
     * Fills the main (bottom) crate from the players inventory
     *
     * @param player
     */
    private void fillMain(Player player) {
        mainInv.setContents(player.getInventory().getStorageContents());
    }

    /**
     * Fills the equipment (top) crate from the players hotbar and Armor
     *
     * @param player
     */
    private void fillEquip(Player player) {

        ItemStack[] armorContents = player.getInventory().getArmorContents();
        ItemStack[] hotbarContents = player.getInventory().getExtraContents();

        int length = armorContents.length + hotbarContents.length;
        ItemStack[] equipContents = new ItemStack[length];

        int i = 0;

        for (ItemStack item : armorContents) {
            equipContents[i] = item;
            i++;
        }

        for (ItemStack item : hotbarContents) {
            equipContents[i] = item;
            i++;
        }

        equipInv.setContents(equipContents);
    }
}
