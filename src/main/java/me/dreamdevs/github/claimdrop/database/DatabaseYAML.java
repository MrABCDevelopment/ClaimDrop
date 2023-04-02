package me.dreamdevs.github.claimdrop.database;

import me.dreamdevs.github.claimdrop.ClaimDropMain;
import me.dreamdevs.github.claimdrop.DropOption;
import me.dreamdevs.github.claimdrop.api.database.DatabaseConnector;
import me.dreamdevs.github.claimdrop.utils.ColourUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DatabaseYAML extends DatabaseConnector {

    private File data;

    @Override
    public void connect() {
        data = new File(ClaimDropMain.getInstance().getDataFolder(), "users");
        if (!data.exists() || !data.isDirectory()) data.mkdirs();
    }

    @Override
    public void disconnect() {}

    @Override
    public void saveData() {
        for(Map.Entry<UUID, DropOption> entry : ClaimDropMain.getInstance().getClaimDropManager().getPlayersOptions().entrySet()) {
            File playerFile = new File(data, entry.getKey()+".yml");
            if (!playerFile.exists()) {
                try {
                    playerFile.createNewFile();
                } catch (Exception e) {
                }
            }
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(playerFile);
            configuration.set("PlayerUUID", entry.getKey().toString());
            configuration.set("DropOption", entry.getValue().name());
            try {
                configuration.save(playerFile);
            } catch (IOException e) {
                Bukkit.getConsoleSender().sendMessage(ColourUtil.colorize("&cCouldn't save "+playerFile.getName()+"!"));
            }
        }
    }

    @Override
    public void loadData() {
        if(data.listFiles().length == 0) return;
        Arrays.stream(data.listFiles((dir, name) -> name.endsWith(".yml")))
                .map(YamlConfiguration::loadConfiguration)
                .forEach(configuration -> ClaimDropMain.getInstance().getClaimDropManager().getPlayersOptions().put(UUID.fromString(configuration.getString("PlayerUUID")), DropOption.valueOf(configuration.getString("DropOption"))));
    }

    @Override
    public boolean isAccount(UUID account) {
        return false;
    }

    @Override
    public void insertData(UUID account) {}
}
