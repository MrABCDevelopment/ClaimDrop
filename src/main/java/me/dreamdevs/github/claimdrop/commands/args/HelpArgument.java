package me.dreamdevs.github.claimdrop.commands.args;

import me.dreamdevs.github.claimdrop.ClaimDropMain;
import me.dreamdevs.github.claimdrop.api.commands.ArgumentCommand;
import me.dreamdevs.github.claimdrop.utils.ColourUtil;
import org.bukkit.command.CommandSender;

public class HelpArgument implements ArgumentCommand {

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        commandSender.sendMessage(ColourUtil.colorize("&aHelp for ClaimDrop:"));
        for(Class<? extends ArgumentCommand> argumentCommand : ClaimDropMain.getInstance().getCommandHandler().getArguments().values()) {
            try {
                commandSender.sendMessage(argumentCommand.newInstance().getHelpText());
            } catch (Exception e) {
                commandSender.sendMessage(ColourUtil.colorize("&cSomething went wrong..."));
                return true;
            }
        }
        return true;
    }

    @Override
    public String getHelpText() {
        return "&b/claimdrop help - shows all ClaimDrop commands.";
    }

    @Override
    public String getPermission() {
        return "claimdrop.admin";
    }
}