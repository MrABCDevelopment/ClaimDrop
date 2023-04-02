package me.dreamdevs.github.claimdrop;

import lombok.Getter;
import me.dreamdevs.github.claimdrop.commands.CommandHandler;
import me.dreamdevs.github.claimdrop.database.Database;
import me.dreamdevs.github.claimdrop.listeners.InventoryListener;
import me.dreamdevs.github.claimdrop.listeners.PlayerListeners;
import me.dreamdevs.github.claimdrop.managers.ClaimDropManager;
import me.dreamdevs.github.claimdrop.managers.MessagesManager;
import me.dreamdevs.github.claimdrop.utils.Settings;
import me.dreamdevs.github.claimdrop.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class ClaimDropMain extends JavaPlugin {

    private @Getter static ClaimDropMain instance;
    private Database database;
    private ClaimDropManager claimDropManager;
    private MessagesManager messagesManager;
    private CommandHandler commandHandler;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        Settings.loadVars();
        this.messagesManager = new MessagesManager(this);

        if(Settings.useDatabase) {
            this.database = new Database();
            this.database.connect(Settings.databaseType);
            this.database.loadData();
            this.database.autoSaveData();
        }

        this.claimDropManager = new ClaimDropManager();
        this.commandHandler = new CommandHandler(this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListeners(), this);

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> new UpdateChecker(ClaimDropMain.getInstance(), 108991).getVersion(version -> {
            if (getDescription().getVersion().equals(version)) {
                Util.sendPluginMessage("");
                Util.sendPluginMessage("&aThere is new ClaimDrop version!");
                Util.sendPluginMessage("&aYour version: " + getDescription().getVersion());
                Util.sendPluginMessage("&aNew version: " + version);
                Util.sendPluginMessage("");
            } else {
                Util.sendPluginMessage("");
                Util.sendPluginMessage("&aYour version is up to date!");
                Util.sendPluginMessage("&aYour version: " + getDescription().getVersion());
                Util.sendPluginMessage("");
            }
        }), 10L, 20L * 300);
    }

    @Override
    public void onDisable() {
        if(Settings.useDatabase)
            database.disconnect();
        saveConfig();
    }

}