package me.endr.toxic.features.modules.misc;


import me.endr.toxic.DiscordPresence;
import me.endr.toxic.features.modules.Module;
import me.endr.toxic.features.setting.Setting;

public class RPC
        extends Module {
    public static RPC INSTANCE;
    public Setting<String> state = this.register(new Setting<>("State", "toxic.club v0.2", "Sets the state of the DiscordRPC."));
    public Setting<Boolean> showIP = this.register(new Setting<>("ShowIP", Boolean.TRUE, "Shows the server IP in your discord presence."));

    public RPC() {
        super("RPC", "Discord rich presence", Module.Category.MISC, false, false, false);
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        DiscordPresence.start();
    }

    @Override
    public void onDisable() {
        DiscordPresence.stop();
    }
}