package me.endr.toxic.features.modules.misc;

import me.endr.toxic.event.events.PacketEvent;
import me.endr.toxic.features.command.Command;
import me.endr.toxic.features.modules.Module;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
public class PayloadSpoof extends Module {

    public PayloadSpoof() {
        super("PayloadSpoof", "blocks payloads and exploits", Category.MISC, true, false, false);
    }

    @SubscribeEvent(priority= EventPriority.HIGHEST)
    public void onPacketRecieve(PacketEvent.Receive event) {
        String text;
        if (event.getPacket() instanceof SPacketChat && ((text = ((SPacketChat)event.getPacket()).getChatComponent().getUnformattedText()).contains("${") || text.contains("$<") || text.contains("$:-") || text.contains("jndi:ldap"))) {
            Command.sendMessage("&c[AntiLog4j] &cBlocked message: " + text);
            event.setCanceled(true);
        }
    }
}