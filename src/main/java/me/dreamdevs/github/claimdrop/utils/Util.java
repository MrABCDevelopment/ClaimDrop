package me.dreamdevs.github.claimdrop.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Util {

    public static void sendMessage(Player player, String message, String messageType) {
        switch (messageType) {
            case "title":
                player.sendTitle("", message, 10, 20, 10);
                break;
            case "actionbar":
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
                break;
            case "chat":
                player.sendMessage(message);
                break;
            default:
                break;
        }
    }

    public static void sendPluginMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(ColourUtil.colorize(message));
    }

}