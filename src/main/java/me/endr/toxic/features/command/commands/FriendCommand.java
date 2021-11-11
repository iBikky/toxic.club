package me.endr.toxic.features.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.endr.toxic.Toxic;
import me.endr.toxic.features.command.Command;
import me.endr.toxic.manager.FriendManager;

public class FriendCommand
        extends Command {
    public FriendCommand() {
        super("friend", new String[]{"<add/del/name/clear>", "<name>"});
    }

    @Override
    public void execute(String[] commands) {
        if (commands.length == 1) {
            if (Toxic.friendManager.getFriends().isEmpty()) {
                FriendCommand.sendMessage("Friend list empty D:.");
            } else {
                StringBuilder f = new StringBuilder("Friends: ");
                for (FriendManager.Friend friend : Toxic.friendManager.getFriends()) {
                    try {
                        f.append(friend.getUsername()).append(", ");
                    } catch (Exception ignored) {
                    }
                }
                FriendCommand.sendMessage(f.toString());
            }
            return;
        }
        if (commands.length == 2) {
            if ("reset".equals(commands[0])) {
                Toxic.friendManager.onLoad();
                FriendCommand.sendMessage("Friends got reset.");
                return;
            }
            FriendCommand.sendMessage(commands[0] + (Toxic.friendManager.isFriend(commands[0]) ? " is friended." : " isn't friended."));
            return;
        }
        if (commands.length >= 2) {
            switch (commands[0]) {
                case "add": {
                    Toxic.friendManager.addFriend(commands[1]);
                    FriendCommand.sendMessage(ChatFormatting.GREEN + commands[1] + " has been friended");
                    return;
                }
                case "del": {
                    Toxic.friendManager.removeFriend(commands[1]);
                    FriendCommand.sendMessage(ChatFormatting.RED + commands[1] + " has been unfriended");
                    return;
                }
            }
            FriendCommand.sendMessage("Unknown Command, try friend add/del (name)");
        }
    }
}

