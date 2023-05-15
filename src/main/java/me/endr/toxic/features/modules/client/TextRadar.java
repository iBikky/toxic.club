package me.endr.toxic.features.modules.client;

import me.endr.toxic.Toxic;
import me.endr.toxic.event.events.Render2DEvent;
import me.endr.toxic.features.modules.Module;
import me.endr.toxic.features.setting.Setting;
import me.endr.toxic.util.ColorUtil;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import me.endr.toxic.util.EntityUtil;
import net.minecraft.entity.player.EntityPlayer;
import com.mojang.realmsclient.gui.ChatFormatting;
public class TextRadar extends Module {
    private final Setting<Integer> amount = register(new Setting<>("PlayerCount", 10, 1, 100));
    public Setting<Integer> Y = register(new Setting<>("Y", 5, 0, 550));

    public TextRadar() {
        super("TextRadar", "Shows players in render distance on hud", Category.CLIENT,false ,false ,false);
    }

    public static TextRadar INSTANCE = new TextRadar();
    private int color;


    @Override
    public void onRender2D(Render2DEvent event) {
        int i = 0;
        for (Object o : mc.world.loadedEntityList) {
            if (o instanceof EntityPlayer && o != mc.player) {
                i++;
                if (i > amount.getValue()) return;
                EntityPlayer entity = (EntityPlayer) o;
                float health = Math.round(entity.getHealth()) + Math.round(entity.getAbsorptionAmount());
                final DecimalFormat dfDistance = new DecimalFormat("#.#");
                dfDistance.setRoundingMode(RoundingMode.CEILING);
                final StringBuilder distanceSB = new StringBuilder();
                color = ColorUtil.toRGBA(ClickGui.getInstance().red.getValue(), ClickGui.getInstance().green.getValue(), ClickGui.getInstance().blue.getValue());



                String heal;
                String health_str;
                health_str = "[" + health + "]";
                health_str = health_str.replace(".0", "");


                final int distanceInt = (int) EntityUtil.mc.player.getDistance(entity);
                final String distance = dfDistance.format(distanceInt);
                if (distanceInt >=  25) {
                    distanceSB.append(ChatFormatting.GREEN);
                } else if (distanceInt > 10) {
                    distanceSB.append(ChatFormatting.GOLD);
                } else if (distanceInt >= 50) {
                    distanceSB.append(ChatFormatting.GRAY);
                } else {
                    distanceSB.append(ChatFormatting.RED);
                }
                distanceSB.append(distance);

                if (health >= 12.0) {
                    heal = " " + ChatFormatting.GREEN + health_str + "";
                } else if (health >= 4.0) {
                    heal = " " + ChatFormatting.YELLOW + health_str + "";
                } else {
                    heal = " " + ChatFormatting.RED + health_str + "";
                }


                String name = entity.getGameProfile().getName();
                String str = heal + " " + ChatFormatting.RESET;

                if (Toxic.friendManager.isFriend(entity.getName())) {
                    Toxic.textManager.drawString(str + ChatFormatting.AQUA + name + " " + distanceSB.toString() + ChatFormatting.WHITE , -2.0F, Y.getValue() + i * 10, this.color, true);
                } else if ((ClickGui.getInstance()).rainbow.getValue() && ClickGui.getInstance().rainbowModeHud.getValue() == ClickGui.rainbowMode.Static) {
                    Toxic.textManager.drawString(str + name + " " + distanceSB.toString() + ChatFormatting.GRAY  +  "", -2.0F, Y.getValue() + i * 10, ColorUtil.rainbow(((Integer) (ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB(), true);
                } else {
                    Toxic.textManager.drawString(str + name + " " + distanceSB.toString() + ChatFormatting.GRAY  + "", -2.0F, Y.getValue() + i * 10, this.color, true);
                }
            }
        }
    }
}