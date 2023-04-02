package me.dreamdevs.github.claimdrop.api.inventory;

import lombok.Getter;
import me.dreamdevs.github.claimdrop.listeners.InventoryListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

@Getter
public class GUI implements InventoryHolder {

    private Inventory inventory;
    private String title;
    private int size;

    private GItem[] itemStacks;

    public GUI(String title, GUISize guiSize) {
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        this.size = guiSize.getSize();
        this.itemStacks = new GItem[size];
        this.inventory = Bukkit.createInventory(this, size, this.title);
    }

    public void openGUI(Player player) {
        InventoryListener.guis.put(player.getUniqueId(), this);
        player.openInventory(inventory);
    }

    public void setItem(int slot, GItem gItem) {
        this.itemStacks[slot] = gItem;
        inventory.setItem(slot, gItem.getItemStack());
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}