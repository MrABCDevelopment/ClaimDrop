package me.dreamdevs.github.claimdrop.commands.args;

import me.dreamdevs.github.claimdrop.ClaimDropMain;
import me.dreamdevs.github.claimdrop.api.commands.ArgumentCommand;
import me.dreamdevs.github.claimdrop.utils.ColourUtil;
import me.dreamdevs.github.claimdrop.utils.Settings;
import org.bukkit.command.CommandSender;

public class ReloadArgument implements ArgumentCommand {


    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        ClaimDropMain.getInstance().saveConfig();
        ClaimDropMain.getInstance().reloadConfig();
        Settings.loadVars();
        ClaimDropMain.getInstance().getMessagesManager().load(ClaimDropMain.getInstance());
        commandSender.sendMessage(ColourUtil.colorize("&aSettings reloaded!"));
        return true;
    }

    @Override
    public String getHelpText() {
        return "&b/claimdrop reload - reloads configuration file.";
    }

    @Override
    public String getPermission() {
        return "claimdrop.admin";
    }
}