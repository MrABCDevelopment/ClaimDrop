package me.dreamdevs.github.claimdrop;

public enum DropOption {

    /**
     * Drops normally, nothing specially happens.
     */
    NORMAL,

    /**
     * Drops to player's inventory, when inventory is full, drops normally.
     */
    INVENTORY,

    /**
     * Drops to player's ender chest, when it is full, drops normally
     */
    ENDER_CHEST
}