package me.endr.toxic.features.modules.misc;

import me.endr.toxic.Toxic;
import me.endr.toxic.event.events.PacketEvent;
import me.endr.toxic.features.modules.Module;
import me.endr.toxic.features.setting.Setting;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatModifier
        extends Module {
    private static ChatModifier INSTANCE = new ChatModifier();
    public Setting<Boolean> clean = this.register(new Setting<>("NoChatBackground", Boolean.FALSE, "Cleans your chat"));
    public Setting<Boolean> infinite = this.register(new Setting<>("InfiniteChat", Boolean.FALSE, "Makes your chat infinite."));
    public boolean check;

    public ChatModifier() {
        super("BetterChat", "Modifies your chat", Module.Category.MISC, true, false, false);
        this.setInstance();
    }

    public static ChatModifier getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChatModifier();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketChatMessage) {
            String s = ((CPacketChatMessage) event.getPacket()).getMessage();
            this.check = !s.startsWith(Toxic.commandManager.getPrefix());
        }
    }
}

