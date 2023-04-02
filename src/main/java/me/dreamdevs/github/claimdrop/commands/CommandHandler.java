package me.dreamdevs.github.claimdrop.commands;

import lombok.Getter;
import me.dreamdevs.github.claimdrop.ClaimDropMain;
import me.dreamdevs.github.claimdrop.DropOption;
import me.dreamdevs.github.claimdrop.api.commands.ArgumentCommand;
import me.dreamdevs.github.claimdrop.api.inventory.GItem;
import me.dreamdevs.github.claimdrop.api.inventory.GUI;
import me.dreamdevs.github.claimdrop.api.inventory.GUISize;
import me.dreamdevs.github.claimdrop.commands.args.ReloadArgument;
import me.dreamdevs.github.claimdrop.utils.ColourUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;

public class CommandHandler implements TabExecutor {

    private @Getter HashMap<String, Class<? extends ArgumentCommand>> arguments;

    public CommandHandler(ClaimDropMain plugin) {
        this.arguments = new HashMap<>();
        registerCommand("reload", ReloadArgument.class);
        plugin.getCommand("claimdrop").setExecutor(this);
        plugin.getCommand("claimdrop").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        try {
            if(strings.length >= 1) {
                if(arguments.containsKey(strings[0])) {
                    Class<? extends ArgumentCommand> argumentCommand = arguments.get(strings[0]).asSubclass(ArgumentCommand.class);
                    ArgumentCommand argument = argumentCommand.newInstance();
                    if(commandSender.hasPermission(argument.getPermission())) {
                        argument.execute(commandSender, strings);
                    } else {
                        commandSender.sendMessage(ColourUtil.colorize("&cYou don't have permission to do this!"));
                    }
                    return true;
                } else {
                    commandSender.sendMessage(ColourUtil.colorize("&cArgument doesn't exist!"));
                    return true;
                }
            } else {
                if(!(commandSender instanceof Player)) {
                    commandSender.sendMessage(ColourUtil.colorize("&aHelp for ClaimDrop:"));
                    for(Class<? extends ArgumentCommand> argumentCommand : arguments.values()) {
                        commandSender.sendMessage(argumentCommand.newInstance().getHelpText());
                    }
                    return true;
                }
                Player player = (Player)commandSender;
                GUI gui = new GUI("ClaimDrop", GUISize.THREE_ROWS);
                GItem normal = new GItem(Material.DIRT, "&6&lNormal Drop", ColourUtil.colouredLore("", "&7Natural Minecraft drop, nothing special."));
                GItem inventory = new GItem(Material.CHEST, "&6&lInventory Drop", ColourUtil.colouredLore("", "&7Drops into your inventory,", "&7but when it is full then drops normally."));
                GItem enderchest = new GItem(Material.ENDER_CHEST, "&6&lEnderChest Drop", ColourUtil.colouredLore("", "&7Drops into your enderchest,", "&7but when it is full then drops normally."));
                normal.addAction(event -> {
                    ClaimDropMain.getInstance().getClaimDropManager().getPlayersOptions().put(player.getUniqueId(), DropOption.NORMAL);
                    event.getPlayer().closeInventory();
                    event.getPlayer().sendMessage(ClaimDropMain.getInstance().getMessagesManager().getMessage("drop-normal"));
                });

                inventory.addAction(event -> {
                    ClaimDropMain.getInstance().getClaimDropManager().getPlayersOptions().put(player.getUniqueId(), DropOption.INVENTORY);
                    event.getPlayer().closeInventory();
                    event.getPlayer().sendMessage(ClaimDropMain.getInstance().getMessagesManager().getMessage("drop-inventory"));
                });

                enderchest.addAction(event -> {
                    ClaimDropMain.getInstance().getClaimDropManager().getPlayersOptions().put(player.getUniqueId(), DropOption.ENDER_CHEST);
                    event.getPlayer().closeInventory();
                    event.getPlayer().sendMessage(ClaimDropMain.getInstance().getMessagesManager().getMessage("drop-enderchest"));
                });

                gui.setItem(4, getActualOption(player.getUniqueId()));
                gui.setItem(11, normal);
                gui.setItem(13, inventory);
                gui.setItem(15, enderchest);

                gui.openGUI(player);
                return true;
            }
        } catch (Exception e) {

        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> completions = new ArrayList<>();
        if(strings.length == 1) {
            StringUtil.copyPartialMatches(strings[0], arguments.keySet(), completions);
            Collections.sort(completions);
            return completions;
        } else return null;
    }

    public void registerCommand(String command, Class<? extends ArgumentCommand> clazz) {
        arguments.put(command, clazz);
    }

    private GItem getActualOption(UUID uuid) {
        switch (ClaimDropMain.getInstance().getClaimDropManager().getOption(uuid)) {
            case NORMAL:
                return new GItem(Material.DIRT, "&6&lNormal Drop", ColourUtil.colouredLore("", "&aCurrently Activated!"));
            case INVENTORY:
                return new GItem(Material.CHEST, "&6&lInventory Drop", ColourUtil.colouredLore("", "&aCurrently Activated!"));
            case ENDER_CHEST:
                return new GItem(Material.ENDER_CHEST, "&6&lIEnderChest Drop", ColourUtil.colouredLore("", "&aCurrently Activated!"));
            default:
                return null;
        }
    }

}