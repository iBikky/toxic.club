package me.endr.toxic.features.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.endr.toxic.Toxic;
import me.endr.toxic.features.command.Command;

public class HelpCommand
        extends Command {
    public HelpCommand() {
        super("help");
    }

    @Override
    public void execute(String[] commands) {
        HelpCommand.sendMessage("Commands: ");
        for (Command command : Toxic.commandManager.getCommands()) {
            HelpCommand.sendMessage(ChatFormatting.GRAY + Toxic.commandManager.getPrefix() + command.getName());
        }
    }
}

