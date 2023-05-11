package me.dreamdevs.github.claimdrop.database;

import lombok.Getter;
import me.dreamdevs.github.claimdrop.ClaimDropMain;
import me.dreamdevs.github.claimdrop.api.database.IData;
import me.dreamdevs.github.claimdrop.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class Database {

    private @Getter IData data;
    private Thread thread;

    public void connect(String databaseType) {
        Class<? extends IData> database = null;
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Connecting to database...");
        try {
            database = Class.forName("me.dreamdevs.github.claimdrop.database.Database" + databaseType).asSubclass(IData.class);
            data = database.newInstance();
            data.connectDatabase();
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Connected to "+databaseType+" database.");
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Database type '" + databaseType + "' does not exist!");
        }
    }

    public void disconnect() {
        data.disconnectDatabase();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Disconnected from the database.");
    }

    public void autoSaveData() {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskTimerAsynchronously(ClaimDropMain.getInstance(), this::saveAllData, 0L, 20L * Settings.databaseTime);
    }

    public void saveAllData() {
        for(Player player : Bukkit.getOnlinePlayers())
            saveData(player);
    }

    public void saveData(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(ClaimDropMain.getInstance(), () -> data.saveData(player));
    }

    public void loadData(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(ClaimDropMain.getInstance(), () -> data.loadData(player));
    }

}