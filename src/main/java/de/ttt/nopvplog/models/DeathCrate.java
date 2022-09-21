package de.ttt.nopvplog.models;

import de.ttt.nopvplog.controllers.DeathCrateController;
import de.ttt.nopvplog.exceptions.DeathCrateCreationException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class DeathCrate {

    private final UUID owner;
    private final DeathCrateController controller;
    private Inventory mainInv;
    private Inventory equipInv;
    private Block blockBottom;
    private Block blockTop;

    public DeathCrate(UUID owner, DeathCrateController controller) {

        this.owner = owner;
        this.controller = controller;

    }

    public Block getBlockBottom() {
        return blockBottom;
    }

    public Block getBlockTop() {
        return blockTop;
    }

    public DeathCrateController getController() {
        return controller;
    }

    public UUID getOwner() {
        return owner;
    }

    public void createCrates(Location location, Material containerType) {

        Block blockBottom = location.getWorld().getBlockAt(location);
        Block blockTop = blockBottom.getRelative(BlockFace.UP);

        this.blockBottom = blockBottom;
        this.blockTop = blockTop;

        blockBottom.setType(containerType);
        blockTop.setType(containerType);

        BlockState stateBottom = blockBottom.getState();
        BlockState stateTop = blockTop.getState();

        Container containerBottom = (Container) stateBottom;
        Container containerTop = (Container) stateTop;

        this.mainInv = containerBottom.getInventory();
        this.equipInv = containerTop.getInventory();

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
     * @param player the player to fill the main inventory from
     */
    private void fillMain(Player player) {

        ItemStack[] contents = player.getInventory().getContents();

        ItemStack[] only3X9 = new ItemStack[27];

        System.arraycopy(contents, 9, only3X9, 0, 27);

        mainInv.setContents(only3X9);
    }

    /**
     * Fills the equipment (top) crate from the players hotbar and Armor
     *
     * @param player the player to fill the quipment inventory from
     */
    private void fillEquip(Player player) {

        ItemStack[] armorContents = player.getInventory().getArmorContents();
        ItemStack[] onlyHotBar = new ItemStack[9];
        System.arraycopy(player.getInventory().getContents(), 0, onlyHotBar, 0, 9);

        int length = armorContents.length + onlyHotBar.length + 1;
        ItemStack[] equipContents = new ItemStack[length];

        int i = 0;

        for (ItemStack item : armorContents) {
            equipContents[i] = item;
            i++;
        }

        equipContents[i++] = player.getInventory().getItemInOffHand();

        for (ItemStack item : onlyHotBar) {
            equipContents[i] = item;
            i++;
        }

        equipInv.setContents(equipContents);
    }
}
