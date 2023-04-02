package me.dreamdevs.github.claimdrop.api.commands;

import org.bukkit.command.CommandSender;

public interface ArgumentCommand {

    boolean execute(CommandSender commandSender, String[] args);

    String getHelpText();

    String getPermission();

}