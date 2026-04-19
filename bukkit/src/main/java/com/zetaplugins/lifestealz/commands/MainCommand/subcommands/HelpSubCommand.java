package com.zetaplugins.lifestealz.commands.MainCommand.subcommands;

import org.bukkit.command.CommandSender;
import com.zetaplugins.lifestealz.commands.SubCommand;
import com.zetaplugins.lifestealz.util.MessageUtils;
import com.zetaplugins.lifestealz.util.commands.CommandUtils;

public final class HelpSubCommand implements SubCommand {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!hasPermission(sender)) {
            CommandUtils.throwPermissionError(sender);
            return false;
        }

        StringBuilder helpMessage = new StringBuilder("<reset><!i><!b> \n<dark_gray>----------------------------------------------------\n<red><b>LifeStealZ <gray>help page<!b>\n<dark_gray>----------------------------------------------------\n");
        addHelpEntry(helpMessage, sender, "lifestealz.admin.reload", "/lifestealz reload", "- reload the config");
        addHelpEntry(helpMessage, sender, "lifestealz.admin.setlife", "/lifestealz hearts", "- modify how many hearts a player has");
        addHelpEntry(helpMessage, sender, "lifestealz.admin.giveitem", "/lifestealz giveItem", "- give other players custom items, such as hearts");
        addHelpEntry(helpMessage, sender, "lifestealz.viewrecipes", "/lifestealz recipe", "- view all recipes");
        addHelpEntry(helpMessage, sender, "lifestealz.admin.graceperiod", "/lifestealz graceperiod", "- manage a player's grace period");
        addHelpEntry(helpMessage, sender, "lifestealz.managedata", "/lifestealz data", "- import, export or edit player data");
        addHelpEntry(helpMessage, sender, "lifestealz.admin.revive", "/revive", "- revive a player without a revive item");
        addHelpEntry(helpMessage, sender, "lifestealz.admin.eliminate", "/eliminate", "- eliminate a player");
        addHelpEntry(helpMessage, sender, "lifestealz.withdraw", "/withdrawheart", "- withdraw a heart");
        addHelpEntry(helpMessage, sender, "lifestealz.viewhearts", "/hearts", "- view your hearts or the hearts of another player");
        helpMessage.append("\n<dark_gray>----------------------------------------------------\n<reset><!i><!b> ");

        sender.sendMessage(MessageUtils.formatMsg(helpMessage.toString()));
        return true;
    }

    private void addHelpEntry(StringBuilder helpMessage, CommandSender sender, String permission, String command, String description) {
        if (sender.hasPermission(permission)) {
            helpMessage
                    .append("<red><click:SUGGEST_COMMAND:")
                    .append(command)
                    .append(">")
                    .append(command)
                    .append("</click> <dark_gray>- <gray>")
                    .append(description)
                    .append("\n");
        }
    }

    @Override
    public String getUsage() {
        return "/lifestealz help";
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission("lifestealz.help");
    }
}
