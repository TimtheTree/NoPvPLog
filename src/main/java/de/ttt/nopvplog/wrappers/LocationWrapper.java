package de.ttt.nopvplog.wrappers;

import org.bukkit.Location;

public class LocationWrapper {

    private final Location location;

    public LocationWrapper(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return this.location.getWorld() + ": " +
                this.location.getBlockX() + ", " +
                this.location.getBlockY() + ", " +
                this.location.getBlockZ();
    }

}