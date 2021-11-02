package me.endr.toxic.features.command.commands;

import me.endr.toxic.Toxic;
import me.endr.toxic.features.command.Command;

public class UnloadCommand
        extends Command {
    public UnloadCommand() {
        super("unload", new String[0]);
    }

    @Override
    public void execute(String[] commands) {
        Toxic.unload(true);
    }
}

