package me.endr.toxic.features.command.commands;

import me.endr.toxic.Toxic;
import me.endr.toxic.features.command.Command;

public class ReloadCommand
        extends Command {
    public ReloadCommand() {
        super("reload", new String[0]);
    }

    @Override
    public void execute(String[] commands) {
        Toxic.reload();
    }
}

