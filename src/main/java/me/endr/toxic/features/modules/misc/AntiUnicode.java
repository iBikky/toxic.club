package me.endr.toxic.features.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;

import me.endr.toxic.event.events.PacketEvent;
import me.endr.toxic.features.command.Command;
import me.endr.toxic.features.modules.Module;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
public class AntiUnicode extends Module {

    public AntiUnicode() {
        super("AntiUnicode", "Makes poplag exploit disappear", Category.MISC, true, false, false);
    }

    public static final String LAGMSG = "\u0101\u0201\u0301\u0401\u0601\u0701\u0801\u0901\u0A01\u0B01\u0E01\u0F01\u1001" + "\u1101\u1201\u1301\u1401\u1501\u1601\u1701\u1801\u1901\u1A01\u1B01\u1C01\u1D01\u1E01\u1F01\u2101\u2201\u2301\u2401\u2501\u2701\u2801" + "\u2901\u2A01\u2B01\u2C01\u2D01\u2E01\u2F01\u3001\u3101\u3201\u3301\u3401\u3501\u3601\u3701\u3801\u3901\u3A01\u3B01\u3C01\u3D01\u3E01" + "\u3F01\u4001\u4101\u4201\u4301\u4401\u4501\u4601\u4701\u4801\u4901\u4A01\u4B01\u4C01\u4D01\u4E01\u4F01\u5001\u5101\u5201\u5301\u5401" + "\u5501\u5601\u5701\u5801\u5901\u5A01\u5B01\u5C01\u5D01\u5E01\u5F01\u6001\u6101\u6201\u6301\u6401\u6501\u6601\u6701\u6801\u6901\u6A01" + "\u6B01\u6C01\u6D01\u6E01\u6F01\u7001\u7101\u7201\u7301\u7401\u7501\u7601\u7701\u7801\u7901\u7A01\u7B01\u7C01\u7D01\u7E01\u7F01\u8001" + "\u8101\u8201\u8301\u8401\u8501\u8601\u8701\u8801\u8901\u8A01\u8B01\u8C01\u8D01\u8E01\u8F01\u9001\u9101\u9201\u9301\u9401\u9501\u9601" + "\u9701\u9801\u9901\u9A01\u9B01\u9C01\u9D01\u9E01\u9F01\uA001\uA101\uA201\uA301\uA401\uA501\uA601\uA701\uA801\uA901\uAA01\uAB01\uAC01" + "\uAD01\uAE01\uAF01\uB001\uB101\uB201\uB301\uB401\uB501\uB601\uB701\uB801\uB901\uBA01\uBB01\uBC01\uBD01";

    @SubscribeEvent(priority= EventPriority.HIGHEST)
    public void onPacketRecieve(PacketEvent.Receive event) {
        String text;
        if (event.getPacket() instanceof SPacketChat && ((text = ((SPacketChat)event.getPacket()).getChatComponent().getUnformattedText()).contains("whispers: \u0101\u0201\u0301\u0401\u0601\u0701\u0801\u0901\u0A01\u0B01\u0E01\u0F01\u1001") || text.contains("whispers: \u1101\u1201\u1301\u1401\u1501\u1601\u1701\u1801\u1901\u1A01\u1B01\u1C01\u1D01\u1E01\u1F01\u2101\u2201\u2301\u2401\u2501\u2701\u2801" + "\u2901\u2A01\u2B01\u2C01\u2D01\u2E01\u2F01\u3001\u3101\u3201\u3301\u3401\u3501\u3601\u3701\u3801\u3901\u3A01\u3B01\u3C01\u3D01\u3E01") || text.contains("whispers: \u2901\u2A01\u2B01\u2C01\u2D01\u2E01\u2F01\u3001\u3101\u3201\u3301\u3401\u3501\u3601\u3701\u3801\u3901\u3A01\u3B01\u3C01\u3D01\u3E01") || text.contains("whispers: \u6B01\\u6C01\\u6D01\\u6E01\\u6F01\\u7001\\u7101\\u7201\\u7301\\u7401\\u7501\\u7601\\u7701\\u7801\\u7901\\u7A01\\u7B01\\u7C01\\u7D01\\u7E01\\u7F01\\u8001\"") || text.contains("whispers: " + LAGMSG))) {
            Command.sendMessage(ChatFormatting.BOLD  + "[AntiUnicode] Blocked potential poplag");
            event.setCanceled(true);
        }
    }
}