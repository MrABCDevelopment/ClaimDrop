package me.dreamdevs.github.claimdrop.managers;

import lombok.Getter;
import me.dreamdevs.github.claimdrop.ClaimDropMain;
import me.dreamdevs.github.claimdrop.utils.ColourUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

@Getter
public class MessagesManager {

    private final Map<String, String> messages;

    public MessagesManager(ClaimDropMain plugin) {
        messages = new HashMap<>();
        load(plugin);
    }

    public void load(ClaimDropMain plugin) {
        messages.clear();
        FileConfiguration config = plugin.getConfig();
        ConfigurationSection section = config.getConfigurationSection("messages");
        section.getKeys(false).forEach(s -> messages.put(s, ColourUtil.colorize(section.getString(s))));
        Bukkit.getConsoleSender().sendMessage(ColourUtil.colorize("&aLoaded "+messages.size()+" messages!"));
    }

    public String getMessage(String id) {
        return messages.get(id);
    }

}