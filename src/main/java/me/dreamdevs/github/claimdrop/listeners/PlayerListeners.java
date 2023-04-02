package me.dreamdevs.github.claimdrop.listeners;

import me.dreamdevs.github.claimdrop.ClaimDropMain;
import me.dreamdevs.github.claimdrop.DropOption;
import me.dreamdevs.github.claimdrop.utils.Settings;
import me.dreamdevs.github.claimdrop.utils.Util;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerListeners implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void blockBreakEvent(BlockBreakEvent event) {
        if(event.getPlayer().getGameMode() != GameMode.SURVIVAL) return;
        if(ClaimDropMain.getInstance().getClaimDropManager().getOption(event.getPlayer().getUniqueId()) == DropOption.NORMAL) return;
        if(ClaimDropMain.getInstance().getClaimDropManager().getOption(event.getPlayer().getUniqueId()) == DropOption.INVENTORY) {
            event.setDropItems(false);
            Inventory inventory = event.getPlayer().getInventory();
            ItemStack[] storageContents = inventory.getStorageContents();

            event.getBlock().getDrops().forEach(itemStack -> {
                if(hasEnoughSpace(storageContents, itemStack)) {
                    event.getBlock().getDrops().forEach(inventory::addItem);
                    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.8f, (float) Math.random());
                } else {
                    Util.sendMessage(event.getPlayer(), ClaimDropMain.getInstance().getMessagesManager().getMessage("full-inventory"), Settings.messageType);
                    event.getBlock().getDrops().forEach(itemStack1 -> event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), itemStack1));
                }
            });
        }
        if(ClaimDropMain.getInstance().getClaimDropManager().getOption(event.getPlayer().getUniqueId()) == DropOption.ENDER_CHEST) {
            event.setDropItems(false);
            Inventory inventory = event.getPlayer().getEnderChest();
            ItemStack[] storageContents = inventory.getStorageContents();

            event.getBlock().getDrops().forEach(itemStack -> {
                if(hasEnoughSpace(storageContents, itemStack)) {
                    event.getBlock().getDrops().forEach(inventory::addItem);
                    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.8f, (float) Math.random());
                } else {
                    Util.sendMessage(event.getPlayer(), ClaimDropMain.getInstance().getMessagesManager().getMessage("full-enderchest"), Settings.messageType);
                    event.getBlock().getDrops().forEach(itemStack1 -> event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), itemStack1));
                }
            });
        }
    }

    @EventHandler
    public void joinEvent(PlayerJoinEvent event) {
        if(ClaimDropMain.getInstance().getClaimDropManager().getOption(event.getPlayer().getUniqueId()) == null)
            ClaimDropMain.getInstance().getClaimDropManager().getPlayersOptions().put(event.getPlayer().getUniqueId(), DropOption.NORMAL);
    }

    public boolean hasEnoughSpace(ItemStack[] storageContents, ItemStack itemStack) {
        for(int x = 0; x<storageContents.length; x++) {
            if(storageContents[x] == null) {
                return true;
            }
            if(storageContents[x].isSimilar(itemStack)) {
                if(itemStack.getAmount()+storageContents[x].getAmount() <= itemStack.getMaxStackSize()) {
                    return true;
                }
            }
        }
        return false;
    }

}