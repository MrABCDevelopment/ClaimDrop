package me.dreamdevs.github.claimdrop.database;

import me.dreamdevs.github.claimdrop.ClaimDropMain;
import me.dreamdevs.github.claimdrop.DropOption;
import me.dreamdevs.github.claimdrop.api.database.IData;
import me.dreamdevs.github.claimdrop.utils.ColourUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DatabaseYAML implements IData {

    private File data;

    @Override
    public void connectDatabase() {
        data = new File(ClaimDropMain.getInstance().getDataFolder(), "users");
        if (!data.exists() || !data.isDirectory()) data.mkdirs();
    }

    @Override
    public void disconnectDatabase() {
        // Do nothing
    }

    @Override
    public void saveData(Player player) {
        File playerFile = new File(data, player.getUniqueId()+".yml");
        if (!playerFile.exists()) {
            try {
                playerFile.createNewFile();
            } catch (Exception e) {
            }
        }
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(playerFile);
        configuration.set("PlayerUUID", player.getUniqueId().toString());
        configuration.set("DropOption", ClaimDropMain.getInstance().getClaimDropManager().getOption(player.getUniqueId()).name());
        try {
            configuration.save(playerFile);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(ColourUtil.colorize("&cCouldn't save "+playerFile.getName()+"!"));
        }
    }

    @Override
    public void loadData(Player player) {
        File playerFile = new File(data, player.getUniqueId()+".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);
        ClaimDropMain.getInstance().getClaimDropManager().getPlayersOptions().put(player.getUniqueId(), DropOption.valueOf(config.getString("DropOption", "NORMAL").toUpperCase()));
    }
}
